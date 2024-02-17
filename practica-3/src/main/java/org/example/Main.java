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

    ArticuloService articuloService = new ArticuloService();
    ComentarioService comentarioService = new ComentarioService();
    EtiquetaService etiquetaService = new EtiquetaService();
    UsuarioService usuarioService = new UsuarioService();
    AuthService authService = new AuthService();


    new ArticuloController(app, articuloService, etiquetaService, comentarioService).applyRoutes();
    new ComentarioController(app, comentarioService).applyRoutes();
    new EtiquetaController(app, etiquetaService).applyRoutes();
    new UsuarioController(app, usuarioService).applyRoutes();
    new AuthController(app, authService, usuarioService).applyRoutes();

    app.get("/", ctx -> ctx.redirect("/articulos"));
  }
}