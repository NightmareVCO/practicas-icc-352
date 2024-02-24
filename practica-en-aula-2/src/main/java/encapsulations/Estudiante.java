package encapsulations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Estudiante implements Serializable {

  @Id
  private int matricula;
  private String nombre;
  private String carrera;

  public int getMatricula() {
    return matricula;
  }

  public void setMatricula(int matricula) {
    this.matricula = matricula;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getCarrera() {
    return carrera;
  }

  public void setCarrera(String carrera) {
    this.carrera = carrera;
  }

  public void mezclar(Estudiante e) {
    matricula = e.getMatricula();
    nombre = e.getNombre();
    carrera = e.getCarrera();
  }
}
