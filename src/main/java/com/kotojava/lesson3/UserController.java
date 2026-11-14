package com.kotojava.lesson3;

import com.kotojava.lesson3.model.User;
import com.kotojava.lesson3.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = User.builder()
                .id(user.getId())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .city(user.getCity())
                .address(user.getAddress())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();

        userService.addUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/by-index")
    public ResponseEntity<User> getUserByIndex(@RequestParam int index) {
        return ResponseEntity.ok(userService.getUserByIndexWithoutSorting(index));
    }

    @GetMapping("/sorted-by-name")
    public ResponseEntity<List<User>> getUsersSortedByName() {
        return ResponseEntity.ok(userService.getUsersSortedByName());
    }

    @GetMapping("/by-birth-date")
    public ResponseEntity<List<User>> getUsersByBirthDate(@RequestParam LocalDate birthDate) {
        return ResponseEntity.ok(userService.getUsersByBirthDate(birthDate));
    }

    @GetMapping("/older-than")
    public ResponseEntity<List<User>> getUsersOlderThan(@RequestParam LocalDate birthDate) {
        return ResponseEntity.ok(userService.getAllUsersOlderThan(birthDate));
    }

    @GetMapping("/between-birth-dates")
    public ResponseEntity<List<User>> getAllUsersBetweenBirthDates(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return ResponseEntity.ok(userService.getAllUsersBetweenBirthDates(from, to));
    }

    @GetMapping("/by-year")
    public ResponseEntity<List<User>> getUsersByTheYear(@RequestParam int year) {
        return ResponseEntity.ok(userService.getUsersByTheYear(year));
    }

    @GetMapping("/by-email")
    public ResponseEntity<List<User>> getUsersBy3EmailChars(@RequestParam String chars) {
        return ResponseEntity.ok(userService.getUsersBy3EmailChars(chars));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<String> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }




    @GetMapping("/video")
    public ResponseEntity<Resource> getVideo() {
        Resource resource = new ClassPathResource("video.mp4");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .body(resource);
    }





    @GetMapping("/video/download")
    public ResponseEntity<Resource> downloadVideo() {
        Resource resource = new ClassPathResource("video.mp4");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("video.mp4")
                                .build()
                                .toString()
                )
                .body(resource);
    }

}