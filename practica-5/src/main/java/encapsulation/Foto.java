package encapsulation;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Foto implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;
  private String mimeType;
  @Lob
  private String fotoBase64;

  public Foto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public String getFotoBase64() {
    return fotoBase64;
  }

  public void setFotoBase64(String fotoBase64) {
    this.fotoBase64 = fotoBase64;
  }
}
