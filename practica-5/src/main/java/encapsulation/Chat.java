package encapsulation;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Chat {
  private int id;
  private String usuario;
  private String agente;
  private String mensaje;

public Chat(int id, String usuario, String agente, String mensaje) {
    this.id = id;
    this.usuario = usuario;
    this.agente = agente;
    this.mensaje = mensaje;
}

  public Chat(int id, String usuario) {
    this.id = id;
    this.usuario = usuario;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getAgente() {
    return agente;
  }

  public void setAgente(String agente) {
    this.agente = agente;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }
}
