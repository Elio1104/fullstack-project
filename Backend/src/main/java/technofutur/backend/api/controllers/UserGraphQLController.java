package technofutur.backend.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import technofutur.backend.api.user.UserGraphQLDTO;
import technofutur.backend.bll.services.user.UserGraphQLService;
import technofutur.backend.dal.entities.security.UserAuth;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserGraphQLController {
    private final UserGraphQLService userGraphQLService;

    @QueryMapping
    public List<UserGraphQLDTO> users() {
        return userGraphQLService.getAllUsers();
    }

    @QueryMapping
    public UserGraphQLDTO user(@Argument Long id) {
        return userGraphQLService.getUserById(id);
    }

    @QueryMapping
    public UserGraphQLDTO currentUser() {
        UserAuth authenticatedUser = getAuthenticatedUser();
        return userGraphQLService.getCurrentUser(authenticatedUser);
    }

    private UserAuth getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserAuth) {
            return (UserAuth) authentication.getPrincipal();
        }
        throw new IllegalStateException("User not authenticated");
    }
}
