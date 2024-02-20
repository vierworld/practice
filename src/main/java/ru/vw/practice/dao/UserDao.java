package ru.vw.practice.dao;

import ru.vw.practice.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
  Optional<User> getByName(String userName);

  List<User> getAll();

  Optional<User> update(User user);

  Optional<User> insert(User user);

  void delete(List<String> userNames);
}
