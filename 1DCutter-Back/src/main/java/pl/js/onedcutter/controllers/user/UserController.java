package pl.js.onedcutter.controllers.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.js.onedcutter.dto.UserDTO;
import pl.js.onedcutter.dto.UserUpdateDTO;
import pl.js.onedcutter.services.UserService;


@CrossOrigin(origins = { "http://localhost:4200", "http://10.0.2.2:8080", "http://localhost", "https://api.cutter.grawires.pl", "http://217.182.73.214", "http://cutter.grawires.pl", "https://cutter.grawires.pl", "*" })
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser());
    }

    @PatchMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userUpdateDTO));
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> removeUser(@PathVariable String uuid) {
        userService.removeUser(uuid);
        return ResponseEntity.noContent().build();
    }
}
