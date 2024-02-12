package encapsulation;

public class Usuario {
  private String username;
  private String nombre;
  private String password;

  public Usuario(String username, String nombre, String password) {
    this.username = username;
    this.nombre = nombre;
    this.password = password;
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

  public String toString() {
    return "Usuario{" +
      "username='" + username + '\'' +
      ", nombre='" + nombre + '\'' +
      ", password='" + password + '\'' +
      '}';
  }
}
