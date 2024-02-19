package services;
import encapsulation.Etiqueta;
import util.BaseServiceDatabase;
import java.util.*;

public class EtiquetaService extends BaseServiceDatabase<Etiqueta> {
  public EtiquetaService() {
    super(Etiqueta.class);
  }

  public Etiqueta find(String etiquetaId) {
    return this.dbFind(etiquetaId);
  }

  public Etiqueta findByName(String etiquetaName) {
    return this.findAll().stream().filter(e -> e.getNombre().equals(etiquetaName)).findFirst().orElse(null);

  }

  public List<Etiqueta> findAll() {
    return this.dbFindAll();
  }

  public Etiqueta create(Etiqueta etiqueta) {
    return this.dbCreate(etiqueta);
  }

  public Etiqueta modify(Etiqueta etiqueta) {
    return this.dbModify(etiqueta);
  }

  public boolean delete(String etiquetaId) {
    return this.dbRemove(etiquetaId);
  }


  public Set<Etiqueta> insertFromString(String[] etiquetas) {
    String[] etiquetasLimpias = Arrays.stream(etiquetas).map(String::trim).toArray(String[]::new);
    Set<String> etiquetasSet = new HashSet<>(Arrays.asList(etiquetasLimpias));
    Set<Etiqueta> newEtiquetas = new HashSet<>();

    for (String etiquetaName : etiquetasSet) {
      Etiqueta e = this.findByName(etiquetaName);
      Etiqueta newEtiqueta = new Etiqueta();
      newEtiqueta.setNombre(etiquetaName);
      if (e == null) this.create(newEtiqueta);
      newEtiquetas.add(this.findByName(etiquetaName));
    }
    return newEtiquetas;
  }

  public String getEtiquetasString(Set<Etiqueta> etiquetas) {
    StringBuilder etiquetasString = new StringBuilder();

    for (Etiqueta e : etiquetas)
      etiquetasString.append(e.getNombre()).append(",");

    return etiquetasString.substring(0, etiquetasString.length());
  }

}
