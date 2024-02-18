package controllers;

import encapsulation.Articulo;
import encapsulation.Etiqueta;
import encapsulation.Usuario;
import io.javalin.Javalin;
import io.javalin.http.Context;
import services.UsuarioService;
import util.BaseController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UsuarioController extends BaseController {
  private final UsuarioService usuarioService;

  public UsuarioController(Javalin app, UsuarioService usuarioService) {
    super(app);
    this.usuarioService = usuarioService;
  }

  public void listar(Context ctx) {
    List<Usuario> usuarios = usuarioService.findAll();
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("usuarios", usuarios);
    ctx.render("/public/templates/usuarios.html", modelo);
  }

  public void crear(Context ctx) {
    Usuario usuario = new Usuario();
    usuario.setNombre(ctx.formParam("nombre"));
    usuario.setUsername(ctx.formParam("username"));
    usuario.setPassword(ctx.formParam("password"));
    usuario.setAdmin(ctx.formParam("admin") != null);
    usuario.setAutor(ctx.formParam("autor") != null);

    if (usuarioService.find(usuario.getUsername()) != null) {
      ctx.status(400);
      ctx.result("El usuario ya existe");
      return;
    }

    usuarioService.create(usuario);
    ctx.redirect("/usuarios");
  }

  public void proteger(Context ctx) {
    Usuario usuario = ctx.sessionAttribute("usuario");
    if (usuario == null || !usuario.isAdmin())
      ctx.redirect("/auth/login");
  }

  public void editar(Context ctx) {
    System.out.println("Editando usuario");
  }

  public void eliminar(Context ctx) {
    System.out.println("Eliminando usuario");
  }

  @Override
  public void applyRoutes() {
    app.routes(() -> path("/usuarios", () -> {
      before("/", this::proteger);
      get("/", this::listar);
      post("/", this::crear);
      put("/{id}", this::editar);
      delete("/{id}", this::eliminar);
    }));
  }
}
