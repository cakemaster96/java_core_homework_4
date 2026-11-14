package com.kotojava.lesson3;

import com.kotojava.lesson3.model.User;
import com.kotojava.lesson3.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {

        userRepository.save(user);
    }

    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUserByIndexWithoutSorting(int index) {
        if (index < 0 ) {
            throw new IllegalArgumentException("Index cannot be less than zero");
        }
        if (index >= userRepository.getUsers().size()) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }

        List<User> users = new ArrayList<>(userRepository.getUsers());
        return users.get(index);
    }

    public List<User>  getUsersSortedByName() {
        List<User> users = new ArrayList<>(userRepository.getUsers());
       return users.stream().sorted(Comparator.comparing(User::getName)).toList();
    }

    public List<User> getUsersByBirthDate(LocalDate birthDate) {
        return userRepository.getUsers().stream().filter(user -> user.getBirthDate().equals(birthDate)).toList();
    }

    public List<User> getAllUsersOlderThan(LocalDate birthDate) {
        return userRepository.getUsers().stream()
                .filter(user -> user.getBirthDate().isBefore(birthDate))
                .toList();
    }

    public List<User> getAllUsersBetweenBirthDates(LocalDate birthDateFrom, LocalDate birthDateTo) {
        if (birthDateTo.isBefore(birthDateFrom)) {
            throw new IllegalArgumentException("Поле \"с даты\" должно быть раньше чем \"Поле \\\"по дату\\\" " + birthDateTo);
        }
        return userRepository.getUsers().stream()
                .filter(user -> !user.getBirthDate().isBefore(birthDateFrom) && !user.getBirthDate().isAfter(birthDateTo))
                .toList();
    }

    public List<User> getUsersByTheYear(int year) {
        return userRepository.getUsers().stream()
                .filter(user -> user.getBirthDate().getYear() == year)
                .toList();
    }

    public List<User> getUsersBy3EmailChars(String threeMatchingChars) {
        if (threeMatchingChars.length() != 3) {
            throw new IllegalArgumentException("String must contain exactly 3 characters!");};
        return userRepository.getUsers().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(threeMatchingChars.toLowerCase()))
                .toList();
    }
}
