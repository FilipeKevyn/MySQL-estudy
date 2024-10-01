package model.dao;

import db.DB;

public class DAOFactory {
    public static VendedorDAO criarVendedorDAO(){
        return new VendedorDAO(DB.getConnection());
    }
    public static DepartamentoDAO criarDepartamentoDAO(){
        return new DepartamentoDAO(DB.getConnection());
    }
}
