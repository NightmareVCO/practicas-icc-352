package controllers;

import encapsulations.Estudiante;
import io.javalin.Javalin;
import io.javalin.http.Context;
import services.EstudianteService;
import util.BaseController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

import static io.javalin.apibuilder.ApiBuilder.path;

public class CrudController extends BaseController {
  private final EstudianteService estudianteService;

  public CrudController(Javalin app, EstudianteService estudianteService) {
    super(app);
    this.estudianteService = estudianteService;
  }

  public void listar(Context ctx) {
    List<Estudiante> lista = estudianteService.listarEstudiante();

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("titulo", "Listado de Estudiante");
    modelo.put("lista", lista);

    ctx.render("/templates/crud-tradicional/listar.html", modelo);
  }

  public void crear(Context ctx) {

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("titulo", "Formulario Creación Estudiante");
    modelo.put("accion", "/crud-simple/crear");

    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
  }

  public void crearEstudiante(Context ctx) {
    int matricula = ctx.formParamAsClass("matricula", Integer.class).get();
    String nombre = ctx.formParam("nombre");
    String carrera = ctx.formParam("carrera");

    Estudiante tmp = new Estudiante();
    tmp.setMatricula(matricula);
    tmp.setNombre(nombre);
    tmp.setCarrera(carrera);
    estudianteService.crearEstudiante(tmp);

    ctx.redirect("/crud-simple/");
  }

  public void visualizar(Context ctx) {
    Estudiante estudiante = estudianteService.getEstudiantePorMatricula(ctx.pathParamAsClass("matricula", Integer.class).get());

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("titulo", "Formulario Visaulizar Estudiante "+estudiante.getMatricula());
    modelo.put("estudiante", estudiante);
    modelo.put("visualizar", true);
    modelo.put("accion", "/crud-simple/");

    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
  }

  public void editar(Context ctx) {
    Estudiante estudiante = estudianteService.getEstudiantePorMatricula(ctx.pathParamAsClass("matricula", Integer.class).get());

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("titulo", "Formulario Editar Estudiante "+estudiante.getMatricula());
    modelo.put("estudiante", estudiante);
    modelo.put("accion", "/crud-simple/editar");

    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
  }

  public void editarEstudiante(Context ctx) {
    int matricula = ctx.formParamAsClass("matricula", Integer.class).get();
    String nombre = ctx.formParam("nombre");
    String carrera = ctx.formParam("carrera");

    Estudiante tmp = new Estudiante();
    tmp.setMatricula(matricula);
    tmp.setNombre(nombre);
    tmp.setCarrera(carrera);
    estudianteService.actualizarEstudiante(tmp);

    ctx.redirect("/crud-simple/");
  }

  public void eliminar(Context ctx) {
    estudianteService.eliminarEstudiante(ctx.pathParamAsClass("matricula", Integer.class).get());
    ctx.redirect("/crud-simple/");
  }

  @Override
  public void applyRoutes() {
    app.routes(() -> path("/crud-simple/", () -> {
      get("/", ctx -> ctx.redirect("/crud-simple/listar"));
      get("/listar", this::listar);
      get("/crear", this::crear);
      get("/editar/{matricula}", this::editar);
      get("/visualizar/{matricula}", this::visualizar);
      get("/eliminar/{matricula}", this::eliminar);
      post("/crear", this::crearEstudiante);
      post("/editar", this::editarEstudiante);
    }));
  }
}
