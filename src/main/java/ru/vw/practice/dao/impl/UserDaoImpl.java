package ru.vw.practice.dao.impl;

import com.zaxxer.hikari.pool.HikariPool;
import org.springframework.stereotype.Repository;
import ru.vw.practice.dao.UserDao;
import ru.vw.practice.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

  //Таблица была в схеме CSEP создана, можно убрать схему
  private final static String TABLE_NAME = "CSEP.USERS";

  private final static String SELECT_QUERY = "SELECT ID, USERNAME FROM %s WHERE USERNAME = ?".formatted(TABLE_NAME);
  private final static String SELECT_ALL_QUERY = "SELECT ID, USERNAME FROM %s".formatted(TABLE_NAME);
  private final static String UPDATE_QUERY = "UPDATE %s SET USERNAME = ? WHERE ID = ? RETURNING ID, USERNAME".formatted(TABLE_NAME);
  private final static String INSERT_QUERY = "INSERT INTO %s (USERNAME) VALUES (?) RETURNING ID, USERNAME".formatted(TABLE_NAME);
  private final static String DELETE_QUERY = "DELETE FROM %s WHERE USERNAME IN (SELECT TT FROM UNNEST(?) TT)".formatted(TABLE_NAME);

  private final HikariPool hikariPool;

  public UserDaoImpl(HikariPool hikariPool) {
    this.hikariPool = hikariPool;
  }

  private User mapRow(ResultSet rs) throws SQLException {
    return User.builder().id(rs.getLong("ID")).userName(rs.getString("USERNAME")).build();
  }

  @Override
  public Optional<User> getByName(String userName) {
    try (Connection con = hikariPool.getConnection();
         PreparedStatement pst = con.prepareStatement(SELECT_QUERY)) {

      pst.setString(1, userName);
      try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRow(rs));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }

  @Override
  public List<User> getAll() {
    List<User> userList = new ArrayList<>();
    try (Connection con = hikariPool.getConnection();
         PreparedStatement pst = con.prepareStatement(SELECT_ALL_QUERY)) {
      try (ResultSet rs = pst.executeQuery()) {
        while (rs.next()) {
          userList.add(mapRow(rs));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return userList;
  }

  @Override
  public Optional<User> update(User user) {
    try (Connection con = hikariPool.getConnection();
         PreparedStatement pst = con.prepareStatement(UPDATE_QUERY)) {
      pst.setString(1, user.getUserName());
      pst.setLong(2, user.getId());

      try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRow(rs));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }

  @Override
  public Optional<User> insert(User user) {
    try (Connection con = hikariPool.getConnection();
         PreparedStatement pst = con.prepareStatement(INSERT_QUERY)) {
      pst.setString(1, user.getUserName());

      try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRow(rs));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }

  @Override
  public void delete(List<String> userNames) {
    try (Connection con = hikariPool.getConnection();
         PreparedStatement pst = con.prepareStatement(DELETE_QUERY)) {
      pst.setArray(1, con.createArrayOf("TEXT", userNames.toArray()));
      pst.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
