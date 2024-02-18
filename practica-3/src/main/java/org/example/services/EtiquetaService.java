package org.example.services;
import org.example.encapsulations.Etiqueta;
import org.example.utils.BaseServiceDatabase;

import java.util.*;

public class EtiquetaService extends BaseServiceDatabase<Etiqueta> {
  public EtiquetaService() {
    super(Etiqueta.class);

    Etiqueta etiqueta = new Etiqueta();
    etiqueta.setNombre("Movies");

    Etiqueta etiqueta2 = new Etiqueta();
    etiqueta2.setNombre("Music");

    Etiqueta etiqueta3 = new Etiqueta();
    etiqueta3.setNombre("Java");
  }

  //  find, findAll, create, modify, delete
  public Etiqueta find(String nombre) {
    return this.dbFind(nombre);
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

  public boolean delete(String nombre) {
    return this.dbRemove(nombre);
  }





//  public ArrayList<Etiqueta> insertFromString(String[] etiquetas) {
//    String[] etiquetasLimpias = Arrays.stream(etiquetas).map(String::trim).toArray(String[]::new);
//    Set<String> etiquetasSet = new HashSet<>(Arrays.asList(etiquetasLimpias));
//    ArrayList<Etiqueta> newEtiquetas = new ArrayList<>();
//
//    for (String etiquetaName : etiquetasSet) {
//      Etiqueta e = this.findByName(etiquetaName);
//      if (e == null) this.etiquetas.add(new Etiqueta(this.getNextId(), etiquetaName));
//      newEtiquetas.add(this.findByName(etiquetaName));
//    }
//    return newEtiquetas;
//  }
//
//  public String getEtiquetasString(ArrayList<Etiqueta> etiquetas) {
//    StringBuilder etiquetasString = new StringBuilder();
//
//    for (Etiqueta e : etiquetas) {
//      etiquetasString.append(e.getNombre()).append(",");
//    }
//
//    return etiquetasString.substring(0, etiquetasString.length() - 1);
//  }

}