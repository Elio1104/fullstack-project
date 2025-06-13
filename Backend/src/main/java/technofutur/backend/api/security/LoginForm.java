package technofutur.backend.api.security;

import jakarta.validation.constraints.NotBlank;

public record LoginForm(
    @NotBlank String username,
    @NotBlank String password
) {
}
