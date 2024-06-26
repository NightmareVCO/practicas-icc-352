package encapsulation;
import jakarta.persistence.*;

import java.io.Serializable;
@Entity
public class Usuario implements Serializable {
  @Id
  private String username;
  private String nombre;
  private String password;
  private boolean admin;
  private boolean autor;
  private boolean active;
  @ManyToOne()
  private Foto foto;

  public Usuario() {
    this.active = true;
  }

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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Foto getFoto() {
    return foto;
  }

  public void setFoto(Foto foto) {
    this.foto = foto;
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
