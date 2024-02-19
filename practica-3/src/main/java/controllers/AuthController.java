package controllers;
import encapsulation.Usuario;
import io.javalin.Javalin;
import io.javalin.http.Context;
import services.UsuarioService;
import util.BaseController;
import static io.javalin.apibuilder.ApiBuilder.*;

public class AuthController extends BaseController {
  private final UsuarioService usuarioService;

  public AuthController(Javalin app, UsuarioService usuarioService) {
    super(app);
    this.usuarioService = usuarioService;
  }

  public void login(Context ctx) {
    if(ctx.cookie("usuario") != null){
      Usuario usuario = usuarioService.findByUsername(ctx.cookie("usuario"));
      ctx.sessionAttribute("usuario", usuario);
      ctx.redirect("/articulos");
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
       ctx.cookie("usuario", usuario.getUsername(), 604800);
     }

    ctx.redirect("/articulos");
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
