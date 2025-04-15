package com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Colonia;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Direccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DireccionDAOImplementation implements IDireccionDAO{

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetByIdDireccion(int IdDireccion) {
        Result result = new Result();
        
        try {
            
            jdbcTemplate.execute("{CALL DireccionGetByIdDireccion(?,?)}",
                    (CallableStatementCallback<Integer>) callableStatement -> {
                        
                        callableStatement.setInt(1, IdDireccion);
                        callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                        
                        callableStatement.execute();
                        
                        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                        
                        if(resultSet.next()) {
                            
                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            
                            result.object = direccion;
                        }
                        
                        return 1;
                    });
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
