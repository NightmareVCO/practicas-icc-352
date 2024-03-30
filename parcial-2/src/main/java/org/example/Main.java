package org.example;

import controllers.AuthController;
import controllers.FormController;
import controllers.UserController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.jasypt.util.text.BasicTextEncryptor;
import services.*;

public class Main {
  public static void main(String[] args) {
    Javalin app = Javalin.create(config ->{
      config.staticFiles.add(staticFileConfig -> {
        staticFileConfig.hostedPath = "/";
        staticFileConfig.directory = "/Public";
        staticFileConfig.location = Location.CLASSPATH;
        staticFileConfig.precompress=false;
        staticFileConfig.aliasCheck=(s, s1) -> true;
      });
      config.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost));
    }).start(3000);

    BootstrapService bootstrapService = new BootstrapService();
    bootstrapService.startDb();
    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

    FormService formService = new FormService();
    UserService userService = new UserService();
    CoordenatesService coordenatesService = new CoordenatesService();
    AuthService authService = new AuthService(textEncryptor);

    userService.create("admin", "admin", "admin", true, false);
    userService.create("vladi", "Vladimir", "1234", false, true);
    formService.create("Vladimir", "Software", "Basica", userService.find("admin"), coordenatesService.create("19.46124076202742", "-70.67662810960341"));
    formService.create("Vladimir 2", "Software 2", "Basica", userService.find("admin"), coordenatesService.create("19.76124076202742", "-70.87662810960341"));

    new FormController(app, formService, userService, coordenatesService).applyRoutes();
    new UserController(app, userService).applyRoutes();
    new AuthController(app, userService, authService).applyRoutes();
  }
}