package services;
import encapsulation.Articulo;
import encapsulation.Comentario;
import encapsulation.Etiqueta;
import encapsulation.Usuario;
import jakarta.persistence.TypedQuery;
import util.BaseServiceDatabase;

import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticuloService extends BaseServiceDatabase<Articulo> {
  private int cantidadArticulos;
  private int cantidadArticulosPorPagina;
  public ArticuloService() {
    super(Articulo.class);
    this.cantidadArticulos = 0;
    this.cantidadArticulosPorPagina = 2;
  }

  public Articulo find(String articuloId) {
    return this.dbFind(articuloId);
  }
  public List<Articulo> findAll(){
    return this.dbFindAll();
  }

  public List<Articulo> findAllByPage(int page, int pageSize){
    TypedQuery<Articulo> query = this.getEntityManager().createQuery("SELECT a FROM Articulo a", Articulo.class);
    query.setFirstResult((page-1) * pageSize);
    query.setMaxResults(pageSize);
    return query.getResultList();
  }

  public List<Articulo> findAllByPageAndTag(int page, int pageSize, String etiqueta){
    TypedQuery<Articulo> query = this.getEntityManager().createQuery("SELECT a FROM Articulo a JOIN a.etiquetas e WHERE e.nombre = :etiqueta", Articulo.class);
    query.setParameter("etiqueta", etiqueta);
    query.setFirstResult((page-1) * pageSize);
    query.setMaxResults(pageSize);
    return query.getResultList();
  }

  public int manyArticlesByTag(String etiqueta){
    TypedQuery<Articulo> query = this.getEntityManager().createQuery("SELECT a FROM Articulo a JOIN a.etiquetas e WHERE e.nombre = :etiqueta", Articulo.class);
    query.setParameter("etiqueta", etiqueta);
    return query.getResultList().size();
  }

  public List<Articulo> findByEtiqueta(String etiqueta){
    return this.findAll().stream().filter(a -> a.getEtiquetas().stream().map(Etiqueta::getNombre).collect(Collectors.toSet()).contains(etiqueta)).collect(Collectors.toList());
  }


  public Articulo create(String titulo, String cuerpo, Date date, Usuario autor, Set<Etiqueta> etiquetas){
    Articulo articulo = new Articulo();
    articulo.setTitulo(titulo);
    articulo.setCuerpo(cuerpo);
    articulo.setFecha(date);
    articulo.setAutor(autor);
    articulo.setEtiquetas(etiquetas);

    return this.createInDatabase(articulo);
  }
  public Articulo createInDatabase(Articulo articulo){
    this.cantidadArticulos++;
    return this.dbCreate(articulo);
  }

  public Articulo modify(String articuloId, String titulo, String cuerpo, Date date, Set<Etiqueta> etiquetas){
    Articulo articulo = this.find(articuloId);
    articulo.setTitulo(titulo);
    articulo.setCuerpo(cuerpo);
    articulo.setFecha(date);
    articulo.setEtiquetas(etiquetas);
    return this.modifyInDatabase(articulo);
  }
  public Articulo addComentario(Articulo articulo, Comentario comentario){
    articulo.getComentarios().add(comentario);
    return this.modifyInDatabase(articulo);
  }

  public Articulo setComentarios(Articulo articulo, List<Comentario> comentarios){
    articulo.setComentarios(comentarios);
    return this.modifyInDatabase(articulo);
  }

  public Articulo modifyInDatabase(Articulo articulo){
    return this.dbModify(articulo);
  }
  public boolean delete(String articuloId){
    this.cantidadArticulos--;
    return this.dbRemove(articuloId);
  }
  public int getCantidadArticulos(){
    return this.cantidadArticulos;
  }

  public void setCantidadArticulos(int cantidadArticulos){
    this.cantidadArticulos = cantidadArticulos;
  }

  public int getCantidadArticulosPorPagina(){
    return this.cantidadArticulosPorPagina;
  }

  public void setCantidadArticulosPorPagina(int cantidadArticulosPorPagina){
    this.cantidadArticulosPorPagina = cantidadArticulosPorPagina;
  }

}



