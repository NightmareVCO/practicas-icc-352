package controllers;
import encapsulation.Articulo;
import encapsulation.Comentario;
import encapsulation.Etiqueta;
import io.javalin.Javalin;
import io.javalin.http.Context;
import services.ArticuloService;
import services.ComentarioService;
import services.EtiquetaService;
import util.BaseController;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ArticuloController extends BaseController {
  private final ArticuloService articuloService;
  private final EtiquetaService etiquetaService;
  private final ComentarioService comentarioService;
  public ArticuloController(Javalin app, ArticuloService articuloService, EtiquetaService etiquetaService, ComentarioService comentarioService) {
    super(app);
    this.articuloService = articuloService;
    this.etiquetaService = etiquetaService;
    this.comentarioService = comentarioService;
  }

  public void listar(Context ctx) {
    ArrayList<Articulo> articulos = articuloService.findAll();

    if (ctx.queryParam("tag") != null) {
      long etiqueta = Long.parseLong(Objects.requireNonNull(ctx.queryParam("tag")));
      if (etiqueta != 0)
        articulos = articuloService.findByEtiqueta(etiquetaService.findNameById(etiqueta));
    }

   ArrayList<Etiqueta> etiquetas = etiquetaService.findAll();

   Map<String, Object> modelo = new HashMap<>();
   modelo.put("articulos", articulos);
   modelo.put("etiquetas", etiquetas);
   ctx.render("/public/templates/articulos.html", modelo);
  }

  public void listarUno(Context ctx) {
    Long id = Long.parseLong(ctx.pathParam("id"));


    Articulo articulo = articuloService.findById(id);
    Map<String, Object> modelo = new HashMap<>();

    modelo.put("articulo", articulo);
    modelo.put("etiquetas", etiquetaService.getEtiquetasString(articulo.getEtiquetas()));
    ctx.render("/public/templates/mostrarArticulo.html", modelo);
  }

  public void crear(Context ctx) {
   Articulo articulo = new Articulo(
     articuloService.getNextId(),
     ctx.formParam("titulo"),
     ctx.formParam("contenido"),
     Objects.requireNonNull(ctx.sessionAttribute("usuario")),
      new Date(),
     etiquetaService.insertFromString(Objects.requireNonNull(ctx.formParam("etiquetas")).split(","))
   );
   articuloService.insert(articulo);
   ctx.redirect("/articulos");
  }

  public void editar(Context ctx) {
    articuloService.update(Long.parseLong(ctx.pathParam("id")), ctx.formParam("contenido"), ctx.formParam("titulo"), etiquetaService.insertFromString(Objects.requireNonNull(ctx.formParam("etiquetas")).split(",")));
    ctx.redirect("/articulos");
  }

  public void eliminar(Context ctx) {
    Articulo articulo = articuloService.findById(Long.parseLong(ctx.pathParam("id")));
    articuloService.delete(articulo);
    ctx.redirect("/articulos");
  }
  
  public void ingresarComentario(Context ctx) {
    long articuloId = Long.parseLong(ctx.pathParam("id"));
    Articulo articulo = articuloService.findById(articuloId);

    Comentario comentario = new Comentario(
      comentarioService.getNextId(),
      ctx.formParam("comentario"),
      Objects.requireNonNull(ctx.sessionAttribute("usuario")),
      articulo
    );
    comentarioService.insert(comentario);
    articulo.getComentarios().add(comentario); //malo, servicio debería hacer esto
    ctx.redirect("/articulos/" + articuloId);

  }

  @Override
  public void applyRoutes() {
    app.routes(() -> path("/articulos", () -> {
      get("/", this::listar);
      get("/{id}", this::listarUno);
      post("/edit/{id}", this::editar);
      post("/{id}/comentario", this::ingresarComentario);
      post("/", this::crear);
      put("/{id}", this::editar);
      delete("/{id}", this::eliminar);
    }));
  }
}
