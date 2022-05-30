package service;

import dao.ShoppingDAO;
import domain.ShoppingItem;
import graphql.ShoppingWiring;
import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.graphql.GraphQLModule;
import io.jooby.graphql.GraphiQLModule;
import io.jooby.json.GsonModule;
import java.io.IOException;

public class GraphQLServer extends Jooby {

	public GraphQLServer() {
		ShoppingDAO dao = new ShoppingDAO();

		dao.addItem(new ShoppingItem("Beer", "12 pack of something tasty"));
		dao.addItem(new ShoppingItem("Chicken", "500 grams for stir fry"));
		dao.addItem(new ShoppingItem("Bread", "Something with whole grains"));
		dao.addItem(new ShoppingItem("Veges", "Spuds, onions, and carrots"));

		setServerOptions(new ServerOptions().setPort(8083));

		install(new GsonModule());
		install(new GraphQLModule(new ShoppingWiring(dao).getWiring()));
		install(new GraphiQLModule());

		get("/", (ctx) -> ctx.sendRedirect("/graphql"));
	}

	public static void main(String[] args) throws IOException {
		System.out.println("\nGraphQL Service starting on http://localhost:8083/graphql\n");
		new GraphQLServer().start();
	}

}
