package services;

import encapsulations.Estudiante;
import util.BaseServiceDatabase;
import util.NoExisteEstudianteException;

import java.util.ArrayList;
import java.util.List;

public class EstudianteService extends BaseServiceDatabase<Estudiante> {
  public EstudianteService() {
    super(Estudiante.class);
  }

  public Estudiante getEstudiantePorMatricula(Integer matricula) {
    return this.dbFind(matricula);
  }

  public Estudiante crearEstudiante(Estudiante estudiante) {
    if(getEstudiantePorMatricula(estudiante.getMatricula()) != null)
      return null;

    return this.dbCreate(estudiante);
  }

  public List<Estudiante> listarEstudiante() {
    return this.dbFindAll();
  }

  public Estudiante actualizarEstudiante(Estudiante estudiante) {
    Estudiante tmp = getEstudiantePorMatricula(estudiante.getMatricula());
    if(tmp == null){
      throw new NoExisteEstudianteException("No Existe el estudiante: " + estudiante.getMatricula());
    }
    tmp.mezclar(estudiante);
    return this.dbModify(tmp);
  }

  public boolean eliminarEstudiante(Integer matricula) {
    return this.dbRemove(matricula);
  }
}
