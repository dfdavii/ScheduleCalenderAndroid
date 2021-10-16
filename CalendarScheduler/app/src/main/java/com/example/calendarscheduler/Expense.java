package com.example.calendarscheduler;

public class Expense extends BudgetItem {

	public Expense(String title, Double amount, String recurrance, String date) {
		super(title, (amount * -1), recurrance, date);
	}
}
