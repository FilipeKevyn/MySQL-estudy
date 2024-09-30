package model.dao;

import db.DB;
import db.DbException;
import model.entidades.Departamento;
import model.entidades.Vendedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()){
                Departamento departamento = instanciarDepartamento(rs);

                Vendedor vendedor = instanciarVendedor(rs, departamento);

                return vendedor;
            }
            return null;

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public List<Vendedor> encontrarPorDep(Departamento dep){
         PreparedStatement st = null;
         ResultSet rs = null;
         try {
             st = conn.prepareStatement(
                     "SELECT seller.*,department.Name as DepName "
                     + "FROM seller INNER JOIN department "
                     + "ON seller.DepartmentId = department.Id "
                     + "WHERE DepartmentId = ? "
                     + "ORDER BY Name"
             );
             st.setInt(1, dep.getId());
             rs = st.executeQuery();

             List<Vendedor> vendedorList = new ArrayList<>();
             Map<Integer, Departamento> map = new HashMap<>();

             while (rs.next()){
                 Departamento departamento = map.get(rs.getInt("DepartmentId"));
                 if (departamento == null){
                     departamento = instanciarDepartamento(rs);
                     map.put(rs.getInt("DepartmentId"), departamento);
                 }

                 Vendedor vendedor = instanciarVendedor(rs, departamento);
                 vendedorList.add(vendedor);
             }
             return vendedorList;

         } catch (SQLException e){
             throw new DbException(e.getMessage());
         }
         finally {
             DB.closeStatement(st);
             DB.closeResultSet(rs);
         }
    }
    @Override
    public List<Vendedor> encontrarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");

            rs = st.executeQuery();

            List<Vendedor> vendedorList = new ArrayList<>();
            Map<Integer, Departamento> map = new HashMap<>();

            while (rs.next()) {

                Departamento departamento = map.get(rs.getInt("DepartmentId"));

                if (departamento == null) {
                    departamento = instanciarDepartamento(rs);
                    map.put(rs.getInt("DepartmentId"), departamento);
                }

                Vendedor vendedor = instanciarVendedor(rs, departamento);
                vendedorList.add(vendedor);
            }
            return vendedorList;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }


    private Departamento instanciarDepartamento(ResultSet rs) throws SQLException{
        Departamento departamento = new Departamento();
        departamento.setId(rs.getInt("DepartmentId"));
        departamento.setNome(rs.getString("DepName"));

        return departamento;
    }

    private Vendedor instanciarVendedor(ResultSet rs, Departamento departamento) throws SQLException{
        Vendedor vendedor = new Vendedor();
        vendedor.setId(rs.getInt("Id"));
        vendedor.setNome(rs.getString("Name"));
        vendedor.setSalario(rs.getDouble("BaseSalary"));
        vendedor.setAniversarioData(rs.getDate("BirthDate"));
        vendedor.setDepartamento(departamento);

        return vendedor;
    }
}
