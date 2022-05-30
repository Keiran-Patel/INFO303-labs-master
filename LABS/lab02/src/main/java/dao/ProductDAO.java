package dao;
import domain.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author keiranpatel
 */
public class ProductDAO {

	private static final Map<String, Product> items = new HashMap<>();

	static {
		if(items.isEmpty()) {
			items.put("57777", new Product("57777", "bread",2.00));
			items.put("90000", new Product("90000", "chips", 3.00));
			items.put("100", new Product("100", "spuds", 4.00));
		}
	}

	public List<Product> getList() {
		return new ArrayList<>(items.values());
	}

	public void addItem(Product item) {
		items.put(item.getId(), item);
	}

	public Product getByName(String itemName) {
		return items.get(itemName);
	}

	public void delete(String id) {
		items.remove(id);
	}

	public void updateItem(String name, Product updatedItem) {
		items.put(name, updatedItem);
	}

	public boolean exists(String itemName) {
		return items.containsKey(itemName);
	}

}


