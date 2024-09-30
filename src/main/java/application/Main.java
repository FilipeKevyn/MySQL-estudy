package application;

import db.DB;
import model.dao.DAOFactory;
import model.dao.VendedorDAO;
import model.entidades.Departamento;
import model.entidades.Vendedor;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        VendedorDAO vendedorDAO = DAOFactory.criarVendedorDAO();

        System.out.println("=== TEST 1: Vendedor encontrado por Id ===");
        Vendedor vendedor = vendedorDAO.encontrarById(1);
        System.out.println(vendedor);

        System.out.println("\n=== TEST 2: Vendedor encontrado por Id ===");
        Departamento departamento = new Departamento(2, null);
        List<Vendedor> vendedorList = vendedorDAO.encontrarPorDep(departamento);
        vendedorList.stream().forEach(System.out::println);

        System.out.println("\n=== TEST 1: Encontrar todos os vendedores ===");
        List<Vendedor> vendedorList2 = vendedorDAO.encontrarTodos();
        vendedorList2.stream().forEach(System.out::println);
    }
}
