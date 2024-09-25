package application;

import db.DB;
import model.dao.DAOFactory;
import model.dao.VendedorDAO;
import model.entidades.Vendedor;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        VendedorDAO vendedorDAO = DAOFactory.criarVendedorDAO();

        Vendedor vendedor = vendedorDAO.encontrarById(3);

        System.out.println(vendedor);
    }
}
