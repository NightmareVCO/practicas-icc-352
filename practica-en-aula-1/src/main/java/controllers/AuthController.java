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
    ctx.render("/public/templates/login.html");
  }

  public void checkLogin(Context ctx) {
    String username = ctx.formParam("username");
    String password = ctx.formParam("password");
    Usuario usuario = usuarioService.findByUsername(username);

    if(!usuarioService.checkPassword(usuario, password))
      ctx.redirect("/auth/login");

    ctx.sessionAttribute("usuario", usuario);
    ctx.redirect("/");
  }

  public void logout(Context ctx) {
    ctx.req().getSession().invalidate();
    ctx.redirect("/");
  }

  @Override
  public void applyRoutes() {
    app.routes(() -> path("/auth", () -> {
      get("/login", this::login);
      post("/login", this::checkLogin);
      get("/logout", this::logout);
    }));
  }

}
