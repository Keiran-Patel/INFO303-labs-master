package auth;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.UserProfile;

public class RouteAuthoriser implements Authorizer<UserProfile> {

    private class OperationRole {

        private final String path;
        private final String httpMethod;
        private final Set<String> requiredRoles;

        public OperationRole(String path, String method, Set<String> requiredRoles) {
            this.path = path;
            this.httpMethod = method.toUpperCase();
            this.requiredRoles = requiredRoles;
        }

        public String getPath() {
            return path;
        }

        public String getMethod() {
            return httpMethod;
        }

        public Set<String> getRequiredRoles() {
            return requiredRoles;
        }

        public Boolean check(String requestPath, String requestMethod, Set<String> userRoles) {
            Set<String> matchedRoles = Sets.intersection(this.requiredRoles, userRoles);

            return requestPath.matches(this.path)
                    && requestMethod.equals(this.httpMethod)
                    && matchedRoles.size() > 0;
        }

    }

    private final Collection<OperationRole> operationRoles = new HashSet<>();

    public RouteAuthoriser addRoute(String path, String httpMethod, Set<String> requiredRoles) {
        operationRoles.add(new OperationRole(path, httpMethod, requiredRoles));
        return this;
    }

    @Override
    public boolean isAuthorized(WebContext wc, List<UserProfile> profiles) {
        String requestedPath = wc.getPath();
        String requestedMethod = wc.getRequestMethod();

        for (OperationRole opRole : operationRoles) {
            if (requestedPath.matches(opRole.getPath())
                    && requestedMethod.equals(opRole.getMethod())) {

                for (UserProfile profile : profiles) {
                    Set<String> userRoles = profile.getRoles();

                    if (opRole.check(requestedPath, requestedMethod, userRoles)) {
                        // found a match in roles - allow request
                        return true;
                    }
                }

                // no profiles had required roles - deny request
                return false;

            }

        }
        // route does not match any routes that have roles - deny request
        return false;
    }
}
