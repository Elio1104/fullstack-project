package technofutur.backend.api.security;

import jakarta.validation.constraints.NotBlank;

public record RegisterForm(
        @NotBlank String username,
        @NotBlank String password
) {
}
