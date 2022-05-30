package dao;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import domain.Product;
import domain.Inventory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class InventoryDAO {

	private static final Map<String, Product> itemsById = new TreeMap<>();
	private static final Multimap<String, Product> itemsByCategory = TreeMultimap.create();

	public InventoryDAO() {
		/*
		 * Some dummy data for testing
		 */
		if (itemsById.isEmpty()) {

			Inventory i1 = new Inventory(new BigDecimal("12.5"), new BigDecimal("10.0"), new BigDecimal("20.0"), "Wally's Widgets", "kg");
			Product p1 = new Product("WD1234", "Green Widget", "A widget that has gone mouldy.", "Widgets", new BigDecimal("21.43"), i1);
			addProduct(p1);

			Inventory i2 = new Inventory(new BigDecimal("10.0"), new BigDecimal("20.0"), new BigDecimal("50.0"), "Deidres' Doohickies", null);
			Product p2 = new Product("DH8832", "Dodgy Doohicky", "A doohicky that might work, or it might not...", "Doohickies", new BigDecimal("12.32"), i2);
			addProduct(p2);

			Inventory i3 = new Inventory(new BigDecimal("5.0"), new BigDecimal("10.0"), new BigDecimal("50.0"), "Wally's Widgets", null);
			Product p3 = new Product("WD3242", "Fuzzy Widget", "A widget that fell in some cat hair.", "Doohickies", new BigDecimal("11.63"), i3);
			addProduct(p3);
		}
	}

	/**
	 * Gets all products in the catalogue ordered by ID.
	 *
	 * @return All products ordered by ID.
	 */
	public Collection<Product> getCatalogue() {
		return new ArrayList<>(itemsById.values());
	}

	/**
	 * Adds a product to the catalogue.
	 *
	 * @param newProduct The product being added.
	 * @return The created product.
	 */
	public final Product addProduct(Product newProduct) {
		// create a default inventory object if the product doesn't have one
		if(newProduct.getInventory() == null) {
			Inventory newInventory = new Inventory(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "NO SUPPLIER", "NO UNIT");
			newProduct.setInventory(newInventory);
		}
		itemsById.put(newProduct.getId(), newProduct);
		itemsByCategory.put(newProduct.getCategory(), newProduct);
		return newProduct;
	}

	/**
	 * Gets a single product that matches the given ID.
	 *
	 * @param id The ID to search for.
	 * @return The product matching the given ID, or null if no match found.
	 */
	public Product getById(String id) {
		return itemsById.get(id);
	}

	/**
	 * Gets all categories.
	 *
	 * @return The categories.
	 */
	public Collection<String> getCategories() {
		return itemsByCategory.keySet();
	}

	/**
	 * Gets all product in a given category.
	 *
	 * @param category
	 * @return The products in the given category.
	 */
	public Collection<Product> getCategory(String category) {
		return itemsByCategory.get(category);
	}

	/**
	 * Deletes a product.
	 *
	 * @param id The ID of the product to delete.
	 * @return The deleted product, or null if nothing was deleted.
	 */
	public Product delete(String id) {
		Product p = itemsById.remove(id);
		itemsByCategory.remove(p.getCategory(), p);
		return p;
	}

	/**
	 * Checks if a product exists.
	 *
	 * @param id The ID of the product being checked.
	 * @return <code>true</code> if product exists, <code>false</code> if not.
	 */
	public boolean exists(String id) {
		return itemsById.containsKey(id);
	}

	/**
	 * Find products that are due for reordering.
	 *
	 * @return Products that need to be reordered (their current stock level is
	 * below the reorder threshold).
	 */
	public Collection<Product> dueForReorder() {
		return itemsById.values().stream()
			.filter(p -> p.getInventory().getCurrentStockLevel().compareTo(p.getInventory().getReorderThreshold()) < 0)
			.collect(Collectors.toList());
	}

	/**
	 * Increase the stock level for an existing product.
	 *
	 * @param id The ID of the product being reordered.
	 * @param quantity The quantity to reorder.
	 * @return The updated product.
	 */
	public Product reorder(String id, BigDecimal quantity) {
		Product product = itemsById.get(id);
		Inventory inventory = product.getInventory();
		inventory.setCurrentStockLevel(inventory.getCurrentStockLevel().add(quantity));
		return product;
	}

}
