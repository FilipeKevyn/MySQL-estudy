package model.dao;

import db.DbException;
import model.entidades.Departamento;
import model.entidades.Vendedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VendedorDAO implements DAO<Vendedor>{
    private Connection conn;
    public VendedorDAO(Connection conn){
        this.conn = conn;

    }
    @Override
    public void inserir(Vendedor obj) {

    }

    @Override
    public void atualizar(Vendedor obj) {

    }

    @Override
    public void deletarById(Integer id) {

    }

    @Override
    public Vendedor encontrarById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id"
                    + "WHERE seller.Id = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()){
                Departamento departamento = new Departamento();
                departamento.setId(rs.getInt("DepartmentId"));
                departamento.setNome(rs.getString("DepName"));

                Vendedor vendedor = new Vendedor();
                vendedor.setId(rs.getInt("Id"));
                vendedor.setNome(rs.getString("Name"));
                vendedor.setSalario(rs.getDouble("BaseSalary"));
                vendedor.setAniversarioData(rs.getDate("BirthDate"));
                vendedor.setDepartamento(departamento);

                return vendedor;
            }
            return null;

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Vendedor> encontrarTodos() {
        return null;
    }
}
