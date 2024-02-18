package org.example;
import controllers.*;
import encapsulation.Articulo;
import encapsulation.Comentario;
import encapsulation.Etiqueta;
import encapsulation.Usuario;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.bundled.CorsPluginConfig;
import services.*;

import java.util.Set;

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

    BootstrapService bootstrapService = new BootstrapService();
    bootstrapService.startDb();

    ArticuloService articuloService = new ArticuloService();
    ComentarioService comentarioService = new ComentarioService();
    EtiquetaService etiquetaService = new EtiquetaService();
    UsuarioService usuarioService = new UsuarioService();

    //------------------------------------CREATION------------------------------//
    Usuario usuario = new Usuario();
    usuario.setUsername("admin");
    usuario.setNombre("Administrador");
    usuario.setPassword("admin");
    usuario.setAdmin(true);
    usuario.setAutor(true);
    Usuario usuario2 = new Usuario();
    usuario2.setUsername("user");
    usuario2.setNombre("Usuario");
    usuario2.setPassword("user");
    usuario2.setAdmin(false);
    usuario2.setAutor(true);
    Usuario usuario3 = new Usuario();
    usuario3.setUsername("user2");
    usuario3.setNombre("Usuario2");
    usuario3.setPassword("user2");
    usuario3.setAdmin(false);
    usuario3.setAutor(false);
    usuarioService.create(usuario);
    usuarioService.create(usuario2);
    usuarioService.create(usuario3);

    Etiqueta etiqueta = new Etiqueta();
    etiqueta.setNombre("Movies");
    Etiqueta etiqueta2 = new Etiqueta();
    etiqueta2.setNombre("Music");
    Etiqueta etiqueta3 = new Etiqueta();
    etiqueta3.setNombre("Java");
    etiquetaService.create(etiqueta);
    etiquetaService.create(etiqueta2);
    etiquetaService.create(etiqueta3);

    Articulo articulo = new Articulo();
    articulo.setTitulo("Cookies");
    articulo.setCuerpo("El manejo de cookies y sesiones en una aplicación web es una tarea fundamental que recae principalmente en el backend. Las cookies, pequeños fragmentos de datos almacenados en el navegador del usuario, son esenciales para mantener el estado de la sesión, gestionar la autenticación y personalizar la experiencia del usuario. Por otro lado, las sesiones, administradas por el servidor, permiten mantener la continuidad de la interacción entre el usuario y la aplicación a lo largo de múltiples solicitudes. A través de una combinación efectiva de cookies y sesiones, se logra una experiencia de usuario coherente y segura en la web.");
//    articulo.setEtiquetas(Set.of(etiqueta, etiqueta2, etiqueta3));
    articulo.setAutor(usuario);
    articulo.setFecha(new java.util.Date());
    Articulo articulo2 = new Articulo();
    articulo2.setTitulo("Javalin");
    articulo2.setCuerpo("Javalin es un framework ligero y fácil de usar para el desarrollo de aplicaciones web y API REST en Java. Con una sintaxis sencilla y una API intuitiva, Javalin permite a los desarrolladores crear rápidamente servicios web robustos y escalables. Ofrece características como enrutamiento dinámico, manejo de solicitudes HTTP, gestión de sesiones, soporte para WebSockets y un sistema de plugins extensible. Además, Javalin es altamente adaptable y se integra fácilmente con otras tecnologías y bibliotecas Java, lo que lo convierte en una opción popular para desarrolladores que buscan una solución eficiente y moderna para sus proyectos web.");
//    articulo2.setEtiquetas(Set.of(etiqueta, etiqueta2, etiqueta3));
    articulo2.setAutor(usuario2);
    articulo2.setFecha(new java.util.Date());
    Articulo articulo3 = new Articulo();
    articulo3.setTitulo("NestJS");
    articulo3.setCuerpo("NestJS es un framework de desarrollo de aplicaciones web progresivas y servidores de API Node.js que utiliza TypeScript como su lenguaje principal. Construido con una arquitectura modular y basada en inyección de dependencias, NestJS proporciona una estructura sólida para construir aplicaciones escalables y mantenibles. Ofrece una amplia gama de características, incluyendo enrutamiento basado en controladores, middleware, validación de datos, gestión de solicitudes HTTP, integración con bases de datos, soporte para WebSockets y más. Además, su ecosistema está respaldado por una comunidad activa y una documentación exhaustiva, lo que lo convierte en una opción atractiva para desarrolladores que buscan una solución moderna y eficiente para sus proyectos Node.js.");
//    articulo3.setEtiquetas(Set.of(etiqueta, etiqueta2, etiqueta3));
    articulo3.setAutor(usuario3);
    articulo3.setFecha(new java.util.Date());
    articuloService.create(articulo);
    articuloService.create(articulo2);
    articuloService.create(articulo3);

    Comentario comentario = new Comentario();
    comentario.setComentario("Excelente articulo");
    comentario.setAutor(usuario);
    comentario.setArticulo(articulo);
    comentarioService.create(comentario);
    Comentario comentario2 = new Comentario();
    comentario2.setComentario("Muy interesante");
    comentario2.setAutor(usuario2);
    comentario2.setArticulo(articulo2);
    comentarioService.create(comentario2);
    Comentario comentario3 = new Comentario();
    comentario3.setComentario("No me gusto");
    comentario3.setAutor(usuario3);
    comentario3.setArticulo(articulo3);
    comentarioService.create(comentario3);

    //--------------------------------------------------------------------------//

    new ArticuloController(app, articuloService, etiquetaService, comentarioService).applyRoutes();
    new ComentarioController(app, comentarioService).applyRoutes();
    new EtiquetaController(app, etiquetaService).applyRoutes();
    new UsuarioController(app, usuarioService).applyRoutes();
    new AuthController(app, usuarioService).applyRoutes();

    app.get("/", ctx -> ctx.redirect("/articulos"));
  }
}