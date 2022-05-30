package domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class ShoppingList implements Serializable {

	private Collection<ShoppingItem> items = new HashSet<>();

	public ShoppingList() {
	}

	public ShoppingList(Collection<ShoppingItem> newItems) {
		this.items.addAll(newItems);
	}

	public void addItem(ShoppingItem item) {
		items.add(item);
	}

	public Collection<ShoppingItem> getItems() {
		return items;
	}

	public void setItems(List<ShoppingItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "ShoppingList{" + "items=" + items + '}';
	}
}
