package services;

import encapsulation.Comentario;
import util.BaseService;

import java.util.ArrayList;

public class ComentarioService {

  private final ArrayList<Comentario> comentarios;

  public ComentarioService() {
    this.comentarios = new ArrayList<>();
  }

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
