package services;
import encapsulation.Articulo;
import encapsulation.Etiqueta;
import jakarta.persistence.TypedQuery;
import util.BaseServiceDatabase;
import java.util.List;
import java.util.stream.Collectors;

public class ArticuloService extends BaseServiceDatabase<Articulo> {
  private int aritculosSize;
  private int pageSize;
  public ArticuloService() {
    super(Articulo.class);
    this.aritculosSize = 0;
    this.pageSize = 2; //Cantidad de articulos por pagina
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
  public Articulo create(Articulo articulo){
    this.aritculosSize++;
    return this.dbCreate(articulo);
  }
  public Articulo modify(Articulo articulo){
    return this.dbModify(articulo);
  }
  public boolean delete(String articuloId){
    this.aritculosSize--;
    return this.dbRemove(articuloId);
  }
  public int getAritculosSize(){
    return this.aritculosSize;
  }

  public void setAritculosSize(int aritculosSize){
    this.aritculosSize = aritculosSize;
  }

  public int getPageSize(){
    return this.pageSize;
  }

  public void setPageSize(int pageSize){
    this.pageSize = pageSize;
  }

}



