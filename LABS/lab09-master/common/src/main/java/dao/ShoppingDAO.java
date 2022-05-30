package dao;

import domain.ShoppingItem;
import domain.ShoppingList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingDAO {

	private static final Map<String, ShoppingItem> items = new HashMap<>();

	public ShoppingList getList() {
		System.out.println("\ngetList called");
		return new ShoppingList(items.values());
	}

	public void addItem(ShoppingItem item) {
		System.out.println("\naddItem called: " + item);
		items.put(item.getName(), item);
	}

	public ShoppingItem getByName(String itemName) {
		System.out.println("\ngetByName called: " + itemName);
		return items.get(itemName);
	}

	public void delete(ShoppingItem item) {
		System.out.println("\ndelete called: " + item);
		items.remove(item.getName());
	}

	public boolean exists(String itemName) {
		return items.containsKey(itemName);
	}

	public void deleteByName(String name) {
		items.remove(name);
	}

}
