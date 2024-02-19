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
 List<Articulo> articulos = articuloService.findAll();

  if(ctx.queryParam("tag") != null) {
    Etiqueta etiqueta = etiquetaService.find(ctx.queryParam("tag"));
    if (etiqueta != null)
      articulos = articuloService.findByEtiqueta(etiqueta.getNombre());
  }
 List<Etiqueta> etiquetas = etiquetaService.findAll();

 Map<String, Object> modelo = new HashMap<>();
 modelo.put("articulos", articulos);
 modelo.put("etiquetas", etiquetas);
 ctx.render("/public/templates/articulos.html", modelo);

  }

  public void listarUno(Context ctx) {
    Long id = Long.parseLong(ctx.pathParam("id"));

    Articulo articulo = articuloService.find(String.valueOf(id));

    Map<String, Object> modelo = new HashMap<>();

    modelo.put("articulo", articulo);
    modelo.put("etiquetas", etiquetaService.getEtiquetasString(articulo.getEtiquetas()));
    modelo.put("comentarios", articulo.getComentarios());
    ctx.render("/public/templates/mostrarArticulo.html", modelo);
  }

  public void crear(Context ctx) {
   Articulo articulo = new Articulo();
    articulo.setTitulo(ctx.formParam("titulo"));
    articulo.setCuerpo(ctx.formParam("contenido"));
    articulo.setFecha(new Date());
    articulo.setAutor(Objects.requireNonNull(ctx.sessionAttribute("usuario")));
    articulo.setEtiquetas(etiquetaService.insertFromString(Objects.requireNonNull(ctx.formParam("etiquetas")).split(",")));

   articuloService.create(articulo);
   ctx.redirect("/articulos");
  }

  public void editar(Context ctx) {
    Articulo articulo = articuloService.find(String.valueOf(Long.parseLong(ctx.pathParam("id"))));
    articulo.setTitulo(ctx.formParam("titulo"));
    articulo.setCuerpo(ctx.formParam("contenido"));
    articulo.setEtiquetas(etiquetaService.insertFromString(Objects.requireNonNull(ctx.formParam("etiquetas")).split(",")));
    articuloService.modify(articulo);
    ctx.redirect("/articulos");
  }

  public void eliminar(Context ctx) {
    Articulo articulo = articuloService.find(String.valueOf(Long.parseLong(ctx.pathParam("id"))));
    articuloService.delete(String.valueOf(articulo.getId()));
    ctx.redirect("/articulos");
  }
  
  public void ingresarComentario(Context ctx) {
    long articuloId = Long.parseLong(ctx.pathParam("id"));
    Articulo articulo = articuloService.find(String.valueOf(articuloId));

    Comentario comentario = new Comentario();
    comentario.setComentario(ctx.formParam("comentario"));
    comentario.setAutor(Objects.requireNonNull(ctx.sessionAttribute("usuario")));
    comentario.setArticulo(articulo);

    comentarioService.create(comentario);
    articulo.getComentarios().add(comentario); //malo, servicio debería hacer esto
    articuloService.modify(articulo);
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
