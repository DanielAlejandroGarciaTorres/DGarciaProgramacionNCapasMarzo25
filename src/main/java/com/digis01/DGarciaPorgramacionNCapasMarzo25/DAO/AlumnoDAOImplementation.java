package com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Alumno;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.AlumnoDireccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Colonia;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Direccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Estado;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Municipio;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Semestre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository // logica de base de datos
public class AlumnoDAOImplementation implements IAlumnoDAO {

    @Autowired //Inyección dependencias (field, contructor, setter)    conexion de JDBC
    private JdbcTemplate jdbcTemplate; // conexión directa 

    @Autowired // conexión de JPA
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL AlumnoGetAll(?)}",
                    (CallableStatementCallback<Integer>) callableStatement -> {

                        callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                        result.objects = new ArrayList<>();

                        while (resultSet.next()) {

                            int idAlumno = resultSet.getInt("IdAlumno");
                            if (!result.objects.isEmpty() && idAlumno == ((AlumnoDireccion) (result.objects.get(result.objects.size() - 1))).Alumno.getIdAlumno()) {
                                /*agregamos solo direccion*/
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                                ((AlumnoDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                            } else { // el id no es el mismo ó la lista es nueva
                                AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
                                alumnoDireccion.Alumno = new Alumno();
                                alumnoDireccion.Alumno.setIdAlumno(resultSet.getInt("idAlumno"));
                                alumnoDireccion.Alumno.setNombre(resultSet.getString("NombreAlumno"));
                                alumnoDireccion.Alumno.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                                alumnoDireccion.Alumno.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                                alumnoDireccion.Alumno.setUsername(resultSet.getString("Username"));
                                alumnoDireccion.Alumno.setImagen(resultSet.getString("Imagen"));
                                alumnoDireccion.Alumno.Semestre = new Semestre();
                                alumnoDireccion.Alumno.Semestre.setIdSemestre(resultSet.getInt("IdSemestre"));
                                alumnoDireccion.Alumno.Semestre.setNombre(resultSet.getString("NombreSemestre"));
                                alumnoDireccion.Direcciones = new ArrayList<>();
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                                alumnoDireccion.Direcciones.add(direccion);
                                result.objects.add(alumnoDireccion);
                            }
                        }
                        result.correct = true;
                        return 1;
                    });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }

        return result;
    }

    @Override
    public Result Add(AlumnoDireccion alumnoDireccion) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL AlumnoDireccionAdd(?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setString(1, alumnoDireccion.Alumno.getNombre());
                callableStatement.setString(2, alumnoDireccion.Alumno.getApellidoPaterno());
                callableStatement.setString(3, alumnoDireccion.Alumno.getApellidoMaterno());
                callableStatement.setInt(4, alumnoDireccion.Alumno.Semestre.getIdSemestre());
                callableStatement.setString(5, alumnoDireccion.Alumno.getUsername());
                callableStatement.setString(6, alumnoDireccion.Alumno.getEmail());
                callableStatement.setDate(7, new java.sql.Date(alumnoDireccion.Alumno.getFechaNacimiento().getTime()));
                callableStatement.setString(8, alumnoDireccion.Direccion.getCalle());
                callableStatement.setString(9, alumnoDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(10, alumnoDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(11, alumnoDireccion.Direccion.Colonia.getIdColonia());
                callableStatement.setString(12, alumnoDireccion.Alumno.getImagen());
                callableStatement.registerOutParameter(13, Types.INTEGER);

                callableStatement.executeUpdate();
                int rowAffected = callableStatement.getInt(13);

                result.correct = rowAffected > 0 ? true : false;

                return 1;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result direccionesByIdUsuario(int IdAlumno) {
        Result result = new Result();

        try {

            jdbcTemplate.execute("CALL DireccionesByIdAlumno(?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.setInt(3, IdAlumno);

                callableStatement.execute();

                AlumnoDireccion alumnoDireccion = new AlumnoDireccion();

                ResultSet resultSetAlumno = (ResultSet) callableStatement.getObject(1);

                if (resultSetAlumno.next()) {
                    alumnoDireccion.Alumno = new Alumno();
                    alumnoDireccion.Alumno.setIdAlumno(resultSetAlumno.getInt("IdAlumno"));
                    alumnoDireccion.Alumno.setNombre(resultSetAlumno.getString("NombreAlumno"));
                    alumnoDireccion.Alumno.setApellidoPaterno(resultSetAlumno.getString("ApellidoPaterno"));
                    alumnoDireccion.Alumno.setApellidoMaterno(resultSetAlumno.getString("ApellidoMaterno"));
                }

                ResultSet resultSetDireccion = (ResultSet) callableStatement.getObject(2);

                alumnoDireccion.Direcciones = new ArrayList();

                while (resultSetDireccion.next()) {

                    Direccion direccion = new Direccion();

                    direccion.setIdDireccion(resultSetDireccion.getInt("IdDireccion"));
                    direccion.setCalle(resultSetDireccion.getString("Calle"));
                    direccion.setNumeroExterior(resultSetDireccion.getString("NumeroExterior"));
                    direccion.setNumeroInterior(resultSetDireccion.getString("NumeroInterior"));
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSetDireccion.getInt("IdColonia"));
                    direccion.Colonia.setNombre(resultSetDireccion.getString("NombreColonia"));

                    alumnoDireccion.Direcciones.add(direccion);
                }

                result.object = alumnoDireccion;
                result.correct = true;
                return 1;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetById(int IdAlumno) {
        Result result = new Result();

        try {

            jdbcTemplate.execute("CALL AlumnoById(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, IdAlumno);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                if (resultSet.next()) {
                    AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
                    alumnoDireccion.Alumno = new Alumno();
                    alumnoDireccion.Alumno.setIdAlumno(resultSet.getInt("IdAlumno"));
                    alumnoDireccion.Alumno.setNombre(resultSet.getString("NombreAlumno"));
                    alumnoDireccion.Alumno.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    alumnoDireccion.Alumno.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    alumnoDireccion.Alumno.Semestre = new Semestre();
                    alumnoDireccion.Alumno.Semestre.setIdSemestre(resultSet.getInt("IdSemestre"));
                    alumnoDireccion.Alumno.Semestre.setNombre(resultSet.getString("NombreSemestre"));

                    result.object = alumnoDireccion;
                }

                result.correct = false;
                return 1;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result Update(Alumno alumno) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL AlumnoUpdate(?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, alumno.getIdAlumno());
                callableStatement.setString(2, alumno.getNombre());
                callableStatement.setString(3, alumno.getApellidoPaterno());
                callableStatement.setString(4, alumno.getApellidoMaterno());

                int rowsAffected = callableStatement.executeUpdate();

                if (rowsAffected > 0) {
                    result.correct = true;
                } else {
                    result.correct = false;
                    result.errorMessage = "Error en la actualización";
                }

                return 1;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetAllDinamico(Alumno alumno) {
        Result result = new Result();

        try {

            jdbcTemplate.execute("CALL AlumnoGetAllDinamico(?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setString(1, alumno.getNombre());
                callableStatement.setString(2, alumno.getApellidoPaterno());
                callableStatement.setString(3, alumno.getApellidoMaterno());
                callableStatement.setInt(4, alumno.Semestre.getIdSemestre());
                callableStatement.registerOutParameter(5, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);

                result.objects = new ArrayList<>();

                while (resultSet.next()) {
                    AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
                    alumnoDireccion.Alumno = new Alumno();
                    alumnoDireccion.Alumno.setIdAlumno(resultSet.getInt("IdAlumno"));
                    alumnoDireccion.Alumno.setNombre(resultSet.getString("NombreAlumno"));
                    alumnoDireccion.Alumno.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    alumnoDireccion.Alumno.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    alumnoDireccion.Alumno.Semestre = new Semestre();
                    alumnoDireccion.Alumno.Semestre.setIdSemestre(resultSet.getInt("IdSemestre"));
                    alumnoDireccion.Alumno.Semestre.setNombre(resultSet.getString("NombreSemestre"));

                    result.objects.add(alumnoDireccion);
                }

                return 1;
            });

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }

        return result;
    }

    @Override
    public Result GetAllJPA() {
        //  Esto es lenguaje JPQL
        Result result = new Result();
        try {

            TypedQuery<com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno> queryAlumnos = entityManager.createQuery("FROM Alumno", com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno.class);
            List<com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno> alumnos = queryAlumnos.getResultList();
            result.objects = new ArrayList<>();
            for (com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno alumno : alumnos) {

                AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
                alumnoDireccion.Alumno = new Alumno();
                alumnoDireccion.Alumno.setIdAlumno(alumno.getIdAlumno());
                alumnoDireccion.Alumno.setNombre(alumno.getNombre());
                alumnoDireccion.Alumno.setApellidoPaterno(alumno.getApellidoPaterno());
                alumnoDireccion.Alumno.setApellidoMaterno(alumno.getApellidoMaterno());
                alumnoDireccion.Alumno.setEmail(alumno.getEmail());

                TypedQuery<com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Direccion> queryDireccion = entityManager.createQuery("FROM Direccion WHERE Alumno.IdAlumno = :idalumno", com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Direccion.class);
                queryDireccion.setParameter("idalumno", alumno.getIdAlumno());

                List<com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Direccion> direccionesJPA = queryDireccion.getResultList();
                alumnoDireccion.Direcciones = new ArrayList();
                for (com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Direccion direccionJPA : direccionesJPA) {
                    Direccion direccion = new Direccion();
                    direccion.setCalle(direccionJPA.getCalle());
                    direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
                    direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                    direccion.Colonia = new Colonia();

                    direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());

                    alumnoDireccion.Direcciones.add(direccion);
                }

                result.objects.add(alumnoDireccion);

            }

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    @Transactional
    @Override
    public Result AddJPA(AlumnoDireccion alumnoDireccion) {
        Result result = new Result();

        try {

            //llenado de alumnoJPA con el alumnoML
            com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno alumnoJPA = new com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno();
            alumnoJPA.setNombre(alumnoDireccion.Alumno.getNombre());
            alumnoJPA.setApellidoPaterno(alumnoDireccion.Alumno.getApellidoPaterno());
            alumnoJPA.setApellidoMaterno(alumnoDireccion.Alumno.getApellidoMaterno());
            alumnoJPA.Semestre = new com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Semestre();
            alumnoJPA.Semestre.setIdSemestre(alumnoDireccion.Alumno.Semestre.getIdSemestre());
            alumnoJPA.setEmail(alumnoDireccion.Alumno.getEmail());
            alumnoJPA.setUsername(alumnoDireccion.Alumno.getUsername());
            alumnoJPA.setPassword(passwordEncoder.encode(alumnoDireccion.Alumno.getPassword()));
            alumnoJPA.setFechaNacimiento(alumnoDireccion.Alumno.getFechaNacimiento());

            entityManager.persist(alumnoJPA);

            /*inserción de dirección*/
            com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Direccion direccionJPA
                    = new com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Direccion();
            direccionJPA.setCalle(alumnoDireccion.Direccion.getCalle());
            direccionJPA.setNumeroExterior(alumnoDireccion.Direccion.getNumeroExterior());
            direccionJPA.setNumeroInterior(alumnoDireccion.Direccion.getNumeroInterior());
            direccionJPA.Colonia = new com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(alumnoDireccion.Direccion.Colonia.getIdColonia());
            direccionJPA.Alumno = alumnoJPA;

            /*direccionJPA.Alumno = new com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno();
            direccionJPA.Alumno.setIdAlumno(alumnoJPA.getIdAlumno());*/
            entityManager.persist(direccionJPA);

            System.out.println("");

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    /*
    CRUD Usuario
    - agregar usuario direccion
        - getbymunicipio
        - getbyesta ...   JPQL - Typedquery
    - agregar direccion
    - editar usuario
    - editar direccion
    - eliminar usuario - direccciones
    - eliminar direccion
    
    
    
    busqueda dinámica  JPQL
     */
    @Override
    public Result GetAllDinamicoJPA(Alumno alumno) {
        Result result = new Result();

        try {
            // nombre, apaterno, amaterno, idsemestre

            String queryDinamico = "FROM Usuario";

            queryDinamico = queryDinamico + " WHERE Nombre LIKE :nombre";
            queryDinamico = queryDinamico + " AND  ApellidoPaterno = :apaterno ";
            queryDinamico = queryDinamico + " AND AMaterno = :amaterno ";
            //validar si se concatena lo de semestre

            TypedQuery<com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno> queryAlumno = entityManager.createQuery(queryDinamico, com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno.class);
            queryAlumno.setParameter("%" + ":nombre" + "%", alumno.getNombre());
            queryAlumno.setParameter(":apaterno", alumno.getApellidoPaterno());
            queryAlumno.setParameter(":amaterno", alumno.getApellidoMaterno());
            List<com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno> alumnos = queryAlumno.getResultList();

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result DeleteJPA(int IdAlumno) {
        Result result = new Result();

        //Eliminar primero las direcciones y luego el usuario
        com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno alumno = new com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno();
        alumno = entityManager.find(com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno.class, IdAlumno);

        entityManager.remove(alumno);

        return result;
    }

    @Transactional
    @Override
    public Result UpdateAlumnoJPA(Alumno alumno) {
        Result result = new Result();

        com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno alumnoJPA = new com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno();
        //alumnoJPA = entityManager.find(com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno.class, alumno.getIdAlumno());

        //vaciar alumno ML a alumno JPA
        entityManager.merge(alumnoJPA);

        return result;
    }

    @Override
    public Result DireccionUsuarioByIdAlumnoJPA(int IdAlumno) {
        Result result = new Result();

        try {

            com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno alumnoJPA
                    = entityManager.find(com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno.class, IdAlumno);

            TypedQuery<com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Direccion> queryDireccion
                    = entityManager.createQuery("FROM Direccion WHERE Alumno.IdAlumno = :idalumno", com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Direccion.class);
            queryDireccion.setParameter("idalumno", IdAlumno);

            List<com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Direccion> direccionesJPA
                    = queryDireccion.getResultList();

            /*vaciar info en ML*/
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
