package com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;

public interface IColoniaDAO {
    Result ColoniaByIdMunicipio(int IdMunicipio);
    
    Result ColoniaByIdMunicipioJPA(int IdMunicipio);
}
