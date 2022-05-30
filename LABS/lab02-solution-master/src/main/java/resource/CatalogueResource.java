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
					ctx
						// add the URI for the new resource to the 'location' header in case the client needs to know where the new resource ended up
						.setResponseHeader("location", ctx.getRequestURL() + "/product/" + product.getId())
						.send(StatusCode.CREATED);
				} else {
					// Non-unique ID
					ctx
						.setResponseCode(StatusCode.UNPROCESSABLE_ENTITY)
						.render(new ErrorMessage("There is already a product with that ID."));
				}

				return ctx;

			});

		});

	}

}
