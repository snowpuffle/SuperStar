package models.items;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Item {

	// Item Attributes
	private int ID;
	private double price;
	private String name;
	private String type;
	private int quantity;
	private String status;

	public Item() {

	}

	// Default Class Constructor
	public Item(int ID, double price, String name, String type, int quantity, String status) {
		this.ID = ID;
		this.price = price;
		this.name = name;
		this.type = type;
		this.quantity = quantity;
		this.status = status;
	}

	// Getter & Setter Methods
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ID: " + this.ID + "\tPrice: " + this.price + "\tName: " + this.name + "\tType: " + this.type
				+ "\tQuantity: " + this.quantity + "\tStatus: " + this.status;
	}

	public enum ItemType {
		FRUITS, VEGETABLES
	}

	public static List<String> getItemTypeOptions() {
		// Convert Each ENUM Constant to its Name (string)
		return Arrays.stream(ItemType.values()).map(Enum::name).collect(Collectors.toList());
	}

}