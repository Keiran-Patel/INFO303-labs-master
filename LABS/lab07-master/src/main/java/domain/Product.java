package domain;

import java.math.BigDecimal;

public class Product implements Comparable<Product> {

	private String id;
	private String name;
	private String description;
	private String category;
	private BigDecimal price;

	public Product() {
	}

	public Product(String id, String name, String description, String category, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product{" + "id=" + id + ", name=" + name + ", description=" + description + ", category=" + category + ", price=" + price + '}';
	}

	@Override
	public int compareTo(Product other) {
		return this.id.compareTo(other.getId());
	}

}
