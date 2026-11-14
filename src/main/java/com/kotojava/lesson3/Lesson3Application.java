package com.kotojava.lesson3;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotojava.lesson3.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class Lesson3Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Lesson3Application.class, args);
		System.out.println(context.getBean(UserRepository.class).getUsers());
        UserRepository repository = context.getBean(UserRepository.class);

    }

}




