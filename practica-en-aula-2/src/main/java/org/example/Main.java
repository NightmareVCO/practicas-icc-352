package org.example;

import controllers.CrudController;
import encapsulations.Estudiante;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.bundled.CorsPluginConfig;
import services.BootstrapService;
import services.EstudianteService;

public class Main {
  public static void main(String[] args) {

    Javalin app = Javalin.create(config ->{
      config.staticFiles.add(staticFileConfig -> {
        staticFileConfig.hostedPath = "/";
        staticFileConfig.directory = "/publico";
        staticFileConfig.location = Location.CLASSPATH;
        staticFileConfig.precompress=false;
        staticFileConfig.aliasCheck=null;
      });
      config.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost));
    }).start(3000);

    BootstrapService bootstrapService = new BootstrapService();
    bootstrapService.startDb();

    EstudianteService estudianteService = new EstudianteService();
    new CrudController(app, estudianteService).applyRoutes();

    Estudiante estudiante = new Estudiante();
    estudiante.setMatricula(2017);
    estudiante.setNombre("Juan Perez");
    estudiante.setCarrera("Ing. Sistemas");
    estudianteService.crearEstudiante(estudiante);

    Estudiante estudiante2 = new Estudiante();
    estudiante2.setMatricula(2018);
    estudiante2.setNombre("Maria Perez");
    estudiante2.setCarrera("Ing. Industrial");
    estudianteService.crearEstudiante(estudiante2);

    Estudiante estudiante3 = new Estudiante();
    estudiante3.setMatricula(2019);
    estudiante3.setNombre("Pedro Perez");
    estudiante3.setCarrera("Ing. Civil");
    estudianteService.crearEstudiante(estudiante3);

    app.get("/", ctx -> ctx.redirect("/crud-simple/"));

  }
}