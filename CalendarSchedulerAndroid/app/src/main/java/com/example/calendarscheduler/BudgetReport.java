package com.example.calendarscheduler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Stack;



public class BudgetReport {

	LinkedList<BudgetItem> budgetReportList = new LinkedList<BudgetItem>();

	double totalIn;
	double totalEx;
	double totalMon;

	public BudgetReport() {
	}

	// adds item in order
	public void addItem(BudgetItem it) {

		// loops from backs to front
		for (int i = budgetReportList.size() - 1; i >= 0; i--) {
            budgetReportList.add(i, it);
            return;
		}
        budgetReportList.addFirst(it);

	}

	public String toString() {
		return Arrays.toString(budgetReportList.toArray());
	}

	public double getTotalIn() {
		return totalIn;
	}

	public double getTotalEx() {
		return totalEx;
	}

	// Updates totals
	public double getTotalMon(BudgetReport list) {
		// variables for adding
		double upTotalMon = 0;
		double upTotalIn = 0;
		double upTotalEx = 0;

		// loops through linked list
		for (int i = 0; i < list.budgetReportList.size(); i++) {
			BudgetItem it = list.budgetReportList.get(i);
			// adds to total money
			upTotalMon = upTotalMon + it.getAmount();
			// if item is income adds the total income
			if (it instanceof Income)
				upTotalIn = upTotalIn + it.getAmount();
			// else adds the total expense
			else
				upTotalEx = upTotalEx + it.getAmount();
		}
		totalIn = upTotalIn;
		totalEx = upTotalEx;
		totalMon = upTotalMon;
		return totalMon;
	}

	// delete
	public void delete(BudgetReport list, String date) {
		for (int i = 0; i < list.budgetReportList.size(); i++) {
			BudgetItem it = list.budgetReportList.get(i);
			// if item matches day
			if (it.getDate().compareTo(date) == 0)
				list.budgetReportList.remove(i);
		}
	}

}
