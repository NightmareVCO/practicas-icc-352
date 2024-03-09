package controllers;
import encapsulation.Articulo;
import encapsulation.Comentario;
import encapsulation.Etiqueta;
import encapsulation.Usuario;
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
  private final String ARTICULOS_INITIAL = "/articulos?page=1";

  public ArticuloController(Javalin app, ArticuloService articuloService, EtiquetaService etiquetaService, ComentarioService comentarioService) {
    super(app);
    this.articuloService = articuloService;
    this.etiquetaService = etiquetaService;
    this.comentarioService = comentarioService;
  }

  public void validarPaginas(Context ctx) {
    String paginaActual = ctx.queryParam("page");
    if (paginaActual == null) {
      ctx.result("Pagina no encontrada");
      return;
    }
    String tag = ctx.queryParam("tag");
    int paginaActualInt;

    paginaActualInt = Integer.parseInt(paginaActual);
    if (paginaActualInt >= 1)
      return;
    if (tag == null)
      ctx.redirect(ARTICULOS_INITIAL);

    ctx.redirect(ARTICULOS_INITIAL + "&tag=" + tag);
  }

  public void validarTag(Context ctx) {
    String paginaActual = ctx.queryParam("page");
    String tag = ctx.queryParam("tag");

    if (paginaActual == null)
      ctx.result("Pagina no encontrada");
    if (tag == null) {
      ctx.result("Tag no existente");
      return;
    }
    if (!tag.equals("null")) //Si es "null" es el tag por defecto
      return;

    ctx.redirect("/articulos?page=" + paginaActual);
  }

  private AbstractMap.SimpleEntry<List<Articulo>, Integer> getArticulosPaginados(Context ctx) {
    List<Articulo> articulos = new ArrayList<>();
    int articulosTotal = articuloService.getCantidadArticulos();
    int articulosPorPagina = articuloService.getCantidadArticulosPorPagina();
    int totalDePaginas = (int)Math.ceil((double)articulosTotal / articulosPorPagina);
    int paginaActual;
    int arituclosTotalPorTag;
    String pa = ctx.queryParam("page");
    if(pa == null){
      ctx.result("Pagina no encontrada");
      return new AbstractMap.SimpleEntry<>(articulos, totalDePaginas);
    }
    paginaActual = Integer.parseInt(pa);

    if(ctx.queryParam("tag") == null){
      if(paginaActual < 1)
        ctx.redirect("/articulos?page=" + totalDePaginas);
      else if(paginaActual > totalDePaginas)
        ctx.redirect(ARTICULOS_INITIAL);

      articulos = articuloService.findAllByPage(paginaActual, articulosPorPagina);
    }
    else {
      Etiqueta etiqueta = etiquetaService.find(ctx.queryParam("tag"));
      if (etiqueta == null){
        ctx.result("Tag no existente");
        return new AbstractMap.SimpleEntry<>(articulos, totalDePaginas);
      }
      arituclosTotalPorTag = articuloService.manyArticlesByTag(etiqueta.getNombre());
      totalDePaginas = (int)Math.ceil((double)arituclosTotalPorTag / articulosPorPagina);

      if(paginaActual < 1)
        ctx.redirect("/articulos?page=" + totalDePaginas + "&tag=" + etiqueta.getId());
      else if(paginaActual > totalDePaginas)
        ctx.redirect("/articulos?page=1&tag=" + etiqueta.getId());

      articulos = articuloService.findAllByPageAndTag(paginaActual, articulosPorPagina, etiqueta.getNombre());
    }

    return new AbstractMap.SimpleEntry<>(articulos, totalDePaginas);
  }
  public void listar(Context ctx) {
    AbstractMap.SimpleEntry<List<Articulo>, Integer> result = getArticulosPaginados(ctx);
    List<Articulo> articulos = result.getKey();
    int totalDePaginas = result.getValue();
    List<Etiqueta> etiquetas = etiquetaService.findAll();
    String pa = ctx.queryParam("page"); assert pa != null;

    int paginaActual = Integer.parseInt(pa);
    String tag = ctx.queryParam("tag");

    Map<String, Object> modelo = setModelo(
      "articulos", articulos,
      "etiquetas", etiquetas,
      "page", paginaActual,
      "tag", tag
    );
    ctx.render("/public/templates/articulos.html", modelo);
  }

  public void listarUno(Context ctx) {
    Long id = Long.parseLong(ctx.pathParam("id"));
    Articulo articulo = articuloService.find(String.valueOf(id));
    String etiquetas = etiquetaService.getEtiquetasString(articulo.getEtiquetas());
    List<Comentario> comentarios = articulo.getComentarios();

    Map<String, Object> modelo = setModelo(
      "articulo", articulo,
      "etiquetas", etiquetas,
      "comentarios", comentarios
    );

    ctx.render("/public/templates/mostrarArticulo.html", modelo);
  }

  public void crear(Context ctx) {
    String titulo = ctx.formParam("titulo");
    String contenido = ctx.formParam("contenido");
    Date fecha = new Date();
    Usuario autor = ctx.sessionAttribute("usuario");
    String e = ctx.formParam("etiquetas");
    assert e != null;
    Set<Etiqueta> etiquetas = etiquetaService.insertFromString(e);

    articuloService.create(titulo, contenido, fecha, autor, etiquetas);
    ctx.redirect(ARTICULOS_INITIAL);
  }

  public void editar(Context ctx) {
    String articuloId = (ctx.pathParam("id"));
    String titulo = ctx.formParam("titulo");
    String contenido = ctx.formParam("contenido");
    String e = ctx.formParam("etiquetas");
    assert e != null;
    Set<Etiqueta> etiquetas = etiquetaService.insertFromString(e);

    articuloService.modify(articuloId, titulo, contenido, new Date(), etiquetas);
    ctx.redirect("/articulos/" + articuloId);
  }

  public void eliminar(Context ctx) {
    String articuloId = ctx.pathParam("id");
    articuloService.delete(articuloId);
    ctx.redirect(ARTICULOS_INITIAL);
  }

  public void ingresarComentario(Context ctx) {
    String articuloId = ctx.pathParam("id");
    Articulo articulo = articuloService.find((articuloId));
    String comentario = ctx.formParam("comentario");
    Usuario autor = ctx.sessionAttribute("usuario");

    Comentario newComentario = comentarioService.create(comentario, autor, articulo);
    articuloService.addComentario(articulo, newComentario);

    ctx.redirect("/articulos/" + articuloId);
  }

  @Override
  public void applyRoutes() {
    app.routes(() -> path("/articulos", () -> {
      before("/", this::validarPaginas);
      before("/", this::validarTag);
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
