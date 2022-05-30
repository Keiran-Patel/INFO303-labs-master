package service;

import auth.RouteAuthoriser;
import dao.CatalogueDAO;
import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.json.GsonModule;
import io.jooby.pac4j.Pac4jModule;
import java.util.Set;
import org.pac4j.oidc.client.KeycloakOidcClient;
import org.pac4j.oidc.config.KeycloakOidcConfiguration;
import resource.CatalogueResource;

public class Lab7Server extends Jooby {

    public Lab7Server() {

        setServerOptions(new ServerOptions().setPort(8081));

        RouteAuthoriser routeAuthoriser = new RouteAuthoriser()
                .addRoute("/api/catalogue", "POST", Set.of("MANAGER"))
                .addRoute("/api/catalogue", "GET", Set.of("MANAGER", "AUTHENTICATED_USER"))
                .addRoute("/api/catalogue/product/.*", "DELETE", Set.of("MANAGER"))
                .addRoute("/api/catalogue/product/.*", "GET", Set.of("MANAGER", "AUTHENTICATED_USER"));
//        Pac4jModule pac4j = new Pac4jModule().client(routeAuthoriser, joobyCfg -> {
//            return new DirectBasicAuthClient(new CatalogueAuthenticator());
//        });
        Pac4jModule pac4j = new Pac4jModule().client(routeAuthoriser, joobyCfg -> {
            KeycloakOidcConfiguration config = new KeycloakOidcConfiguration();
            config.setRealm("lab7_realm");
            config.setClientId("lab7_client");
            config.setBaseUri("http://localhost:8080");
            config.setSecret("YXUhI4sUm4FFUfL1O3cL5qhOH67JFzSD");
            KeycloakOidcClient client = new KeycloakOidcClient(config);
            return client;
        });
        CatalogueDAO dao = new CatalogueDAO();

        install(new GsonModule());
        install(pac4j);

        mount(new CatalogueResource(dao));

    }

    public static void main(String[] args) {
        new Lab7Server().start();
    }

}
