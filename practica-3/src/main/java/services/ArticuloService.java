package services;
import encapsulation.Articulo;
import encapsulation.Etiqueta;
import util.BaseServiceDatabase;
import java.util.List;

public class ArticuloService extends BaseServiceDatabase<Articulo> {
  public ArticuloService() {
    super(Articulo.class);

    Etiqueta etiqueta = new Etiqueta();
    etiqueta.setNombre("Movies");
    Etiqueta etiqueta2 = new Etiqueta();
    etiqueta2.setNombre("Music");
    Etiqueta etiqueta3 = new Etiqueta();
    etiqueta3.setNombre("Java");

    Articulo articulo = new Articulo();
    articulo.setTitulo("Cookies");
    articulo.setCuerpo("El manejo de cookies y sesiones en una aplicación web es una tarea fundamental que recae principalmente en el backend. Las cookies, pequeños fragmentos de datos almacenados en el navegador del usuario, son esenciales para mantener el estado de la sesión, gestionar la autenticación y personalizar la experiencia del usuario. Por otro lado, las sesiones, administradas por el servidor, permiten mantener la continuidad de la interacción entre el usuario y la aplicación a lo largo de múltiples solicitudes. A través de una combinación efectiva de cookies y sesiones, se logra una experiencia de usuario coherente y segura en la web.");
    articulo.setEtiquetas(List.of(etiqueta, etiqueta2, etiqueta3));

    Articulo articulo2 = new Articulo();
    articulo2.setTitulo("Javalin");
    articulo2.setCuerpo("Javalin es un framework ligero y fácil de usar para el desarrollo de aplicaciones web y API REST en Java. Con una sintaxis sencilla y una API intuitiva, Javalin permite a los desarrolladores crear rápidamente servicios web robustos y escalables. Ofrece características como enrutamiento dinámico, manejo de solicitudes HTTP, gestión de sesiones, soporte para WebSockets y un sistema de plugins extensible. Además, Javalin es altamente adaptable y se integra fácilmente con otras tecnologías y bibliotecas Java, lo que lo convierte en una opción popular para desarrolladores que buscan una solución eficiente y moderna para sus proyectos web.");
    articulo2.setEtiquetas(List.of(etiqueta, etiqueta2, etiqueta3));

    Articulo articulo3 = new Articulo();
    articulo3.setTitulo("NestJS");
    articulo3.setCuerpo("NestJS es un framework de desarrollo de aplicaciones web progresivas y servidores de API Node.js que utiliza TypeScript como su lenguaje principal. Construido con una arquitectura modular y basada en inyección de dependencias, NestJS proporciona una estructura sólida para construir aplicaciones escalables y mantenibles. Ofrece una amplia gama de características, incluyendo enrutamiento basado en controladores, middleware, validación de datos, gestión de solicitudes HTTP, integración con bases de datos, soporte para WebSockets y más. Además, su ecosistema está respaldado por una comunidad activa y una documentación exhaustiva, lo que lo convierte en una opción atractiva para desarrolladores que buscan una solución moderna y eficiente para sus proyectos Node.js.");
    articulo3.setEtiquetas(List.of(etiqueta, etiqueta2, etiqueta3));

    this.create(articulo);
    this.create(articulo2);
    this.create(articulo3);
  }

  public Articulo find(String articuloId) {
    return this.dbFind(articuloId);
  }
  public List<Articulo> findAll(){
    return this.dbFindAll();
  }
  public Articulo create(Articulo articulo){
    return this.dbCreate(articulo);
  }
  public Articulo modify(Articulo articulo){
    return this.dbModify(articulo);
  }
  public boolean delete(String articuloId){
    return this.dbRemove(articuloId);
  }
}



