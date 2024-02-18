package org.example.encapsulations;


// entidad
public class Comentario { // implements Serializable

  //id
  // que se genere
  private long id;
  private String comentario;

  // @manyToOne, a que 1 usuario puede tener muchos comentarios
  // el id del usuario
  // @joinColumn, el nombre de la columna que se va a crear
  private Usuario autor;

  // @manyToOne, a que 1 articulo puede tener muchos comentarios
  private Articulo articulo;

  // se va
  public Comentario(long id, String comentario, Usuario autor, Articulo articulo) {
    this.id = id;
    this.comentario = comentario;
    this.autor = autor;
    this.articulo = articulo;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public Usuario getAutor() {
    return autor;
  }

  public void setAutor(Usuario autor) {
    this.autor = autor;
  }
}
