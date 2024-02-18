package org.example.encapsulations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Etiqueta implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String nombre;

  public Etiqueta(long id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  // Constructor vacío (bien)
  public Etiqueta() {

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
