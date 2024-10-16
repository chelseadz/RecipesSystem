package recipes.Controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import recipes.Service.Users.UserService;
import recipes.Service.Users.AppUser;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AppUser appUser) {
        try {
            userService.registerUser(appUser);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Map.of("error", Objects.requireNonNull(e.getReason())));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "An unexpected error occurred: " + e.getMessage()));  // General error message
        }
    }
}

