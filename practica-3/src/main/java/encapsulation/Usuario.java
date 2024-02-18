package encapsulation;

public class Usuario {
  private String username;
  private String nombre;
  private String password;
  private boolean admin;
  private boolean autor;

  public Usuario(String username, String nombre, String password, boolean admin, boolean autor) {
    this.username = username;
    this.nombre = nombre;
    this.password = password;
    this.admin = admin;
    this.autor = autor;
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
