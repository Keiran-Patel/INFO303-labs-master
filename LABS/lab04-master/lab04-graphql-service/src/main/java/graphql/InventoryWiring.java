package graphql;

import dao.InventoryDAO;
import graphql.schema.idl.RuntimeWiring;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

public class InventoryWiring {

	private final InventoryDAO dao;

	public InventoryWiring(InventoryDAO dao) {
		this.dao = dao;
	}

	public RuntimeWiring getWiring() {
		return RuntimeWiring
			.newRuntimeWiring()
			.type(
				newTypeWiring("CatalogueQuery")
					.dataFetcher("catalogue", new InventoryDataFetcher(dao).getCatalogueFetcher())
					.dataFetcher("product", new InventoryDataFetcher(dao).getProductByIdFetcher())
					.dataFetcher("productsInCategory", new InventoryDataFetcher(dao).getCatalogueByCategoryFetcher())
					.dataFetcher("dueForReorder", new InventoryDataFetcher(dao).getDueForReorderFetcher())
                                        .dataFetcher("categories", new InventoryDataFetcher(dao).getCategoriesFetcher())
			).type(
            newTypeWiring("CatalogueMutation")
					.dataFetcher("deleteProduct", new InventoryDataFetcher(dao).getDeleteProductFetcher())
					.dataFetcher("order", new InventoryDataFetcher(dao).getOrderFetcher())
                                        .dataFetcher("createProduct", new InventoryDataFetcher(dao).getCreateProductFetcher())
         ).build();
	}

}
