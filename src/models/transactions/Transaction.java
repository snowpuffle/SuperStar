package models.transactions;

import java.sql.Timestamp;

public class Transaction {
	private int ID;
	private double totalAmount;
	private double taxAmount;
	private TransactionType type;
	private String cashierName;
	private PaymentStatus paymentStatus;
	private Timestamp date;

	// Enum for Transaction Type
	public enum TransactionType {
		CASH, CREDIT
	}

	// Enum for Payment Status
	public enum PaymentStatus {
		PENDING, COMPLETED, REFUNDED
	}

	// Default Class Constructor
	public Transaction(int ID, double totalAmount, double taxAmount, TransactionType type, String cashierName,
			PaymentStatus paymentStatus, Timestamp date) {
		this.ID = ID;
		this.totalAmount = totalAmount;
		this.taxAmount = taxAmount;
		this.type = type;
		this.cashierName = cashierName;
		this.paymentStatus = paymentStatus;
		this.date = date;
	}

	// Getters and Setters
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	// Override toString() to Display Transaction Details
	@Override
	public String toString() {
		return String.format("ID: %d | Total: $%.2f | Tax: $%.2f | Type: %s | Cashier: %s | Status: %s | Date: %s", ID,
				totalAmount, taxAmount, type, cashierName, paymentStatus, (date != null ? date.toString() : "N/A"));
	}

}