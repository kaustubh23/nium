package com.nium.interview.transfers;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * A Command-line application to parse and process a transfers file and provide
 * the business requirements, namely:
 * <ul>
 * <li>1. Print the final balances on all bank accounts</li>
 * <li>2. Print the bank account with the highest balance</li>
 * <li>3. Print the most frequently used source bank account</li>
 * </ul>
 * </p>
 */
@SpringBootApplication
@Log4j2
public class TransfersApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TransfersApplication.class, args);
	}

	@Override
	public void run(final String... args) {
		// Below is some sample code to get you started. Good luck :)

		final URL file = getClass().getClassLoader().getResource("transfers.txt");
		try {
			Files.readAllLines(Path.of(file.toURI()));
			process(file);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String process(URL file) {

		try {
			HashMap<Long, Integer> frequency = new HashMap<>();
			final DecimalFormat df = new DecimalFormat("0.00");
			df.setRoundingMode(RoundingMode.UP);

			try (Stream<String> lines = Files.lines(Path.of(file.toURI()))) {
            // Get List of all transactions
				List<Transactions> transactions = lines.filter(line -> line.trim().matches(("\\d.*")))
						.map(Transactions::setTransaction).collect(Collectors.toList());
				if (!transactions.isEmpty()) {
              // Extract Transactions to process
					Map<Long, Double> accounts = transactions.stream().filter(trans -> trans.getSourceAcc() == 0)
							.map(Account::setAccount)
							.collect(Collectors.toMap(Account::getAcNumber, Account::getBalance));
                 // process to get most frequent transaction 
					for (Transactions item : transactions) {
						if (item.getSourceAcc() != 0) {
							if (!frequency.containsKey(item.getSourceAcc())) {
								frequency.put(item.getSourceAcc(), 1);
							} else {
								frequency.put(item.getSourceAcc(), frequency.get(item.getSourceAcc()) + 1);
							}
						}
                  // process transaction in/out amount
						if (item.getSourceAcc() != 0 && accounts.containsKey(item.getSourceAcc())
								&& accounts.containsKey(item.getDestinationAcc())) {
							accounts.put(item.getSourceAcc(), Double
									.parseDouble(df.format(accounts.get(item.getSourceAcc()) - item.getAmount())));
							accounts.put(item.getDestinationAcc(), Double
									.parseDouble(df.format(accounts.get(item.getDestinationAcc()) + item.getAmount())));

						}
					}
					//Printout result on console
					printResults(accounts, frequency);

				} else {
					System.out.println("#No Transaction is found to process");
					return "Process failed";
				}

			}
		} catch (IOException | URISyntaxException x) {
			x.printStackTrace();
		}
		return "Process Successful";
	}

	public void printResults(Map<Long, Double> accounts, HashMap<Long, Integer> frequency) {

		System.out.println("#Balances");
		accounts.entrySet().stream().sorted(Map.Entry.<Long, Double>comparingByKey()).forEach(entry -> {
			System.out.println(entry.getKey() + " - " + entry.getValue());

		});
		System.out.println("#Bank Account with highest balance");
		System.out.println(
				Collections.max(accounts.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey());

		System.out.println("#Frequently used source bank account");
		System.out
				.println(Collections.max(frequency.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey());

	}

}
