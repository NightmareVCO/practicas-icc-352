package services;

import entities.Coordinates;
import util.BaseServiceDatabase;

public class CoordenatesService extends BaseServiceDatabase<Coordinates> {
  public CoordenatesService() {
    super(Coordinates.class);
  }

  public Coordinates createInDatabase(Coordinates coordinates) {
    return this.dbCreate(coordinates);
  }

  public Coordinates modifyInDatabase(Coordinates coordinates) {
    return this.dbModify(coordinates);
  }

  public boolean delete(String id) {
    return this.dbRemove(id);
  }

  public Coordinates find(String id) {
    return this.dbFind(id);
  }

  public Coordinates create(String latitude, String longitude) {
    Coordinates coordinates = new Coordinates();
    double lat = Double.parseDouble(latitude);
    double lon = Double.parseDouble(longitude);
    coordinates.setLatitude(lat);
    coordinates.setLongitude(lon);

    return this.createInDatabase(coordinates);
  }


}
