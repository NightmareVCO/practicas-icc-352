package org.example.services;
import org.example.encapsulations.Comentario;

import java.util.ArrayList;

//borrar el arreglo

//implementar el BaseServiceDatabase
public class ComentarioService {

  // se va
  private final ArrayList<Comentario> comentarios;

  public ComentarioService() {
    this.comentarios = new ArrayList<>(); // se va
  }


  // todos los metodos se van
  public ArrayList<Comentario> findAll() {
    return comentarios;
  }

  public Comentario findById(Long id) {
    Comentario comentario = null;

    for (Comentario c : comentarios)
      if (c.getId() == id)
        comentario = c;

    return comentario;
  }

  public Comentario insert(Comentario entity) {
    comentarios.add(entity);
    return null;
  }

  public Comentario update(Comentario entity) {
    return null;
  }

  public Comentario delete(Comentario entity) {
    return null;
  }

  public Comentario deleteById(Long id) {
    return null;
  }

  public Comentario deleteAll() {
    return null;
  }

  public Long getNextId() {
    return (long) (comentarios.size() + 1);
  }
}
