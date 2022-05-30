package server;

import dao.ShoppingDAO;
import domain.ShoppingItem;
import io.jooby.Jooby;
import io.jooby.OpenAPIModule;
import io.jooby.ServerOptions;
import io.jooby.json.GsonModule;
import java.io.IOException;
import resources.ShoppingItemResource;
import resources.ShoppingListResource;

public class RestServer extends Jooby {

	public RestServer() {
		ShoppingDAO dao = new ShoppingDAO();

		dao.addItem(new ShoppingItem("Cereal", "Something healthy"));
		dao.addItem(new ShoppingItem("Peanut butter", "Crunchy"));
		dao.addItem(new ShoppingItem("Wine", "Something cheap"));
		dao.addItem(new ShoppingItem("Laundry detergent", "For front loader"));

		setServerOptions(new ServerOptions().setPort(8081));

		// add support for JSON
		install(new GsonModule());

		mount(new ShoppingListResource(dao));
		mount(new ShoppingItemResource(dao));

		install(new OpenAPIModule());

		// provide our OAS specification to the swagger UI
		assets("/openapi.json", "shopping.json");
		assets("/openapi.yaml", "shopping.yaml");

		// redirect requests to / to /swagger
		get("/", ctx -> ctx.sendRedirect("/swagger"));
	}

	public static void main(String[] args) throws IOException {
		new RestServer().start();
	}

}
