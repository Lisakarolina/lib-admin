package com.example.libadmin;

import com.example.libadmin.domain.Book;
import com.example.libadmin.repository.BookRepository;
import com.example.libadmin.repository.RoleRepository;
import com.example.libadmin.repository.UserRepository;
import com.example.libadmin.service.BookService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import com.example.libadmin.domain.Role;
import com.example.libadmin.domain.User;

import java.util.List;
import java.io.InputStream;
import java.io.IOException;
import java.util.Optional;

@RestController
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.libadmin"})
public class LibAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibAdminApplication.class, args);
	}


	@Bean
	CommandLineRunner runner(BookService bookService){
		return args -> {
			// read JSON and load json
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			TypeReference<List<Book>> typeReference = new TypeReference<List<Book>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/test_books.json");
			try {
				List<Book> questions = mapper.readValue(inputStream,typeReference);
				bookService.save(questions);
				System.out.println("books Saved!");
			} catch (IOException e){
				System.out.println("Unable to save book: " + e.getMessage());
			}
		};
	}


	@Bean
	CommandLineRunner runner2(RoleRepository roleRepository, UserRepository userRepository, BookRepository bookRepository){
		return args -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String secret = encoder.encode("password");
			// "{bcrypt}" +

			// new user
			Role userRole = new Role("ROLE_USER");
			roleRepository.save(userRole);
			User user = new User("user@email.com", secret, true);
			user.addRole(userRole);
			Book favBook = bookRepository.findById(1L).orElse(null);
			//favBook.setTitle("Favorite Book");
			//favBook.setAuthor("Fav Author");
			//bookRepository.save(favBook);
			user.addFavorite(favBook);
			userRepository.save(user);

		};
	}

}
