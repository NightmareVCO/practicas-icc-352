package controllers;
import encapsulation.Usuario;
import io.javalin.Javalin;
import io.javalin.http.Context;
import services.AuthService;
import services.UsuarioService;
import util.BaseController;
import static io.javalin.apibuilder.ApiBuilder.*;

public class AuthController extends BaseController {
  private final UsuarioService usuarioService;
  private final AuthService authService;

  public AuthController(Javalin app, UsuarioService usuarioService, AuthService authService) {
    super(app);
    this.usuarioService = usuarioService;
    this.authService = authService;
  }

  public void login(Context ctx) {
    String usuarioEncryptado = ctx.cookie("usuario");
    if (usuarioEncryptado == null) {
      ctx.render("/public/templates/login.html");
      return;
    }

    String username = authService.decryptText(usuarioEncryptado);
    Usuario usuarioEnCookie = usuarioService.findByUsername(username);
    ctx.sessionAttribute("usuario", usuarioEnCookie);

    ctx.redirect("/articulos?page=1");
  }

  public void checkLogin(Context ctx) {
    String username = ctx.formParam("username"); // Entrada no validada
    String password = ctx.formParam("password");
    Usuario usuario = usuarioService.findByUsername(username); // Potencialmente vulnerable

    if (!usuarioService.checkPassword(username, password) || usuario == null) {
      ctx.redirect("/auth/login");
      return;
    }

    ctx.sessionAttribute("usuario", usuario);
    ctx.redirect("/articulos?page=1");
  }

  public void logout(Context ctx) {
    ctx.req().getSession().invalidate();
    ctx.removeCookie("usuario");
    ctx.redirect("/");
  }

  public void register(Context ctx) {
    System.out.println("Registrando usuario");
  }

  public void proteger(Context ctx) {
    Usuario usuario = ctx.sessionAttribute("usuario");
    if (usuario != null)
      ctx.redirect("/articulos?page=1");
  }

  @Override
  public void applyRoutes() {
    app.routes(() -> path("/auth", () -> {
      before("/login", this::proteger);
      get("/login", this::login);
      post("/login", this::checkLogin);
      get("/logout", this::logout);
      post("/register", this::register);
    }));
  }

}