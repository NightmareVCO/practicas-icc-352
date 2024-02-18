package org.example.encapsulations;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity // Indica que la clase es una entidad
public class Usuario implements Serializable { // Implementa la interfaz Serializable

  @Id // Indica que el atributo es una clave primaria
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor de la clave primaria es autogenerado
  private String username;
  private String nombre;
  private String password;
  private boolean admin;
  private boolean autor;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
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

  public boolean isAutor() {
    return autor;
  }

  public void setAutor(boolean autor) {
    this.autor = autor;
  }

  public String toString() {
    return "Usuario{" +
      "username='" + username + '\'' +
      ", nombre='" + nombre + '\'' +
      ", password='" + password + '\'' +
      ", admin=" + admin +
      ", autor=" + autor +
      '}';
  }
}
