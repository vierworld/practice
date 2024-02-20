package ru.vw.practice.dto;

public class User {
  private Long id;

  private String userName;

  public User(Long id, String userName) {
    this.id = id;
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return String.format("{id: %s, username: %s}", id, userName);
  }

  public static UserBuilder builder() {
    return new UserBuilder();
  }

  public static class UserBuilder {
    private Long id;
    private String userName;

    public UserBuilder userName(String userName) {
      this.userName = userName;

      return this;
    }

    public UserBuilder id(Long id) {
      this.id = id;
      return this;
    }


    public User build() {
      return new User(id, userName);
    }

  }
}
