package services;

import entities.Coordinates;
import entities.Form;
import entities.User;
import util.BaseServiceDatabase;

import java.util.List;

public class FormService extends BaseServiceDatabase<Form> {
  public FormService() {
    super(Form.class);
  }

  public List<Form> findAll() {
    return this.dbFindAll();
  }

  public Form find(String id) {
    return this.dbFind(id);
  }

  public Form create(String name, String sector, String education, User user, Coordinates coordinates) {
    Form form = new Form();
    form.setName(name);
    form.setSector(sector);
    form.setEducationLevel(education);
    form.setUser(user);
    form.setCoordinates(coordinates);

    return this.createInDatabase(form);
  }

  public Form modify(String id, String name, String sector, String education, User user) {
    Form form = this.find(id);
    form.setName(name);
    form.setSector(sector);
    form.setEducationLevel(education);
    form.setUser(user);

    return this.modifyInDatabase(form);
  }

  public Form createInDatabase(Form form) {
    return this.dbCreate(form);
  }

  public Form modifyInDatabase(Form form) {
    return this.dbModify(form);
  }

  public boolean delete(String id) {
    return this.dbRemove(id);
  }


}
