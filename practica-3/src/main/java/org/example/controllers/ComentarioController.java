package org.example.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.services.ComentarioService;
import org.example.encapsulations.Comentario;
import util.BaseController;

import java.util.ArrayList;
import static io.javalin.apibuilder.ApiBuilder.*;

public class ComentarioController extends BaseController {
  private final ComentarioService comentarioService;

  public ComentarioController(Javalin app, ComentarioService comentarioService) {
    super(app);
    this.comentarioService = comentarioService;
  }

  public void listar(Context ctx) {
    ArrayList<Comentario> comentarios = comentarioService.findAll();
    ctx.result(comentarios.toString());
  }

  public void crear(Context ctx) {
    System.out.println("Creando comentario");
  }

  public void editar(Context ctx) {
    System.out.println("Editando comentario");
  }

  public void eliminar(Context ctx) {
    System.out.println("Eliminando comentario");
  }

  @Override
  public void applyRoutes() {
    app.routes(() -> path("/comentarios", () -> {
      get("/", this::listar);
      post("/", this::crear);
      put("/{id}", this::editar);
      delete("/{id}", this::eliminar);
    }));

  }
}
