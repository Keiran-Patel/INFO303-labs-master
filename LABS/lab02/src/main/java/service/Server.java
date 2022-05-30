/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import dao.ProductDAO;
import io.jooby.Jooby;
import io.jooby.OpenAPIModule;
import io.jooby.json.GsonModule;
import java.io.IOException;
import resources.ProductItemResource;
import resources.ProductListResource;
/**
 *
 * @author keiranpatel
 */
public class Server extends Jooby {
    public Server() {
		ProductDAO dao = new ProductDAO();

		// add support for JSON
		install(new GsonModule());

		mount(new ProductListResource(dao));
		mount(new ProductItemResource(dao));

        install(new OpenAPIModule());
        
        // provide our OAS specification to the swagger UI
   		assets("/openapi.json", "catalogue.json");
   		assets("/openapi.yaml", "catalogue.yaml");

		// redirect requests to / to /swagger
		get ("/", ctx -> ctx.sendRedirect("/swagger"));
	}

	public static void main(String[] args) throws IOException {
		new Server().start();
	}

}


