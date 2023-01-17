package pl.js.onedcutter.controllers.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.js.onedcutter.dto.UserDTO;
import pl.js.onedcutter.dto.UserRegisterDTO;
import pl.js.onedcutter.models.user.AuthRequest;
import pl.js.onedcutter.models.user.AuthResponse;
import pl.js.onedcutter.services.UserService;


@CrossOrigin(origins = { "http://localhost:4200", "http://10.0.2.2:8080", "http://localhost", "https://api.cutter.grawires.pl", "http://217.182.73.214", "http://cutter.grawires.pl", "https://cutter.grawires.pl", "http://api.cutter.grawires.pl", "*" })
@RestController
@RequestMapping("/user")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> registerForm(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userRegisterDTO));
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<AuthResponse> authenticateRequest(@RequestBody AuthRequest aRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.doLogin(aRequest));
    }
}
