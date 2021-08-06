package pl.printo3d.waluty.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

  private UserRepo userRepo;

  @Bean
  public PasswordEncoder passwordEncoder() 
  {
      return new BCryptPasswordEncoder();
  }

  @Autowired
  private PasswordEncoder pEncoder;

  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //return userRepo.findByUsername(username);
    return userRepo.findByUsername(username);
  }

  public boolean addUser(UserEntity userEntity)
  {
    System.out.println("Adduser..");

    // TODO: Ustawic role dynamicznie?
    userEntity.setRole("KIEP");
    userEntity.setPasswd(pEncoder.encode(userEntity.getPasswd()));
  
    userRepo.save(userEntity);
    return true;
  }

  public boolean updateUser(UserEntity userEntity)
  {
    System.out.println("Update User..");
    
    //1 sprawdzic czy user istnieje
    //2 zmodyfikowac wpisy w db

    //1
    //userRepo.findByUsername(userEntity.getUsername())

    userRepo.save(userEntity);

    return true;
  }
}