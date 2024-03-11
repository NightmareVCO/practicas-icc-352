package controllers;

import encapsulation.Chat;
import encapsulation.Usuario;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.websocket.*;
import org.eclipse.jetty.websocket.api.Session;
import services.ChatService;
import util.BaseController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatController extends BaseController {

  private final ChatService chatService;
  private final List<Session> usuariosConectados;
  private final Map<String, StringBuilder> mensajesChat;
  private final Map<Integer, Map<String, Session>> usuariosChat;

  public ChatController(Javalin app, ChatService chatService, List<Session> usuariosConectados, Map<String, StringBuilder> mensajesChat, Map<Integer, Map<String, Session>> usuariosChat) {
    super(app);
    this.chatService = chatService;
    this.usuariosConectados = usuariosConectados;
    this.mensajesChat = mensajesChat;
    this.usuariosChat = usuariosChat;
  }

  public void proteger(Context ctx) {
    Usuario usuario = ctx.sessionAttribute("usuario");
    if (usuario == null || !usuario.isAdmin() || !usuario.isAutor())
      ctx.redirect("/auth/login");
  }

  public void allChats(Context ctx) {
    Map<String, Object> modelo = setModelo(
      "chats", chatService.getChats()
    );
    ctx.render("/public/templates/chats.html", modelo);
  }

  public void newChat(Context ctx) {
    String nombreUsuario = ctx.formParam("nombre");
    Chat chat = chatService.createNewChat(nombreUsuario);

    ctx.redirect("/chat/" + chat.getId());
  }

  public void showChat(Context ctx) {
    int chatId = Integer.parseInt(ctx.pathParam("chatId"));
    Chat chat = chatService.getChatById(chatId);

    Map<String, Object> modelo = setModelo(
      "usuario", chat.getUsuario(),
      "chat", chat
    );
    ctx.render("/public/templates/chatbox.html", modelo);
  }

  public void responderChat(Context ctx) {
    Usuario usuario = ctx.sessionAttribute("usuario"); assert usuario != null;
    int idChat = Integer.parseInt(ctx.pathParam("chatId"));
    Chat chat = chatService.getChatById(idChat);
    chat.setAgente(usuario.getUsername());


    Map<String, Object> modelo = setModelo(
      "usuario", chat.getUsuario(),
      "agente", chat.getAgente(),
      "chat", chat
    );

    ctx.render("/public/templates/chatbox.html", modelo);
  }

  public void emptyChat(Context ctx) {
    ctx.render("/public/templates/chatbox.html");
  }

  public void onConnect(WsConnectContext ctx) throws IOException {
    // Agregar el usuario a la lista de usuarios conectados
    usuariosConectados.add(ctx.session);
    String chatId = ctx.pathParam("chatId");
    Usuario usuario = ctx.sessionAttribute("usuario");

    // Vincular el chatId con el sessionId, para el filtrado de mensajes
    usuariosChat.computeIfAbsent(Integer.valueOf(chatId), key -> new ConcurrentHashMap<>()).put(ctx.getSessionId(), ctx.session);
    // Obtener los mensajes del chat
    StringBuilder mensajes = mensajesChat.get(chatId);
    // Sin mensajes anteriores
    if(mensajes == null) {
      mensajes = new StringBuilder();
      mensajesChat.put(chatId, mensajes);
      return;
    }
    // Listado de mensajes separados por salto de linea "\n" pero dentro de un objeto String
    // Cargo los mensajes anteriores, como objeto String
    String mensajesAnteriores = mensajes.toString();
    // Puede existir el objeto pero estar vació
    if(mensajesAnteriores.isEmpty())
      return;

    // Mensajes anteriores separados por salto de linea "\n"
    String[] mensajesAnterioresSeparados = mensajesAnteriores.split("\n");
    // Obtendremos el usuario que los mando solo la primera vez para cargar su nombre en los primeros mensajes.
    Chat chat = chatService.getChatById(Integer.parseInt(chatId));
    // Enviar los mensajes anteriores al admin/autor
    for(String mensaje : mensajesAnterioresSeparados)
      ctx.session.getRemote().sendString(mensaje);
  }

  public void onMessage(WsMessageContext ctx) throws IOException {
    int chatId = Integer.parseInt(ctx.pathParam("chatId"));
    Usuario usuario = ctx.sessionAttribute("usuario");
    Chat chat = chatService.getChatById(chatId);
    String mensaje = ctx.message(); chat.setMensaje(mensaje);
    // Guardar el nombre del usuario que mandó el mensaje
    String username = usuario != null ? usuario.getUsername() : chatService.getChatById(chatId).getUsuario();
    enviarMensajeAClientesConectados(mensaje, chatId, username);
    guardarMensajesEnChat(mensaje, String.valueOf(chatId), username);
  }

  public void enviarMensajeAClientesConectados(String mensaje, int chatId, String username) throws IOException {
    // De forma explícita agregamos el usuario que lo mandó al momento de guardar el mensaje, en este momento el mensaje no tiene el usuario.
    for(Session connectedSessions : usuariosChat.get(chatId).values()){
      String mensajeConUsuario = username + ": " + mensaje;
      connectedSessions.getRemote().sendString(mensajeConUsuario);
    }
  }

  public void guardarMensajesEnChat(String mensajes, String chatId, String username) {
    StringBuilder mensajesDelChat = mensajesChat.computeIfAbsent(chatId, key -> new StringBuilder());
    // Para cuando los mensajes se guarden en memoria se guardan con el usuario que los mando.
    // El usuario no sale repetido, ya que al momento de ver un mensaje en vivo no se guarda el nombre del usuario.
    mensajesDelChat.append(username).append(": ").append(mensajes).append("\n");
  }

  public void onBinaryMessage(WsBinaryMessageContext ctx) {
    System.out.println("Denzel Crocker was here!");
  }

  public void onClose(WsCloseContext ctx) {
    usuariosConectados.remove(ctx.session);
    int  chatId = Integer.parseInt(ctx.pathParam("chatId"));
    usuariosChat.get(chatId).remove(ctx.getSessionId());
  }

  public void onError(WsErrorContext ctx) {
    System.out.print("Ocurrió un error en el WS");
  }



  @Override
  public void applyRoutes() {
    app.get("/emptyChat", this::emptyChat);
    app.post("/chat", this::newChat);
    app.before("/chats", this::proteger);
    app.get("/chats", this::allChats);
    app.get("/chat/{chatId}", this::showChat);
    app.get("/chatResponse/{chatId}", this::responderChat);

    app.ws("/mensajeServidor/{chatId}", ws -> {
      ws.onConnect(this::onConnect);
      ws.onMessage(this::onMessage);
      ws.onBinaryMessage(this::onBinaryMessage);
      ws.onClose(this::onClose);
      ws.onError(this::onError);
    });
  }
}