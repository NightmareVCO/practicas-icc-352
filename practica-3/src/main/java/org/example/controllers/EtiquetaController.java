package org.example.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.encapsulations.Etiqueta;
import org.example.services.EtiquetaService;
import util.BaseController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;
public class EtiquetaController extends BaseController {
  private final EtiquetaService etiquetaService;

  public EtiquetaController(Javalin app, EtiquetaService etiquetaService) {
    super(app);
    this.etiquetaService = etiquetaService;
  }

  public void listar(Context ctx) {
    ArrayList<Etiqueta> etiquetas = etiquetaService.findAll();
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("etiquetas", etiquetas);
    ctx.render("/public/templates/articulos.html", modelo);
  }

  public void crear(Context ctx) {
    System.out.println("Creando etiqueta");
  }

  public void editar(Context ctx) {
    System.out.println("Editando etiqueta");
  }

  public void eliminar(Context ctx) {
    System.out.println("Eliminando etiqueta");
  }

  @Override
  public void applyRoutes() {
    app.routes(() -> path("/etiquetas", () -> {
      get("/", this::listar);
      post("/", this::crear);
      put("/{id}", this::editar);
      delete("/{id}", this::eliminar);
    }));
  }
}
