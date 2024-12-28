package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.User;

import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> getUser(String email, String password);

}
