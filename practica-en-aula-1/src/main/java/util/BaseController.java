package util;

import io.javalin.Javalin;

public abstract class BaseController {

  protected static Javalin app;

  public BaseController(Javalin app){
    this.app = app;
  }

  abstract public void applyRoutes();
}
