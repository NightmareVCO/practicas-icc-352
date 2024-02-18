package controllers;
import io.javalin.Javalin;
import io.javalin.http.Context;
import services.EtiquetaService;
import util.BaseController;

import static io.javalin.apibuilder.ApiBuilder.*;
public class EtiquetaController extends BaseController {
  private final EtiquetaService etiquetaService;

  public EtiquetaController(Javalin app, EtiquetaService etiquetaService) {
    super(app);
    this.etiquetaService = etiquetaService;
  }

  public void listarByTag(Context ctx) {
    String tag = ctx.queryParam("tag");
    ctx.redirect("/articulos?tag=" + tag);
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
