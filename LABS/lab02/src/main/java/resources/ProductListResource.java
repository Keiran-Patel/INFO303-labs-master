package resources;

import dao.ProductDAO;
import domain.Product;
import io.jooby.Jooby;
import io.jooby.StatusCode;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author keiranpatel
 */
public class ProductListResource extends Jooby {
    public ProductListResource(ProductDAO dao) {

		path("/api/items", () -> {

			get("", ctx -> {
				return dao.getList();
			});

			post("", ctx -> {
				Product item = ctx.body(Product.class);
				dao.addItem(item);

				// send the URI of the new item via the "location" header of the response
                return ctx
                    .setResponseHeader("location", ctx.getRequestURL() + "/item/"+item.getId())
                    .send(StatusCode.CREATED);
			});

		});
	}
    
}
