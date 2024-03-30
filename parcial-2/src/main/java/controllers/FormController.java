package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Coordinates;
import entities.Form;
import entities.User;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.websocket.WsMessageContext;
import services.CoordenatesService;
import services.FormService;
import services.UserService;
import util.BaseController;

import java.util.List;
import java.util.Map;

public class FormController extends BaseController {

  private final FormService formService;
  private final UserService userService;
  private final CoordenatesService coordenatesService;
  public FormController(Javalin app, FormService formService, UserService userService, CoordenatesService coordenatesService) {
    super(app);
    this.formService = formService;
    this.userService = userService;
    this.coordenatesService = coordenatesService;
  }

  public void entries(Context ctx) {
    List<Form> entries = formService.findAll();

    Map<String, Object> model = setModelo("entries", entries);
    ctx.render("/public/templates/index.html", model);
  }

  public void mainView(Context ctx) {
    ctx.render("/public/templates/cover.html");
  }

  public void getForm(Context ctx) {
    ctx.render("/public/templates/form.html");
  }

  public void postForm(Context ctx) {
    String name = ctx.formParam("name");
    String sector = ctx.formParam("sector");
    String education = ctx.formParam("education");
    String username = ctx.formParam("username");
    User user = userService.find(username);
    String latitude = ctx.formParam("latitude").isEmpty() ? "0" : ctx.formParam("latitude");
    String longitude = ctx.formParam("longitude").isEmpty() ? "0" : ctx.formParam("longitude");


    Coordinates coordinates = coordenatesService.create(latitude, longitude);
    formService.create(name, sector, education, user, coordinates);
    ctx.redirect("/dashboard");
  }

  public void protect (Context ctx) {
    User user = ctx.sessionAttribute("user");
    if (user == null) {
      ctx.redirect("/auth/login");
      return;
    }

    if (!user.isAdmin() && !user.isPollster())
      ctx.redirect("/");

  }

  public void showOne(Context ctx) {
    String id = ctx.pathParam("id");
    Form form = formService.find(id);

    Map<String, Object> model = setModelo("form", form);
    ctx.render("/public/templates/showForm.html", model);
  }

  public void editForm(Context ctx) {
    String id = ctx.pathParam("id");
    String name = ctx.formParam("name");
    String sector = ctx.formParam("sector");
    String education = ctx.formParam("education");
    String username = ctx.formParam("username");
    System.out.println(username);
    User user = userService.find(username);

    formService.modify(id, name, sector, education, user);
    ctx.redirect("/form/" + id);

  }

  public void getOfflineForm(Context ctx) {
    ctx.render("/public/templates/formOffline.html");
  }

  public void getOfflineEntries(Context ctx) {
    ctx.render("/public/templates/dashboardOffline.html");
  }

  public void onMessage(WsMessageContext ctx) {
    String json = ctx.message();
    System.out.println(json);
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      JsonNode jsonNode = objectMapper.readTree(json);
      String name = jsonNode.get("name").asText();
      String sector = jsonNode.get("sector").asText();
      String education = jsonNode.get("educationLevel").asText();
      String username = jsonNode.get("username").asText();
      User user = userService.find(username);
      String latitude = jsonNode.get("latitude").asText();
      String longitude = jsonNode.get("longitude").asText();
      Coordinates coordinates = coordenatesService.create(latitude, longitude);
      formService.create(name, sector, education, user, coordinates);
    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  @Override
  public void applyRoutes() {
    app.get("/", this::mainView);
    app.before("/dashboard", this::protect);
    app.get("/dashboard", this::entries);
    app.before("/dashboardOffline", this::protect);
    app.get("/dashboardOffline", this::getOfflineEntries);
    app.before("/formOffline", this::protect);
    app.before("/form", this::protect);
    app.before("/form/{id}", this::protect);
    app.ws("/ws" , ws -> ws.onMessage(this::onMessage));
    app.post("/form/edit/{id}", this::editForm);
    app.get("/form/{id}", this::showOne);
    app.get("/form", this::getForm);
    app.get("/formOffline", this::getOfflineForm);
    app.post("/form", this::postForm);
  }
}
