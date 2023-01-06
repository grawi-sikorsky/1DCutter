package pl.js.onedcutter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import pl.js.onedcutter.repo.UserRepo;

@CrossOrigin(origins = "*")
@SpringBootApplication
public class OnedcutterApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnedcutterApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepo userRepo) {
			return args -> {
					userRepo.findAll().forEach(System.out::println);
			};
	}
}

