package services;

import encapsulation.Foto;
import io.javalin.http.UploadedFile;
import util.BaseServiceDatabase;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

public class FotoService extends BaseServiceDatabase<Foto> {
    public FotoService() {
        super(Foto.class);
    }

    public Foto create (UploadedFile file){
        Foto foto = new Foto();
        try {
            byte[] bytes;
            if(file == null || file.filename().isEmpty()){
                File defaultImage = new File("src/main/resources/public/pictures/default.jpg");
                bytes = Files.readAllBytes(defaultImage.toPath());
                foto.setNombre(defaultImage.getName());
                foto.setMimeType(Files.probeContentType(defaultImage.toPath()));
            } else {
                bytes = file.content().readAllBytes();
                foto.setNombre(file.filename());
                foto.setMimeType(file.contentType());
            }
            String encodedString = Base64.getEncoder().encodeToString(bytes);
            foto.setFotoBase64(encodedString);
            this.createInDatabase(foto);
        } catch (Exception e){
            e.printStackTrace();
        }
        return foto;
    }

    public Foto createInDatabase(Foto foto) {
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
