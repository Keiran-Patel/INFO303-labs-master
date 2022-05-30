package dao;

import domain.ShoppingItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingDAO {

	private static final Map<String, ShoppingItem> items = new HashMap<>();

	static {
		if(items.isEmpty()) {
			items.put("bread", new ShoppingItem("bread", "wholegrain"));
			items.put("beer", new ShoppingItem("beer", "something tasty"));
			items.put("veges", new ShoppingItem("veges", "spuds and onions"));
		}
	}

	public List<ShoppingItem> getList() {
		return new ArrayList<>(items.values());
	}

	public void addItem(ShoppingItem item) {
		items.put(item.getName(), item);
	}

	public ShoppingItem getByName(String itemName) {
		return items.get(itemName);
	}

	public void delete(String name) {
		items.remove(name);
	}

	public void updateItem(String name, ShoppingItem updatedItem) {
		items.put(name, updatedItem);
	}

	public boolean exists(String itemName) {
		return items.containsKey(itemName);
	}

}
