package com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Estado;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstadoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {

            jdbcTemplate.execute("CALL EstadoGetAll(?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                result.objects = new ArrayList<>();
                while(resultSet.next()) {
                    Estado estado = new Estado();
                    
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));
                    
                    result.objects.add(estado);
                }
                
                return 1;
            });

            result.correct = true;
        } catch (Exception ex) {
            result.correct = true;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
