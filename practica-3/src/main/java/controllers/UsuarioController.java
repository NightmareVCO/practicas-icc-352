package controllers;
import encapsulation.Usuario;
import io.javalin.Javalin;
import io.javalin.http.Context;
import services.UsuarioService;
import util.BaseController;
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
    String usuario_autoDelete = ctx.sessionAttribute("usuario_autoDelete");
    ctx.sessionAttribute("usuario_autoDelete", null);

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("usuarios", usuarios);
    modelo.put("usuario_autoDelete", usuario_autoDelete);
    ctx.render("/public/templates/usuarios.html", modelo);
  }

  public void listarUno(Context ctx) {
    String username = ctx.pathParam("username");
    Usuario usuario = usuarioService.find(username);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("usuario", usuario);
    ctx.render("/public/templates/mostrarUsuario.html", modelo);
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
    Usuario usuario = usuarioService.find(ctx.pathParam("username"));
    if (usuario == null) {
      ctx.status(404);
      ctx.result("Usuario no encontrado");
      return;
    }
    usuario.setNombre(ctx.formParam("nombre"));
    usuario.setPassword(ctx.formParam("password"));
    usuario.setAdmin(ctx.formParam("admin") != null);
    usuario.setAutor(ctx.formParam("autor") != null);
    usuario.setActive(true);
    usuarioService.modify(usuario);
    ctx.redirect("/usuarios/" + usuario.getUsername());
  }

  public void eliminar(Context ctx) {
    Usuario usuario = usuarioService.find(ctx.pathParam("username"));
    Usuario usuarioLogueado = ctx.sessionAttribute("usuario");
    if (usuario == null) {
      ctx.status(404);
      ctx.result("Usuario no encontrado");
      return;
    }

    assert usuarioLogueado != null;
    if(usuario.getUsername().equals(usuarioLogueado.getUsername())) {
      ctx.sessionAttribute("usuario_autoDelete", "No puedes eliminarte a ti mismo.");
      ctx.redirect("/usuarios");
      return;
    }

    usuario.setActive(false);
    usuarioService.modify(usuario);
    ctx.redirect("/usuarios");
  }

  @Override
  public void applyRoutes() {
    app.routes(() -> path("/usuarios", () -> {
      before("/", this::proteger);
      get("/", this::listar);
      post("/", this::crear);
      post("/edit/{username}", this::editar);
      before("/{username}", this::proteger);
      get("/{username}", this::listarUno);
      put("/{id}", this::editar);
      delete("/{username}", this::eliminar);
    }));
  }
}
