package resources;

import dao.ShoppingDAO;
import domain.ShoppingItem;
import io.jooby.Jooby;
import io.jooby.StatusCode;

public class ShoppingListResource extends Jooby {

	public ShoppingListResource(ShoppingDAO dao) {

		path("/api/shopping", () -> {

			get("", ctx -> {
				return dao.getList();
			});

			post("", ctx -> {
				ShoppingItem item = ctx.body(ShoppingItem.class);
				dao.addItem(item);

				// send the URI of the new item via the "location" header of the response
				return ctx
					.setResponseHeader("location", ctx.getRequestURL() + "/item/" + item.getName())
					.send(StatusCode.CREATED);
			});

		});
	}

}
