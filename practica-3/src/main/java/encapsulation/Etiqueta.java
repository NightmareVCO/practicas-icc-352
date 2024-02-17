package encapsulation;

public class Etiqueta {
  private long id;
  private String nombre;

  public Etiqueta(long id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String toString() {
    return "Etiqueta{" +
      "id=" + id +
      ", nombre='" + nombre + '\'' +
      '}';
  }
}
