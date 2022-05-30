package auth;

import java.util.Set;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.credentials.extractor.BasicAuthExtractor;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.definition.CommonProfileDefinition;

public class CatalogueAuthenticator implements Authenticator<Credentials> {

    @Override
    public void validate(Credentials creds, WebContext ctx) {
        // extract the username and password from the BAA token
        UsernamePasswordCredentials baCreds = new BasicAuthExtractor().extract(ctx).get();

        CommonProfile profile = this.authenticate(baCreds.getUsername(), baCreds.getPassword());

        if (profile != null) {
            creds.setUserProfile(profile);
        } else {
            throw new CredentialsException("Invalid credentials");
        }

    }

    public CommonProfile authenticate(String username, String password) {

        if (username.equals("user") && password.equals("user")) {

            CommonProfile profile = new CommonProfile();

            profile.setId(username);
            profile.setRoles(Set.of("AUTHENTICATED_USER"));
            profile.addAttribute(CommonProfileDefinition.FIRST_NAME, "Boris");
            profile.addAttribute(CommonProfileDefinition.FAMILY_NAME, "McNoris");
            profile.addAttribute(CommonProfileDefinition.EMAIL, "boris@example.com");

            return profile;
        } else if (username.equals("manager") && password.equals("manager")) {

            CommonProfile profile = new CommonProfile();

            profile.setId(username);
            profile.setRoles(Set.of("MANAGER", "AUTHENTICATED_USER"));
            profile.addAttribute(CommonProfileDefinition.FIRST_NAME, "Doris");
            profile.addAttribute(CommonProfileDefinition.FAMILY_NAME, "Delores");
            profile.addAttribute(CommonProfileDefinition.EMAIL, "doris@example.com");

            return profile;

        } else {
            return null;
        }
    }
}
