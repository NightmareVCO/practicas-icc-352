package services;
import encapsulation.Articulo;
import encapsulation.Etiqueta;
import util.BaseServiceDatabase;
import java.util.List;
import java.util.Set;

public class ArticuloService extends BaseServiceDatabase<Articulo> {
  public ArticuloService() {
    super(Articulo.class);
  }

  public Articulo find(String articuloId) {
    return this.dbFind(articuloId);
  }
  public List<Articulo> findAll(){
    return this.dbFindAll();
  }
  public Articulo create(Articulo articulo){
    return this.dbCreate(articulo);

  }
  public Articulo modify(Articulo articulo){
    return this.dbModify(articulo);
  }
  public boolean delete(String articuloId){
    return this.dbRemove(articuloId);
  }
}



