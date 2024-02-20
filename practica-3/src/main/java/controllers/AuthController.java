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
    if(ctx.cookie("usuario") != null){
      Usuario usuario = usuarioService.findByUsername(authService.decryptText(ctx.cookie("usuario")));
      ctx.sessionAttribute("usuario", usuario);
      ctx.redirect("/articulos?page=1");
    }

    ctx.render("/public/templates/login.html");
  }

  public void checkLogin(Context ctx) {
    String username = ctx.formParam("username");
    String password = ctx.formParam("password");
    boolean remember = ctx.formParam("remember") != null;
    Usuario usuario = usuarioService.findByUsername(username);

    if(!usuario.isActive()){
      ctx.sessionAttribute("usuario_inactivo", "Usuario inactivo, favor contactar al administrador.");
      ctx.redirect("/auth/login");
      return;
    }

    if(!usuarioService.checkPassword(username, password) || usuario == null) {
      ctx.redirect("/auth/login");
      return;
    }

    ctx.sessionAttribute("usuario", usuario);
     if(remember){
       ctx.cookie("usuario", authService.encryptText(usuario.getUsername()), 604800);
     }

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
      ctx.redirect("/articulos");
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
