package services;

import encapsulation.Etiqueta;
import util.BaseService;

import java.util.*;

public class EtiquetaService {

  private final ArrayList<Etiqueta> etiquetas;

  public EtiquetaService() {
    this.etiquetas = new ArrayList<>();
    etiquetas.add(new Etiqueta(1L, "Cine"));
    etiquetas.add(new Etiqueta(2L, "Medicina"));
    etiquetas.add(new Etiqueta(3L, "Deportes"));
  }

  public ArrayList<Etiqueta> findAll() {
    return etiquetas;
  }

  public Etiqueta findById(Long id) {
    return null;
  }

  public Etiqueta findByName(String name) {
    Etiqueta etiqueta = null;

    for (Etiqueta e : etiquetas)
      if (e.getNombre().equals(name))
        etiqueta = e;

    return etiqueta;
  }

  public Etiqueta insert(Etiqueta entity) {
    return null;
  }

  public ArrayList<Etiqueta> insertFromString(String[] etiquetas) {
    String[] etiquetasLimpias = Arrays.stream(etiquetas).map(String::trim).toArray(String[]::new);
    Set<String> etiquetasSet = new HashSet<>(Arrays.asList(etiquetasLimpias));
    ArrayList<Etiqueta> newEtiquetas = new ArrayList<>();

    for (String etiquetaName : etiquetasSet) {
      Etiqueta e = this.findByName(etiquetaName);
      if (e == null) this.etiquetas.add(new Etiqueta(this.getNextId(), etiquetaName));
      newEtiquetas.add(this.findByName(etiquetaName));
    }
    return newEtiquetas;
  }

  public String getEtiquetasString(ArrayList<Etiqueta> etiquetas) {
    StringBuilder etiquetasString = new StringBuilder();

    for (Etiqueta e : etiquetas) {
      etiquetasString.append(e.getNombre()).append(",");
    }

    return etiquetasString.substring(0, etiquetasString.length() - 1);
  }

  public long getNextId() {
    return this.etiquetas.size() + 1;
  }

  public Etiqueta getByIndex(int index) {
    return this.etiquetas.get(index);
  }

  public Etiqueta update(Etiqueta entity) {
    return null;
  }

  public Etiqueta delete(Etiqueta entity) {
    return null;
  }

  public Etiqueta deleteById(Long id) {
    return null;
  }

  public Etiqueta deleteAll() {
    return null;
  }
}
