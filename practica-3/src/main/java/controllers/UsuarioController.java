package controllers;
import encapsulation.Foto;
import encapsulation.Usuario;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import services.FotoService;
import services.UsuarioService;
import util.BaseController;

import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class  UsuarioController extends BaseController {
  private final UsuarioService usuarioService;
  private final FotoService fotoService;

  public UsuarioController(Javalin app, UsuarioService usuarioService, FotoService fotoService) {
    super(app);
    this.usuarioService = usuarioService;
    this.fotoService = fotoService;
  }

  public void proteger(Context ctx) {
    Usuario usuario = ctx.sessionAttribute("usuario");
    if (usuario == null || !usuario.isAdmin())
      ctx.redirect("/auth/login");
  }

  public void listar(Context ctx) {
    String usuario_autoDelete = ctx.sessionAttribute("usuario_autoDelete");
    ctx.sessionAttribute("usuario_autoDelete", null);
    List<Usuario> usuarios = usuarioService.findAll();

    Map<String, Object> modelo =  setModelo(
      "usuarios", usuarios,
      "usuario_autoDelete", usuario_autoDelete);

    ctx.render("/public/templates/usuarios.html", modelo);
  }

  public void listarUno(Context ctx) {
    String username = ctx.pathParam("username");
    Usuario usuario = usuarioService.find(username);
    Foto foto = usuario.getFoto();

    Map<String, Object> modelo = setModelo(
      "usuario", usuario,
      "foto", foto);

    ctx.render("/public/templates/mostrarUsuario.html", modelo);
  }

  public void crear(Context ctx) {
    String username = ctx.formParam("username");
    if (usuarioService.find(username) != null) {
      ctx.status(400);
      ctx.result("El usuario ya existe");
      return;
    }

    String nombre = ctx.formParam("nombre");
    String password = ctx.formParam("password");
    boolean admin = ctx.formParam("admin") != null;
    boolean autor = ctx.formParam("autor") != null;
    UploadedFile file = ctx.uploadedFile("foto");
    Foto foto = fotoService.create(file);

    usuarioService.create(username, nombre, password, admin, autor, foto);
    ctx.redirect("/usuarios/" + username);
  }

  public void editar(Context ctx) {
    String username = ctx.pathParam("username");
    String nombre = ctx.formParam("nombre");
    String password = ctx.formParam("password");
    boolean admin = ctx.formParam("admin") != null;
    boolean autor = ctx.formParam("autor") != null;

    usuarioService.modify(username, nombre, password, admin, autor);
    ctx.redirect("/usuarios/" + username);
  }

  public void eliminar(Context ctx) {
    String username = ctx.pathParam("username");
    Usuario usuarioLogueado = ctx.sessionAttribute("usuario");

    assert usuarioLogueado != null;
    if(usuarioLogueado.getUsername().equals(username)) {
      ctx.sessionAttribute("usuario_autoDelete", "No puedes eliminarte a ti mismo.");
      ctx.redirect("/usuarios");
      return;
    }

    usuarioService.desactivar(username);
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
