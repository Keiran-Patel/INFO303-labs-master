package service;

import dao.ShoppingDAO;
import io.jooby.Jooby;
import io.jooby.OpenAPIModule;
import io.jooby.json.GsonModule;
import java.io.IOException;
import resources.ShoppingItemResource;
import resources.ShoppingListResource;

public class Server extends Jooby {

	public Server() {
		ShoppingDAO dao = new ShoppingDAO();

		// add support for JSON
		install(new GsonModule());

		mount(new ShoppingListResource(dao));
		mount(new ShoppingItemResource(dao));

        install(new OpenAPIModule());
        
        // provide our OAS specification to the swagger UI
   		assets("/openapi.json", "shopping.json");
   		assets("/openapi.yaml", "shopping.yaml");

		// redirect requests to / to /swagger
		get ("/", ctx -> ctx.sendRedirect("/swagger"));
	}

	public static void main(String[] args) throws IOException {
		new Server().start();
	}

}
