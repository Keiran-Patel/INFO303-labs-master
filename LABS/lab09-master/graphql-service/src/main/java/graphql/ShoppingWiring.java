package graphql;

import dao.ShoppingDAO;
import graphql.schema.idl.RuntimeWiring;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

public class ShoppingWiring {

	private final ShoppingDAO dao;

	public ShoppingWiring(ShoppingDAO dao) {
		this.dao = dao;
	}

	public RuntimeWiring getWiring() {
		return RuntimeWiring
			.newRuntimeWiring()
			.type(
				newTypeWiring("ShoppingListQuery")
					.dataFetcher("shoppingList", new ShoppingDataFetcher(dao).getShoppingListFetcher())
			).type(
				newTypeWiring("ShoppingListMutation")
					.dataFetcher("removeItem", new ShoppingDataFetcher(dao).getRemoveItemFetcher())
					.dataFetcher("addItem", new ShoppingDataFetcher(dao).getAddItemFetcher())
			).build();
	}

}
