package controllers;
import encapsulation.Etiqueta;
import io.javalin.Javalin;
import io.javalin.http.Context;
import services.EtiquetaService;
import util.BaseController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;
public class EtiquetaController extends BaseController {
  private final EtiquetaService etiquetaService;

  public EtiquetaController(Javalin app, EtiquetaService etiquetaService) {
    super(app);
    this.etiquetaService = etiquetaService;
  }

  public void listar(Context ctx) {
    List<Etiqueta> etiquetas = etiquetaService.findAll();
    Map<String, Object> modelo = setModelo("etiquetas", etiquetas);
    ctx.render("/public/templates/articulos.html", modelo);
  }

  public void listarByTag(Context ctx) {
   ctx.redirect("/articulos?page=1&tag=" + ctx.queryParam("tag"));
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
      get("/", this::listarByTag);
      post("/", this::crear);
      put("/{id}", this::editar);
      delete("/{id}", this::eliminar);
    }));
  }
}
