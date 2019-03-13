package com.refresh.pos.domain.inventory;

import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.inventory.InventoryDao;


public class Inventory {

	private Stock stock;
	private ProductCatalog productCatalog;
	private static Inventory instance = null;
	private static InventoryDao inventoryDao = null;

	private Inventory() throws NoDaoSetException {
		if (!isDaoSet()) {
			throw new NoDaoSetException();
		}
		stock = new Stock(inventoryDao);
		productCatalog = new ProductCatalog(inventoryDao);
	}


	public static boolean isDaoSet() {
		return inventoryDao != null;
	}

	public static void setInventoryDao(InventoryDao dao) {
		inventoryDao = dao;
	}

	public ProductCatalog getProductCatalog() {
		return productCatalog;
	}

	public Stock getStock() {
		return stock;
	}

	public static Inventory getInstance() throws NoDaoSetException {
		if (instance == null)
			instance = new Inventory();
		return instance;
	}

}
