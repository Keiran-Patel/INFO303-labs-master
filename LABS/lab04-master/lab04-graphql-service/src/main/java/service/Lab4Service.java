package service;

import dao.InventoryDAO;
import graphql.InventoryWiring;
import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.graphql.GraphQLModule;
import io.jooby.graphql.GraphiQLModule;
import io.jooby.json.GsonModule;
import java.io.IOException;

public class Lab4Service extends Jooby {

	public Lab4Service() {
		InventoryDAO dao = new InventoryDAO();

		setServerOptions(new ServerOptions().setPort(8080));

		install(new GsonModule());
		install(new GraphQLModule(new InventoryWiring(dao).getWiring()));
		install(new GraphiQLModule());

		get("/", (ctx) -> ctx.sendRedirect("/graphql"));
	}

	public static void main(String[] args) throws IOException {
		new Lab4Service().start();
	}

}
