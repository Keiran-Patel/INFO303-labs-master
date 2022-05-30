package resources;

import dao.ShoppingDAO;
import io.jooby.Jooby;
import io.jooby.StatusCode;

public class ShoppingItemResource extends Jooby {

	public ShoppingItemResource(ShoppingDAO dao) {

		path("/api/shopping", () -> {

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
					dao.deleteByName(name);
					return ctx.send(StatusCode.NO_CONTENT);
				} else {
					return ctx.send(StatusCode.NOT_FOUND);
				}
			});

		});

	}

}
