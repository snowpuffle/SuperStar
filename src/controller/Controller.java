package controller;

import java.util.List;

import views.View;
import models.Model;
import models.items.Item;
import superstarsystem.DBManager;

public class Controller {
	private View view;
	private Model model;

	// Default Class Constructor
	public Controller(DBManager dbManager) {
		this.model = new Model(dbManager);
		this.view = new View(this);

		view.start();
	}

	// Feature: View all items in the inventory
	public void viewAllItems() {
		List<Item> items = model.getAllItems();
		view.displayAllItems(items);
	}
}