package graphql;

import dao.ShoppingDAO;
import domain.ShoppingItem;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class ShoppingDataFetcher {

	private final ShoppingDAO dao;

	public ShoppingDataFetcher(ShoppingDAO dao) {
		this.dao = dao;
	}

	public DataFetcher getRemoveItemFetcher() {
		return (DataFetchingEnvironment dfe) -> {
			String name = dfe.getArgument("name");
			dao.deleteByName(name);
			return dao.getList();
		};
	}

	public DataFetcher getAddItemFetcher() {
		return (DataFetchingEnvironment dfe) -> {
			String name = dfe.getArgument("name");
			String description = dfe.getArgument("description");
			dao.addItem(new ShoppingItem(name, description));
			return dao.getList();
		};
	}

	public DataFetcher getShoppingListFetcher() {
		return (DataFetchingEnvironment dfe) -> {
			return dao.getList();
		};
	}

}
