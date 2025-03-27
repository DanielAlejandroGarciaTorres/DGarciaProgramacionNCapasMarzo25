package com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Alumno;
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
                            Alumno alumno = new Alumno();
                            alumno.setIdAlumno(resultSet.getInt("idAlumno"));
                            alumno.setNombre(resultSet.getString("NombreAlumno"));
                            alumno.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                            alumno.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                            alumno.Semestre = new Semestre();
                            alumno.Semestre.setIdSemestre(resultSet.getInt("IdSemestre"));
                            alumno.Semestre.setNombre(resultSet.getString("NombreSemestre"));

                            result.objects.add(alumno);
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

}
