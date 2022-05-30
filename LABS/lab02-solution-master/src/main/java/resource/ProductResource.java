package resource;

import dao.CatalogueDAO;
import domain.ErrorMessage;
import domain.Product;
import io.jooby.Jooby;
import io.jooby.StatusCode;

public class ProductResource extends Jooby {

	public ProductResource(CatalogueDAO dao) {

		path("/api/catalogue/product/{id}", () -> {

			// Check that the ID is valid so that the other routes don't need to.
			before(ctx  -> {
				String id = ctx.path("id").value();
				
				if(!dao.exists(id)) {
					ctx
						.setResponseCode(StatusCode.NOT_FOUND)
						.render(new ErrorMessage(String.format("No product found with the ID '%s'", id)));
				}
			});

			get("", ctx -> {
				String id = ctx.path("id").value();
				return dao.getById(id);
			});

			delete("", ctx -> {
				String id = ctx.path("id").value();
				dao.delete(id);
				return ctx.send(StatusCode.NO_CONTENT);
			});

			put("", ctx -> {
				String id = ctx.path("id").value();
				Product item = ctx.body().to(Product.class);

				if (!id.equals(item.getId())) {
					return ctx
						.setResponseCode(StatusCode.CONFLICT)
						.render(new ErrorMessage("Modifying the product's ID via this operation is not allowed.  Create a new product instead."));
				} else {
					dao.updateItem(id, item);
					return ctx.send(StatusCode.NO_CONTENT);
				}
			});

		});

	}

}
