package encapsulation;
import java.util.ArrayList;
import java.util.Date;

public class Articulo {
  private long id;
  private String titulo;
  private String cuerpo;
  private Usuario autor;
  private Date fecha;
  private ArrayList<Comentario> comentarios;
  private ArrayList<Etiqueta> etiquetas;

  public Articulo(long id, String titulo, String cuerpo, Usuario autor, Date fecha, ArrayList<Etiqueta> etiquetas) {
    this.id = id;
    this.titulo = titulo;
    this.cuerpo = cuerpo;
    this.autor = autor;
    this.fecha = fecha;
    this.etiquetas = etiquetas;
    this.comentarios = new ArrayList<Comentario>();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getCuerpo() {
    return cuerpo;
  }

  public void setCuerpo(String cuerpo) {
    this.cuerpo = cuerpo;
  }

  public Usuario getAutor() {
    return autor;
  }

  public void setAutor(Usuario autor) {
    this.autor = autor;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public ArrayList<Comentario> getComentarios() {
    return comentarios;
  }

  public void setComentarios(ArrayList<Comentario> comentarios) {
    this.comentarios = comentarios;
  }

  public ArrayList<Etiqueta> getEtiquetas() {
    return etiquetas;
  }

  public void setEtiquetas(ArrayList<Etiqueta> etiquetas) {
    this.etiquetas = etiquetas;
  }

  public String toString() {
    return "Articulo{" +
      "id=" + id +
      ", titulo='" + titulo + '\'' +
      ", cuerpo='" + cuerpo + '\'' +
      ", autor=" + autor +
      ", fecha=" + fecha +
      ", comentarios=" + comentarios +
      ", etiquetas=" + etiquetas +
      '}';
  }
}
