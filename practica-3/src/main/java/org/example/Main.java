package org.example;

import controllers.*;
import encapsulation.*;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.jasypt.util.text.BasicTextEncryptor;
import org.postgresql.ds.PGSimpleDataSource;
import services.*;

import java.util.Date;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    Javalin app = Javalin.create(config ->{
      config.staticFiles.add(staticFileConfig -> {
        staticFileConfig.hostedPath = "/";
        staticFileConfig.directory = "/public";
        staticFileConfig.location = Location.CLASSPATH;
        staticFileConfig.precompress=false;
        staticFileConfig.aliasCheck=null;
      });
      config.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost));
    }).start(3000);

    BootstrapService bootstrapService = new BootstrapService();
    bootstrapService.startDb();
    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
    PGSimpleDataSource ds = new PGSimpleDataSource();

    ArticuloService articuloService = new ArticuloService();
    ComentarioService comentarioService = new ComentarioService();
    EtiquetaService etiquetaService = new EtiquetaService();
    UsuarioService usuarioService = new UsuarioService();
    FotoService fotoService = new FotoService();
    AuthService authService = new AuthService(textEncryptor);


    //------------------------------------CREATION------------------------------//
    addInfo(usuarioService, etiquetaService, articuloService, comentarioService, fotoService);
    //--------------------------------------------------------------------------//

    new ArticuloController(app, articuloService, etiquetaService, comentarioService).applyRoutes();
    new ComentarioController(app, comentarioService).applyRoutes();
    new EtiquetaController(app, etiquetaService).applyRoutes();
    new UsuarioController(app, usuarioService, fotoService).applyRoutes();
    new AuthController(app, usuarioService, authService).applyRoutes();

    app.get("/", ctx -> ctx.redirect("/articulos?page=1"));
  }

  public static void addInfo(UsuarioService usuarioService, EtiquetaService etiquetaService, ArticuloService articuloService, ComentarioService comentarioService, FotoService fotoService) {

    Foto foto = fotoService.create(null);

    Usuario usuario = usuarioService.create("admin", "Administrador", "admin", true, true, foto);
    Usuario usuario2 = usuarioService.create("admin2", "Administrador2", "admin2", true, true, foto);
    Usuario usuario3 = usuarioService.create("admin3", "Administrador3", "admin3", true, true, foto);

    Etiqueta etiqueta = etiquetaService.create("Movies");
    Etiqueta etiqueta2 = etiquetaService.create("Music");
    Etiqueta etiqueta3 = etiquetaService.create("Java");

    Articulo articulo = articuloService.create("Cookies", "El manejo de cookies y sesiones en una aplicación web es una tarea fundamental que recae principalmente en el backend. Las cookies, pequeños fragmentos de datos almacenados en el navegador del usuario, son esenciales para mantener el estado de la sesión, gestionar la autenticación y personalizar la experiencia del usuario. Por otro lado, las sesiones, administradas por el servidor, permiten mantener la continuidad de la interacción entre el usuario y la aplicación a lo largo de múltiples solicitudes. A través de una combinación efectiva de cookies y sesiones, se logra una experiencia de usuario coherente y segura en la web.", new Date(),usuario, Set.of(etiqueta));
    Articulo articulo2 = articuloService.create("Javalin", "Javalin es un framework ligero y fácil de usar para el desarrollo de aplicaciones web y API REST en Java. Con una sintaxis sencilla y una API intuitiva, Javalin permite a los desarrolladores crear rápidamente servicios web robustos y escalables. Ofrece características como enrutamiento dinámico, manejo de solicitudes HTTP, gestión de sesiones, soporte para WebSockets y un sistema de plugins extensible. Además, Javalin es altamente adaptable y se integra fácilmente con otras tecnologías y bibliotecas Java, lo que lo convierte en una opción popular para desarrolladores que buscan una solución eficiente y moderna para sus proyectos web.", new Date(),usuario2, Set.of(etiqueta2));
    Articulo articulo3 = articuloService.create("NestJS", "NestJS es un framework de desarrollo de aplicaciones web progresivas y servidores de API Node.js que utiliza TypeScript como su lenguaje principal. Construido con una arquitectura modular y basada en inyección de dependencias, NestJS proporciona una estructura sólida para construir aplicaciones escalables y mantenibles. Ofrece una amplia gama de características, incluyendo enrutamiento basado en controladores, middleware, validación de datos, gestión de solicitudes HTTP, integración con bases de datos, soporte para WebSockets y más. Además, su ecosistema está respaldado por una comunidad activa y una documentación exhaustiva, lo que lo convierte en una opción atractiva para desarrolladores que buscan una solución moderna y eficiente para sus proyectos Node.js.", new Date(),usuario3, Set.of(etiqueta3));

    Comentario comentario = comentarioService.create("Excelente articulo", usuario, articulo);
    Comentario comentario2 = comentarioService.create("Muy interesante", usuario2, articulo2);
    Comentario comentario3 = comentarioService.create("No me gusto", usuario3, articulo3);

    articuloService.addComentario(articulo, comentario);
    articuloService.addComentario(articulo2, comentario2);
    articuloService.addComentario(articulo3, comentario3);
  }
}