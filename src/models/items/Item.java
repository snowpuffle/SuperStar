package models.items;

import java.time.LocalDate;

public class Item {
	private int ID;
	private double price;
	private String type;
	private String name;
	private int quantity;
	private String brandName;
	private boolean isOrganic;
	private LocalDate expirationDate;
	private String status;
	private String imageURL;

	// Default Class Constructor
	public Item(int ID, double price, String name, String type, int quantity, String brandName, boolean isOrganic,
			LocalDate expirationDate, String status, String imageURL) {
		this.ID = ID;
		this.price = price;
		this.name = name;
		this.type = type;
		this.quantity = quantity;
		this.brandName = brandName;
		this.isOrganic = isOrganic;
		this.expirationDate = expirationDate;
		this.status = status;
		this.imageURL = imageURL;
	}

	// Getters and Setters
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public boolean isOrganic() {
		return isOrganic;
	}

	public void setOrganic(boolean isOrganic) {
		this.isOrganic = isOrganic;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	// Override toString() to Display Item Details
	@Override
	public String toString() {
		return String.format(
				"ID: %d | Name: %s | Type: %s | Price: $%.2f | Quantity: %d | Brand: %s | Organic: %s | Expiration: %s | Status: %s | Image: %s",
				ID, name, type, price, quantity, brandName, (isOrganic ? "Yes" : "No"),
				(expirationDate != null ? expirationDate.toString() : "N/A"), status, imageURL);
	}
}