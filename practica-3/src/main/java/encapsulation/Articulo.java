package encapsulation;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Articulo implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String titulo;
  @Column(length = 1000)
  private String cuerpo;
  @ManyToOne
  private Usuario autor;
  private Date fecha;
  @OneToMany(fetch = FetchType.EAGER)
  private List<Comentario> comentarios;
  @OneToMany(fetch = FetchType.EAGER)
  private Set<Etiqueta> etiquetas;


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

  public List<Comentario> getComentarios() {
    return comentarios;
  }

  public void setComentarios(List<Comentario> comentarios) {
    this.comentarios = comentarios;
  }

  public Set<Etiqueta> getEtiquetas() {
    return etiquetas;
  }

  public void setEtiquetas(Set<Etiqueta> etiquetas) {
    this.etiquetas = etiquetas;
  }
}
