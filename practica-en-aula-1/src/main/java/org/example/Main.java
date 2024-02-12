package org.example;
import controllers.*;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.bundled.CorsPluginConfig;
import services.*;

public class Main {
  public static void main(String[] args) {
    Javalin app = Javalin.create(config ->{
      //configurando los documentos estaticos.
      config.staticFiles.add(staticFileConfig -> {
        staticFileConfig.hostedPath = "/";
        staticFileConfig.directory = "/public";
        staticFileConfig.location = Location.CLASSPATH;
        staticFileConfig.precompress=false;
        staticFileConfig.aliasCheck=null;
      });
      config.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost));
    }).start(3000);

    UsuarioService usuarioService = new UsuarioService();

    new AuthController(app, usuarioService).applyRoutes();

    app.before("/", ctx -> {
      if (ctx.sessionAttribute("usuario") == null) {
        ctx.redirect("/auth/login");
      }
    });
    app.get("/", ctx -> ctx.render("/public/templates/index.html"));
  }
}