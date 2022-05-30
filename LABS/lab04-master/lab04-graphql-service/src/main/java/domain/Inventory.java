package domain;

import java.math.BigDecimal;

public class Inventory {

	private BigDecimal currentStockLevel;
	private BigDecimal reorderThreshold;
	private BigDecimal reorderAmount;
	private String supplier;
	private String units;

	public Inventory() {
	}

	public Inventory(BigDecimal currentStockLevel, BigDecimal reorderThreshold, BigDecimal reorderAmount, String supplier, String units) {
		this.currentStockLevel = currentStockLevel;
		this.reorderThreshold = reorderThreshold;
		this.reorderAmount = reorderAmount;
		this.supplier = supplier;
		this.units = units;
	}

	public BigDecimal getCurrentStockLevel() {
		return currentStockLevel;
	}

	public void setCurrentStockLevel(BigDecimal currentStockLevel) {
		this.currentStockLevel = currentStockLevel;
	}

	public BigDecimal getReorderThreshold() {
		return reorderThreshold;
	}

	public void setReorderThreshold(BigDecimal reorderThreshold) {
		this.reorderThreshold = reorderThreshold;
	}

	public BigDecimal getReorderAmount() {
		return reorderAmount;
	}

	public void setReorderAmount(BigDecimal reorderAmount) {
		this.reorderAmount = reorderAmount;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	@Override
	public String toString() {
		return "Inventory{" + "currentStockLevel=" + currentStockLevel + ", reorderThreshold=" + reorderThreshold + ", reorderAmount=" + reorderAmount + ", supplier=" + supplier + ", units=" + units + '}';
	}

}
