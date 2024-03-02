package services;
import encapsulation.Articulo;
import encapsulation.Comentario;
import encapsulation.Usuario;
import util.BaseServiceDatabase;
import java.util.List;

public class ComentarioService extends BaseServiceDatabase<Comentario> {
  public ComentarioService() {
      super(Comentario.class);
  }
  public Comentario find(String comentarioId) {
    return this.dbFind(comentarioId);
  }
  public List<Comentario> findAll(){
    return this.dbFindAll();
  }

  public Comentario create(String contenido, Usuario autor, Articulo articulo){
    Comentario comentario = new Comentario();
    comentario.setComentario(contenido);
    comentario.setAutor(autor);
    comentario.setArticulo(articulo);

    return this.createInDatabase(comentario);
  }
  public Comentario createInDatabase(Comentario comentario){
    return this.dbCreate(comentario);
  }
  public Comentario modify(Comentario comentario){
    return this.dbModify(comentario);
  }
  public boolean delete(String comentarioId){
    return this.dbRemove(comentarioId);
  }
}