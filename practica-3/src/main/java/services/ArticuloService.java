package services;

import encapsulation.Articulo;
import encapsulation.Comentario;
import encapsulation.Etiqueta;
import encapsulation.Usuario;

import java.util.ArrayList;
import java.util.Date;

public class ArticuloService {
  private final ArrayList<Articulo> articulos;
  public ArticuloService() {
    this.articulos = new ArrayList<>();

    ArrayList<Etiqueta> etiquetas = new ArrayList<>();
    etiquetas.add(new Etiqueta(1L, "Cine"));
    etiquetas.add(new Etiqueta(2L, "Medicina"));
    etiquetas.add(new Etiqueta(3L, "Deportes"));

    articulos.add(new Articulo(1L, "Cookies", "El manejo de cookies y sesiones en una aplicación web es una tarea fundamental que recae principalmente en el backend. Las cookies, pequeños fragmentos de datos almacenados en el navegador del usuario, son esenciales para mantener el estado de la sesión, gestionar la autenticación y personalizar la experiencia del usuario. Por otro lado, las sesiones, administradas por el servidor, permiten mantener la continuidad de la interacción entre el usuario y la aplicación a lo largo de múltiples solicitudes. A través de una combinación efectiva de cookies y sesiones, se logra una experiencia de usuario coherente y segura en la web.", null, new Date(), etiquetas));
    articulos.add(new Articulo(2L, "Javalin",  "Javalin es un framework ligero y fácil de usar para el desarrollo de aplicaciones web y API REST en Java. Con una sintaxis sencilla y una API intuitiva, Javalin permite a los desarrolladores crear rápidamente servicios web robustos y escalables. Ofrece características como enrutamiento dinámico, manejo de solicitudes HTTP, gestión de sesiones, soporte para WebSockets y un sistema de plugins extensible. Además, Javalin es altamente adaptable y se integra fácilmente con otras tecnologías y bibliotecas Java, lo que lo convierte en una opción popular para desarrolladores que buscan una solución eficiente y moderna para sus proyectos web.", null, new Date(), etiquetas));
    articulos.add(new Articulo(3L, "NestJS", "NestJS es un framework de desarrollo de aplicaciones web progresivas y servidores de API Node.js que utiliza TypeScript como su lenguaje principal. Construido con una arquitectura modular y basada en inyección de dependencias, NestJS proporciona una estructura sólida para construir aplicaciones escalables y mantenibles. Ofrece una amplia gama de características, incluyendo enrutamiento basado en controladores, middleware, validación de datos, gestión de solicitudes HTTP, integración con bases de datos, soporte para WebSockets y más. Además, su ecosistema está respaldado por una comunidad activa y una documentación exhaustiva, lo que lo convierte en una opción atractiva para desarrolladores que buscan una solución moderna y eficiente para sus proyectos Node.js.", null, new Date(), etiquetas));

    Usuario usuario = new Usuario("nightmareVCO", "Vladimir Curiel", "123456", false, false);

    ArrayList<Comentario> comentarios = new ArrayList<>();
    comentarios.add(new Comentario(1L, "Excelente artículo", usuario, this.findById(1L)));
    comentarios.add(new Comentario(2L, "Muy interesante", usuario, this.findById(1L)));
    comentarios.add(new Comentario(3L, "Gracias por compartir", usuario, this.findById(1L)));

    articulos.get(0).setComentarios(comentarios);
  }

  public ArrayList<Articulo> findAll() {
    return articulos;
  }

  public Articulo findById(Long id) {
    Articulo articulo = null;

    for (Articulo a : articulos)
      if (a.getId() == id)
        articulo = a;

    return articulo;
  }

  public ArrayList<Articulo> findByEtiqueta(String nombre) {
    ArrayList<Articulo> articulosByTag = new ArrayList<>();

    for (Articulo a : articulos)
      for (Etiqueta e : a.getEtiquetas())
        if (e.getNombre().equals(nombre))
          articulosByTag.add(a);

    return articulosByTag;
  }

  public Articulo insert(Articulo entity) {
    articulos.add(entity);
    return entity;
  }


  public void update(Long id, String contenido, String titulo, ArrayList<Etiqueta> etiqueta) {
    Articulo articulo = this.findById(id);
    articulo.setCuerpo(contenido);
    articulo.setTitulo(titulo);
    articulo.setEtiquetas(etiqueta);
  }

  public void delete(Articulo entity) {
    articulos.remove(entity);
  }

  public Articulo deleteById(Long id) {
    return null;
  }

  public Articulo deleteAll() {
    return null;
  }

  public Long getNextId() {
    return (long) (articulos.size() + 1);
  }
}
