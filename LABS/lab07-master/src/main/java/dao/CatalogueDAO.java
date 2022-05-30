package dao;

import domain.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CatalogueDAO {

	private static final Map<String, Product> items = new TreeMap<>();

	/*
	 * Some dummy data for testing
	 */
	static {
		if (items.isEmpty()) {
			items.put("WD1234", new Product("WD1234", "Green Widget", "A widget that has gone mouldy.", "Widgets", new BigDecimal("21.43")));
			items.put("DH8832", new Product("DH8832", "Dodgy Doohicky", "A doohicky that might work, or it might not...", "Doohickies", new BigDecimal("12.32")));
		}
	}

	/**
	 * Gets all products in the catalogue ordered by ID.
	 *
	 * @return All products ordered by ID.
	 */
	public List<Product> getCatalogue() {
		return new ArrayList<>(items.values());
	}

	/**
	 * Adds a product to the catalogue.
	 *
	 * @param item The product being added.
	 */
	public void addItem(Product item) {
		items.put(item.getId(), item);
	}

	/**
	 * Gets a single product that matches the given ID.
	 *
	 * @param id The ID to search for.
	 * @return The product matching the given ID, or null if no match found.
	 */
	public Product getById(String id) {
		return items.get(id);
	}

	/**
	 * Deletes a product.
	 *
	 * @param id The ID of the product to delete.
	 */
	public void delete(String id) {
		items.remove(id);
	}

	/**
	 * Updates a product (effectively replaces it).
	 *
	 * @param id The ID of the product to replace.
	 * @param updatedProduct The product to replace it with.
	 */
	public void updateItem(String id, Product updatedProduct) {
		items.put(id, updatedProduct);
	}

	/**
	 * Checks if a product exists.
	 *
	 * @param id The ID of the product being checked.
	 * @return <code>true</code> if product exists, <code>false</code> if not.
	 */
	public boolean exists(String id) {
		return items.containsKey(id);
	}

}
