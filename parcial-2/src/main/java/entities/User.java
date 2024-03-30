package entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "`User`")
public class User implements Serializable {

  @Id
  private String username;
  private String name;
  private String email;
  private String password;
  private boolean admin;
  private boolean pollster;
  private boolean active;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public boolean isPollster() {
    return pollster;
  }

  public void setPollster(boolean pollster) {
    this.pollster = pollster;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
