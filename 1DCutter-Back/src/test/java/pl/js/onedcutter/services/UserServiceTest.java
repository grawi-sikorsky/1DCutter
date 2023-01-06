package pl.js.onedcutter.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.js.onedcutter.dto.UserRegisterDTO;
import pl.js.onedcutter.models.user.UserModel;
import pl.js.onedcutter.repo.UserRepo;
import pl.js.onedcutter.utility.JWTUtil;
import pl.js.onedcutter.exceptions.user.BadCredentialsException;
import pl.js.onedcutter.exceptions.user.UserExistsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private PasswordEncoder pEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void doLogin() {
    }

    @Test
    void addUser_shouldThrow_BadCredentialsException() {
        UserRegisterDTO testUserDTO = new UserRegisterDTO();
        testUserDTO.setUsername("");
        testUserDTO.setPassword("");
        testUserDTO.setEmail("");
        assertThrows( BadCredentialsException.class, () -> { userService.addUser(testUserDTO); } );
    }
    @Test

    void addUser_shouldThrow_UserExistsException_whenUsernameExists() {
        UserRegisterDTO testUserDTO = new UserRegisterDTO();
        testUserDTO.setUsername("testuser");
        testUserDTO.setPassword("testpass");
        testUserDTO.setEmail("testemail@email.com");

        Mockito.when( userRepo.existsByUsername( testUserDTO.getUsername() ) ).thenReturn(true);

        assertThrows( UserExistsException.class, () -> { userService.addUser(testUserDTO); } );
    }

    @Test
    void addUser_shouldThrow_UserExistsException_whenEmailExists() {
        UserRegisterDTO testUserDTO = new UserRegisterDTO();
        testUserDTO.setUsername("testuser");
        testUserDTO.setPassword("testpass");
        testUserDTO.setEmail("testemail@email.com");

        Mockito.when( userRepo.existsByEmail( testUserDTO.getEmail() ) ).thenReturn(true);

        assertThrows( UserExistsException.class, () -> { userService.addUser(testUserDTO); } );
    }

    @Test
    void addUser_should_AddUserToDB() {
//        UserRegisterDTO testUserDTO = new UserRegisterDTO();
//        testUserDTO.setUsername("testuser");
//        testUserDTO.setPassword("testpass");
//        testUserDTO.setEmail("testemail@email.com");
//
//        Mockito.when( userRepo.existsByEmail( testUserDTO.getEmail() ) ).thenReturn(false);
//        Mockito.when( userRepo.existsByUsername( testUserDTO.getUsername() ) ).thenReturn(false);
//        //Mockito.when( pEncoder.encode(testUserDTO.getPassword()) ).thenReturn("somePasswordString");
//
//        //userToSave.setPassword(pEncoder.encode(userRegisterDTO.getPassword()));
//
//        userService.addUser(testUserDTO);
//
//        Mockito.verify( userRepo, Mockito.times(2) ).save(new UserModel(testUserDTO));
    }

    @Test
    void getUser() {
        UserModel testUser = new UserModel();
        testUser.setUsername("testuser");
        testUser.setNumberOfSavedItems(5);

        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(testUser);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(userRepo.findByUsername("testuser")).thenReturn(testUser);

        userService.getUser();

        Mockito.verify( userRepo, Mockito.times(1) ).findByUsername(testUser.getUsername());
    }

    @Test
    void updateUser() {
    }

    @Test
    void removeUser() {
    }

    @Test
    void saveUserEntity() {
    }
}