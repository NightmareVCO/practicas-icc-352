package services;

import encapsulation.Foto;
import util.BaseServiceDatabase;

public class FotoService extends BaseServiceDatabase<Foto> {
    public FotoService() {
        super(Foto.class);
    }

    public Foto create(Foto foto) {
        return this.dbCreate(foto);
    }

    public Foto find(String name) {
        return this.dbFind(name);
    }

    public Foto modify(Foto foto) {
        return this.dbModify(foto);
    }

    public boolean delete(String name) {
        return this.dbRemove(name);
    }

    public Foto findByName(String name) {
        return this.dbFind(name);
    }

}
