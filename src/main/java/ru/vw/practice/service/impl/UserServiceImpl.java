package ru.vw.practice.service.impl;

import org.springframework.stereotype.Service;
import ru.vw.practice.dao.UserDao;
import ru.vw.practice.dto.User;
import ru.vw.practice.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  private final UserDao userDao;

  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Optional<User> getByName(String userName) {
    return userDao.getByName(userName);
  }

  @Override
  public List<User> getAll() {
    return userDao.getAll();
  }

  @Override
  public Optional<User> update(User user) {
    return userDao.update(user);
  }

  @Override
  public Optional<User> insert(User user) {
    return userDao.insert(user);
  }

  @Override
  public void delete(List<String> userNames) {
    userDao.delete(userNames);
  }
}
