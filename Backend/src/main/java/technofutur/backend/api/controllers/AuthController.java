package technofutur.backend.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technofutur.backend.api.security.LoginForm;
import technofutur.backend.api.security.RegisterForm;
import technofutur.backend.api.security.UserTokenDTO;
import technofutur.backend.bll.services.UserService;
import technofutur.backend.dal.entities.security.User;
import technofutur.backend.security.jwt.JwtUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<UserTokenDTO> login(@RequestBody LoginForm form) {
        User user = userService.login(form.username(), form.password());
        UserTokenDTO dto = UserTokenDTO.fromEntity(user);
        String token = jwtUtil.generateToken(user);
        dto.setToken(token);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserTokenDTO> register(@RequestBody RegisterForm form) {
        User user = this.userService.register(form, "USER");
        UserTokenDTO dto = UserTokenDTO.fromEntity(user);
        String token = jwtUtil.generateToken(user);
        dto.setToken(token);
        return ResponseEntity.ok(dto);
    }
}
