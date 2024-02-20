package ru.vw.practice.runner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.vw.practice.dto.User;
import ru.vw.practice.service.UserService;

import java.util.List;
import java.util.Optional;

public class RunnerClass {
  public static String TEST_USERNAME = "Test User";
  public static String TEST_USERNAME_SECOND = "Test User Second";
  public static String TEST_USERNAME_AFTER = "Test User after update";

  public static void RunPractice4() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext("ru.vw.practice");
    UserService userService = applicationContext.getBean(UserService.class);

    userService.delete(List.of(TEST_USERNAME, TEST_USERNAME_SECOND, TEST_USERNAME_AFTER));

    userService.insert(User.builder().userName(TEST_USERNAME).build());
    userService.insert(User.builder().userName(TEST_USERNAME_SECOND).build());
    Optional<User> user = userService.getByName(TEST_USERNAME);

    System.out.println(user);

    user.ifPresent(usr -> {
      usr.setUserName(TEST_USERNAME_AFTER);
      System.out.println(userService.update(usr));
    });

    System.out.println(userService.getAll());
  }
}
