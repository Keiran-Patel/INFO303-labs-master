package resources;

import dao.ShoppingDAO;
import domain.ShoppingItem;
import io.jooby.Jooby;
import io.jooby.MediaType;
import io.jooby.StatusCode;


public class ShoppingItemResource extends Jooby {

	public ShoppingItemResource(ShoppingDAO dao) {

		path("/api/items/item", () -> {

			get("/{name}", ctx -> {
				String name = ctx.path("name").value();

				if (dao.exists(name)) {
					return dao.getByName(name);
				} else {
					return ctx.send(StatusCode.NOT_FOUND);
				}

			});

			delete("/{name}", ctx -> {
				String name = ctx.path("name").value();

				if (dao.exists(name)) {
					dao.delete(name);
					return ctx.send(StatusCode.NO_CONTENT);
				} else {
					return ctx.send(StatusCode.NOT_FOUND);
				}
			});

			put("/:name", ctx -> {
				String name = ctx.path("name").value();
				ShoppingItem item = ctx.body().to(ShoppingItem.class);

				// make sure the names match an existing item or there is a conflict that will likely cause data corruption in the database (identifying keys should always be immutable)
				if (name.equals(item.getName()) && dao.exists(name)) {
					dao.updateItem(name, item);
                    return ctx.send(StatusCode.NO_CONTENT);
				} else {
                    return ctx.send(StatusCode.NOT_FOUND);
				}
			});

		});

	}

}
