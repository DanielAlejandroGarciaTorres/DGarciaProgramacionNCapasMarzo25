package com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Alumno;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.AlumnoDireccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Colonia;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Direccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Estado;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Municipio;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Semestre;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // logica de base de datos
public class AlumnoDAOImplementation implements IAlumnoDAO {

    @Autowired //Inyección dependencias (field, contructor, setter)
    private JdbcTemplate jdbcTemplate; // conexión directa 

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
            jdbcTemplate.execute("{CALL DireccionesByIdAlumno(?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
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

                int rowAffected = callableStatement.executeUpdate();

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
                    alumnoDireccion.Alumno.setApellidoMaterno(resultSet.getString("IdAlumno"));
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

}
