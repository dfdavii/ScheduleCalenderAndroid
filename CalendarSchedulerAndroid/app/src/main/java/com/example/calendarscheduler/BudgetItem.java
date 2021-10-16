package com.example.calendarscheduler;

public class BudgetItem {
	private String title;
	private Double amount;
	private String transaction_type;
	private String date;

	public BudgetItem(String title, Double amount, String transaction_type, String date) {
		this.title = title;
		this.amount = amount;
		this.transaction_type = transaction_type;
		this.date = date;
	}

	public String toString() {
		return "Title: " + title + "  Date: " + date + "  Amount: $" + amount + "  Recurrance: " + transaction_type;
	}

	public String getTitle() {
		return title;
	}

	public Double getAmount() {
		return amount;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public String getDate() {
		return date.toString();
	}

	public String getDateObj() {
		return date;
	}
}
