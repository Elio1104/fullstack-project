package technofutur.backend.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import technofutur.backend.api.user.UserGraphQLDTO;
import technofutur.backend.bll.services.user.UserGraphQLService;

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
}
