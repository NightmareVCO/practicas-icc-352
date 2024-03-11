package services;

import encapsulation.Chat;
import encapsulation.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ChatService {
  private final List<Chat> chats;

  public ChatService() {
    this.chats = new ArrayList<>();
  }
  public List<Chat> getChats() {
    return chats;
  }

  public Chat getChatById(int id) {
    return chats.stream().filter(chat -> chat.getId() == id).findFirst().orElse(null);
  }

  public Chat createNewChat(String usuario) {
    int id = chats.size() + 1;
    Chat chat = new Chat(id, usuario);
    chats.add(chat);
    return chat;
  }

  public Chat createChat(String usuario, String agente, String mensaje) {
      int id = chats.size() + 1;
      Chat chat = new Chat(id, usuario, agente, mensaje);
      chats.add(chat);
      return chat;
  }

  public String getUsuarioChatById(int id) {
    for (Chat chat : chats)
      if (chat.getId() == id)
        return chat.getUsuario();

    return null;
  }


}
