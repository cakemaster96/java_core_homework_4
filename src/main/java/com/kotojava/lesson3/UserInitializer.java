package com.kotojava.lesson3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotojava.lesson3.model.User;
import com.kotojava.lesson3.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.util.List;

@Component
public class UserInitializer implements CommandLineRunner {
    private final UserRepository repository;

    public UserInitializer(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.getUsers().isEmpty()) {

//            Faker faker = new Faker();
//
//            for (int i = 0; i < 100; i++) {
//                User user = User.builder()
//                        .name(faker.name().firstName())
//                        .surname(faker.name().lastName())
//                        .age(faker.number().numberBetween(18, 81))
//                        .country(faker.address().country())
//                        .profession(faker.job().title())
//                        .email("user" + (i + 1) + "@example.com")
//                        .build();
//
//                repository.save(user);
//            }
//
//            System.out.println("Создано 100 пользователей");


            String url = "https://gist.githubusercontent.com/isicju/f40e58017d64533a2660cf04285f6cb9/raw/5fde264d0758aab1df4afe92f33bd83ad3d7b9ad/gistfile1.txt";
            RestClient client = RestClient.create();
            String data = client.get()
                    .uri(url)
                    .retrieve()
                    .body(String.class);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            User[] users = mapper.readValue(data, User[].class);

            for (int i = 0; i < 100; i++) {
                repository.save(users[i]);
            }



        }
    }


}
