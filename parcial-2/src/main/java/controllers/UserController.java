package controllers;

import entities.User;
import io.javalin.Javalin;
import io.javalin.http.Context;
import services.UserService;
import util.BaseController;

import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class UserController extends BaseController {

  UserService userService;

  public UserController(Javalin app, UserService userService) {
    super(app);
    this.userService = userService;
  }

  public void proteger(Context ctx) {
    User user = ctx.sessionAttribute("user");
    if (user == null || !user.isAdmin())
      ctx.redirect("/auth/login");
  }

  public void listar(Context ctx) {
    String usuario_autoDelete = ctx.sessionAttribute("usuario_autoDelete");
    ctx.sessionAttribute("usuario_autoDelete", null);
    List<User> users = userService.findAll();

    Map<String, Object> modelo =  setModelo(
      "users", users,
      "usuario_autoDelete", usuario_autoDelete);

    ctx.render("/public/templates/users.html", modelo);
  }

  public void listarUno(Context ctx) {
    String username = ctx.pathParam("username");
    User user = userService.find(username);

    Map<String, Object> modelo = setModelo(
      "user", user);

    ctx.render("/public/templates/showUser.html", modelo);
  }

  public void crear(Context ctx) {
    String username = ctx.formParam("username");
    if (userService.find(username) != null) {
      ctx.status(400);
      ctx.result("El usuario ya existe");
      return;
    }

    String nombre = ctx.formParam("name");
    String password = ctx.formParam("password");
    boolean admin = ctx.formParam("admin") != null;
    boolean pollster = ctx.formParam("pollster") != null;

    userService.create(username, nombre, password, admin, pollster);
    ctx.redirect("/users/" + username);
  }

  public void editar(Context ctx) {
    String username = ctx.pathParam("username");
    String nombre = ctx.formParam("name");
    String password = ctx.formParam("password");
    boolean admin = ctx.formParam("admin") != null;
    boolean pollster = ctx.formParam("pollster") != null;

    userService.modify(username, nombre, password, admin, pollster);
    ctx.redirect("/users/" + username);
  }

  public void eliminar(Context ctx) {
    String username = ctx.pathParam("username");
    User usuarioLogueado = ctx.sessionAttribute("user");

    assert usuarioLogueado != null;
    if(usuarioLogueado.getUsername().equals(username)) {
      ctx.sessionAttribute("usuario_autoDelete", "No puedes eliminarte a ti mismo.");
      ctx.redirect("/users");
      return;
    }

    userService.desactivate(username);
    ctx.redirect("/users");
  }

  @Override
  public void applyRoutes() {
    app.routes(() -> path("/users", () -> {
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
