package pl.js.onedcutter.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.js.onedcutter.dto.UserDTO;
import pl.js.onedcutter.dto.UserRegisterDTO;
import pl.js.onedcutter.dto.UserUpdateDTO;
import pl.js.onedcutter.exceptions.user.BadCredentialsException;
import pl.js.onedcutter.exceptions.user.UserDoesntExistsException;
import pl.js.onedcutter.exceptions.user.UserExistsException;
import pl.js.onedcutter.models.user.AuthRequest;
import pl.js.onedcutter.models.user.AuthResponse;
import pl.js.onedcutter.models.user.UserModel;
import pl.js.onedcutter.repo.UserRepo;
import pl.js.onedcutter.utility.JWTUtil;

import javax.swing.text.html.Option;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserService(UserRepo userRepo, JWTUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Logowanko
     *
     * @param aRequest
     * @return
     */
    public AuthResponse doLogin(AuthRequest aRequest) {

        UserModel userModel = userRepo.findByUsername(aRequest.getUsername());

        if (userModel != null) {
            if (passwordEncoder().matches(aRequest.getPassword(), userModel.getPassword())) {
                return new AuthResponse(jwtUtil.generateToken(userModel)); // git, zwroc token
            } else
                throw new BadCredentialsException("Bad credentials error.");
        } else {
            throw new UserDoesntExistsException("User doesnt exist.");
        }
    }

    /**
     * Rejestruje usera
     * 
     * @param UserRegisterDTO
     * @return UserDTO
     */
    public UserDTO addUser(UserRegisterDTO userRegisterDTO) {
        if (!userRegisterDTO.getUsername().equals("") && !userRegisterDTO.getPassword().equals("") && !userRegisterDTO.getEmail().equals("")) {
            if (!userRepo.existsByUsername(userRegisterDTO.getUsername()) && !userRepo.existsByEmail(userRegisterDTO.getEmail())) {
                UserModel userToSave = new UserModel(userRegisterDTO);
                userToSave.setPassword(passwordEncoder().encode(userRegisterDTO.getPassword()));
                userRepo.save(userToSave);

                userToSave.setActiveProjectId(userToSave.getSavedProjectModels().get(0).getId().intValue());
                userRepo.save(userToSave);
                return new UserDTO(userToSave);
            } else {
                throw new UserExistsException("User already exists!");
            }
        } else {
            throw new BadCredentialsException("Bad credentials!");
        }
    }


    public UserDTO getUser() {
        if( !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser") ){
            UserModel userDetails = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new UserDTO(userRepo.findByUsername(userDetails.getUsername()));
        }else throw new RuntimeException("Anonymous User dont have acces to this resource.");

    }


    public UserDTO updateUser(UserUpdateDTO userUpdateDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel userModel = (UserModel) userRepo.findByUsername(userDetails.getUsername());

        if (userRepo.findByUuid(userModel.getUuid()) != null) {
            userModel.setPhone(userUpdateDTO.getPhone());
            userModel.setWebsite(userUpdateDTO.getWebsite());

            if (userUpdateDTO.getactiveProjectId() != null) {
                userModel.setActiveProjectId(userUpdateDTO.getactiveProjectId());
            }

            userModel.setActiveProjectModel(userModel.getSavedProjectModels().stream()
                    .filter(e -> e.getId() == Long.valueOf(userModel.getActiveProjectId())).collect(Collectors.toList())
                    .get(0));

            userRepo.save(userModel);
            return new UserDTO(userModel);
        }
        throw new UserDoesntExistsException("Cannot update, user doesn't exist.");
    }


    public void removeUser(String uuid) {
        UserModel userModel = userRepo.findByUsername(
                ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        if ((userRepo.findByUuid(uuid) != null) && userModel.getUuid().equals(uuid)) {
            userRepo.deleteByUuid(uuid);
        } else
            throw new UserDoesntExistsException("No such user, or you don't have access to delete this user.");
    }


    public void saveUserEntity(UserModel userModel) {
        userRepo.save(userModel);
    }
}
