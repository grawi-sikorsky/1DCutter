package pl.printo3d.waluty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import pl.printo3d.waluty.repository.UserService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

  private UserService uService;

  @Autowired
  public SecurityConfig(UserService uService) {
    this.uService = uService;
  }

  public SecurityConfig()
  {
    
  }

/*
  @Bean
  public UserDetailsService userDetailsService()
  {
    UserDetails userDetails = User.withDefaultPasswordEncoder()
    .username("kloc")
    .password("kloc")
    .roles("KIEP")
    .build();

    return new InMemoryUserDetailsManager(userDetails);
  }
*/
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception
  {
    auth.userDetailsService(uService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
    .antMatchers("/login","/img/**","/css/**").permitAll()
    .antMatchers("/register", "/img/**","/css/**").permitAll()
    .antMatchers("/1dcut").permitAll()
    .antMatchers("/").permitAll()
    .anyRequest().hasAuthority("KIEP");


    http.formLogin().permitAll()
      .loginPage("/login").permitAll()
      .defaultSuccessUrl("/", true)
      .and()
      .logout().permitAll()
      .logoutSuccessUrl("/")
      .deleteCookies("JSESSIONID");

    http.csrf().disable();

  }
}
