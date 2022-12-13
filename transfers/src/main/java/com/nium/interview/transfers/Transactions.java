package com.nium.interview.transfers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transactions {

	private long sourceAcc;
	private long destinationAcc;
	private double amount;
	private Date date;
	private long tranferId;

	public long getSourceAcc() {
		return sourceAcc;
	}

	public void setSourceAcc(long sourceAcc) {
		this.sourceAcc = sourceAcc;
	}

	public long getDestinationAcc() {
		return destinationAcc;
	}

	public void setDestinationAcc(long destinationAcc) {
		this.destinationAcc = destinationAcc;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getTranferId() {
		return tranferId;
	}

	public void setTranferId(long tranferId) {
		this.tranferId = tranferId;
	}

	public static Transactions setTransaction(String line) {

		String[] parts = line.split(",");

		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse(parts[3].trim());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Transactions(Long.parseLong(parts[0].trim()), Long.parseLong(parts[1].trim()),
				Double.parseDouble(parts[2].trim()), date1, Long.parseLong(parts[4].trim()));

	}

	public Transactions(long sourceAcc, long destinationAcc, double amount, Date date, long tranferId) {
		super();
		this.sourceAcc = sourceAcc;
		this.destinationAcc = destinationAcc;
		this.amount = amount;
		this.date = date;
		this.tranferId = tranferId;
	}
}
