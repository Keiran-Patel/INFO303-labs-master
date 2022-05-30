package domain;

import java.io.Serializable;

public class ShoppingItem implements Serializable {

	private String name;
	private String description;

	public ShoppingItem() {
	}

	public ShoppingItem(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ShoppingItem{" + "name=" + name + ", description=" + description + '}';
	}

}
