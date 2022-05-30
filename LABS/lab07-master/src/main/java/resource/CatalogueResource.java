package resource;

import dao.CatalogueDAO;
import domain.ErrorMessage;
import domain.Product;
import io.jooby.Jooby;
import io.jooby.StatusCode;

public class CatalogueResource extends Jooby {

	public CatalogueResource(CatalogueDAO dao) {

		path("/api/catalogue", () -> {

			get("", ctx -> {
				return dao.getCatalogue();
			});

			post("", ctx -> {
				Product product = ctx.body().to(Product.class);

				if (!dao.exists(product.getId())) {

					// store the product
					dao.addItem(product);

					// return a response containing the complete product
					return ctx
						// add the URI for the new resource to the 'location' header in case the client needs to know where the new resource ended up
						.setResponseHeader("location", ctx.getRequestURL() + "/product/" + product.getId())
						.send(StatusCode.CREATED);
				} else {
					// Non-unique ID
					return ctx
						.setResponseCode(StatusCode.UNPROCESSABLE_ENTITY)
						.render(new ErrorMessage("There is already a product with that ID."));
				}

			});

			path("/product/{id}", () -> {

				// Check that the ID is valid so that the other routes don't need to.
				before(ctx -> {
					String id = ctx.path("id").value();

					if (!dao.exists(id)) {
						ctx
							.setResponseCode(StatusCode.NOT_FOUND)
							.render(new ErrorMessage(String.format("No product found with the ID '%s'", id)));
					}
				});

				get("", ctx -> {
					String id = ctx.path("id").value();

					if (!dao.exists(id)) {
						ctx
							.setResponseCode(StatusCode.NOT_FOUND)
							.render(new ErrorMessage(String.format("No product found with the ID '%s'", id)));
					}

					return dao.getById(id);
				});

				delete("", ctx -> {
					String id = ctx.path("id").value();
					dao.delete(id);
					return ctx.send(StatusCode.NO_CONTENT);
				});

			});

		});

	}

}
