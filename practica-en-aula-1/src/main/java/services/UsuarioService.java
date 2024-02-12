package services;

import encapsulation.Usuario;

import java.util.ArrayList;

public class UsuarioService {

  private final ArrayList<Usuario> usuarios;

  public UsuarioService() {
    this.usuarios = new ArrayList<>();
    usuarios.add(new Usuario("admin", "vladimir", "admin"));
    usuarios.add(new Usuario("user", "user", "user"));
    usuarios.add(new Usuario("user2", "user2", "user2"));
  }

  public ArrayList<Usuario> findAll() {
    return usuarios;
  }


  public Usuario findByUsername(String username) {
    Usuario usuario = null;

    for (Usuario u : usuarios)
      if (u.getUsername().equals(username))
        usuario = u;

    return usuario;
  }

  public boolean exists(String username) {
    return findByUsername(username) !=  null;
  }

  public boolean checkPassword(Usuario usuario, String password) {
    return usuario.getPassword().equals(password);
  }

  public Usuario findById(Long id) {
    return null;
  }
  public Usuario insert(Usuario entity) {
    usuarios.add(entity);
    return entity;
  }

  public Usuario update(Usuario entity) {
    return null;
  }

  public Usuario delete(Usuario entity) {
    return null;
  }

  public Usuario deleteById(Long id) {
    return null;
  }

  public Usuario deleteAll() {
    return null;
  }
}
