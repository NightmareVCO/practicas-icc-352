package services;

import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import util.BaseServiceDatabase;

import java.util.List;

public class UserService extends BaseServiceDatabase<User> {

  public UserService() {
    super(User.class);
  }

  public User find(String username) {
    return this.dbFind(username);
  }

  public List<User> findAll() {
    return this.dbFindAll();
  }

  public User create(String username, String nombre, String password, boolean admin, boolean pollster) {
    User user = new User();
    user.setUsername(username);
    user.setName(nombre);
    user.setPassword(password);
    user.setAdmin(admin);
    user.setPollster(pollster);
    user.setActive(true);

    return this.createInDatabase(user);
  }

  public User createInDatabase(User user) {
    return this.dbCreate(user);
  }

  public User modify(String username, String nombre, String password, boolean admin, boolean autor) {
    User user = this.find(username);
    user.setName(nombre);
    user.setPassword(password);
    user.setAdmin(admin);
    user.setPollster(autor);

    return this.dbModify(user);
  }
  public User modifyInDatabase(User user) {
    return this.dbModify(user);
  }

  public void desactivate(String username) {
    User user = this.find(username);
    user.setActive(false);
    this.dbModify(user);
  }

  public boolean delete(String username) {
    return this.dbRemove(username);
  }

  public boolean checkPassword(String username, String password) {
    User user = this.find(username);
    return user != null && user.getPassword().equals(password);
  }

  public User findByUsername(String username) {
    try (EntityManager em = getEntityManager()) {
      Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
      query.setParameter("username", username);
      return (User) query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }
}
