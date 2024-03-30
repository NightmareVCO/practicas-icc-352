package controllers;

import entities.User;
import io.javalin.Javalin;
import io.javalin.http.Context;
import services.AuthService;
import services.UserService;
import util.BaseController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class AuthController extends BaseController {
  private final UserService userService;
  private final AuthService authService;

  public AuthController(Javalin app, UserService userService, AuthService authService) {
    super(app);
    this.userService = userService;
    this.authService = authService;
  }

  public void login(Context ctx) {
    String usuarioEncryptado = ctx.cookie("user");
    if (usuarioEncryptado == null) {
      ctx.render("/public/templates/login.html");
      return;
    }

    String username = authService.decryptText(usuarioEncryptado);
    User userInCookie = userService.findByUsername(username);
    ctx.sessionAttribute("user", userInCookie);

    ctx.redirect("/");
  }

  public void checkLogin(Context ctx) {
    String username = ctx.formParam("username");
    String password = ctx.formParam("password");
    boolean remember = ctx.formParam("remember") != null;
    User user = userService.findByUsername(username);

    if (user == null) {
      ctx.redirect("/auth/login");
      return;
    }

    if(!user.isActive()){
      ctx.sessionAttribute("inactive_user", "Usuario inactivo, favor contactar al administrador.");
      ctx.redirect("/auth/login");
      return;
    }

    if(!userService.checkPassword(username, password) || user  == null) {
      ctx.redirect("/auth/login");
      return;
    }

    ctx.sessionAttribute("user", user);
    ctx.sessionAttribute("username", user.getUsername());
     if(remember)
       ctx.cookie("user", authService.encryptText(user.getUsername()), 604800);

    ctx.redirect("/");
  }

  public void logout(Context ctx) {
    ctx.req().getSession().invalidate();
    ctx.removeCookie("user");
    ctx.redirect("/");
  }

  public void register(Context ctx) {
    System.out.println("Registrando usuario");
  }

  public void protect(Context ctx) {
    User usuario = ctx.sessionAttribute("user");
    if (usuario != null)
      ctx.redirect("/");
  }

  @Override
  public void applyRoutes() {
    app.routes(() -> path("/auth", () -> {
      before("/login", this::protect);
      get("/login", this::login);
      post("/login", this::checkLogin);
      get("/logout", this::logout);
      post("/register", this::register);
    }));
  }

}
