package graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.InventoryDAO;
import domain.Product;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.math.BigDecimal;
import java.util.Map;

public class InventoryDataFetcher {

	private final InventoryDAO dao;

	public InventoryDataFetcher(InventoryDAO dao) {
		this.dao = dao;
	}

	public DataFetcher getProductByIdFetcher() {
		return (DataFetchingEnvironment dfe) -> {
			String id = dfe.getArgument("id");
			return dao.getById(id);
		};
	}

	public DataFetcher getCatalogueFetcher() {
		return (DataFetchingEnvironment dfe) -> {
			return dao.getCatalogue();
		};
	}
        
        public DataFetcher getCreateProductFetcher() {
    return (DataFetchingEnvironment dfe) -> {
        Map map = dfe.getArgument("newProduct");
        ObjectMapper mapper = new ObjectMapper();
        Product newProduct = mapper.convertValue(map, Product.class);
        return dao.addProduct(newProduct);
    };
}

	public DataFetcher getCatalogueByCategoryFetcher() {
		return (DataFetchingEnvironment dfe) -> {
			String category = dfe.getArgument("category");
			return dao.getCategory(category);
		};
	}

	public DataFetcher getDueForReorderFetcher() {
		return (DataFetchingEnvironment dfe) -> {
			return dao.dueForReorder();
		};
	}

	public DataFetcher getOrderFetcher() {
		return (DataFetchingEnvironment dfe) -> {
			String id = dfe.getArgument("id");
			Double quantity = dfe.getArgument("quantity");
			return dao.reorder(id, new BigDecimal(quantity));
		};
	}

	public DataFetcher getDeleteProductFetcher() {
		return (DataFetchingEnvironment dfe) -> {
			String id = dfe.getArgument("id");
			return dao.delete(id);
		};
	}
        public DataFetcher getCategoriesFetcher() {
		return (DataFetchingEnvironment dfe) -> {
			return dao.getCategories();
		};
                
        }}
