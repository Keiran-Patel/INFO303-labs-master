/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

import dao.ProductDAO;
import domain.Product;
import io.jooby.Jooby;
import io.jooby.MediaType;
import io.jooby.StatusCode;

/**
 *
 * @author keiranpatel
 */
public class ProductItemResource extends Jooby {
    
    public ProductItemResource(ProductDAO dao) {

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

			put("/{name}", ctx -> {
				String name = ctx.path("name").value();
				Product item = ctx.body().to(Product.class);

				// make sure the names match an existing item or there is a conflict that will likely cause data corruption in the database (identifying keys should always be immutable)
				if (name.equals(item.getId()) && dao.exists(name)) {
					dao.updateItem(name, item);
                    return ctx.send(StatusCode.NO_CONTENT);
				} else {
                    return ctx.send(StatusCode.NOT_FOUND);
				}
			});

		});

	}

}


