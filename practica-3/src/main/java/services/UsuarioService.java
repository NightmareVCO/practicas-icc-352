package services;

import encapsulation.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import util.BaseServiceDatabase;


import java.util.List;

public class UsuarioService extends BaseServiceDatabase<Usuario> {

  public UsuarioService() {
    super(Usuario.class);
    Usuario usuario = new Usuario();
    usuario.setUsername("admin");
    usuario.setNombre("Administrador");
    usuario.setPassword("admin");
    usuario.setAdmin(true);
    usuario.setAutor(true);Usuario usuario2 = new Usuario();
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

    this.create(usuario);
    this.create(usuario2);
    this.create(usuario3);
  }



  public Usuario find(String username) {
    return this.dbFind(username);
  }

  public List<Usuario> findAll() {
    return this.dbFindAll();
  }

  public Usuario create(Usuario usuario) {
    return this.dbCreate(usuario);
  }

  public Usuario modify(Usuario usuario) {
    return this.dbModify(usuario);
  }

  public boolean delete(String username) {
    return this.dbRemove(username);
  }

  public Usuario findByUsername(String username) {
    try (EntityManager em = getEntityManager()) {
      Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.username = :username");
      query.setParameter("username", username);
      return (Usuario) query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }
}
