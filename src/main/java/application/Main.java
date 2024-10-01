package application;

import model.dao.DAOFactory;
import model.dao.DepartamentoDAO;
import model.entidades.Departamento;

public class Main {
    public static void main(String[] args) {
        DepartamentoDAO departamentoDAO = DAOFactory.criarDepartamentoDAO();

//        System.out.println("====== TESTE - INSERIR ======");
//        Departamento departamento = new Departamento(null, "Celular");
//        departamentoDAO.inserir(departamento);
//        System.out.println(departamento.getId() + " Departamento adicionado");

//          System.out.println("====== TESTE - ATUALIZAR ======");
//          Departamento departamento = departamentoDAO.encontrarById(3);
//          departamento.setNome("Roupas");
//          departamentoDAO.atualizar(departamento);
//          System.out.println(departamento.getId() + " Departamento atualizado");

//        System.out.println("====== TESTE - DELETAR - ENCONTRAR POR ID ======");
//        departamentoDAO.deletarById(7);
//        System.out.println("Departamento Deletado");

        System.out.println("====== TESTE - ENCONTRAR TODOS ======");
        System.out.println(departamentoDAO.encontrarTodos());
    }
}
