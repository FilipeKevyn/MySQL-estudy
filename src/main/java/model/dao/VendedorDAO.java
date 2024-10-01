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
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("INSERT INTO seller " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentID) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?)"
                    , Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setDate(3, new Date(obj.getAniversarioData().getTime()));
            st.setDouble(4, obj.getSalario());
            st.setInt(5, obj.getDepartamento().getId());

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
            }

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public void atualizar(Vendedor obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE seller " +
                    "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                    "WHERE Id = ?");
            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setDate(3, new Date(obj.getAniversarioData().getTime()));
            st.setDouble(4, obj.getSalario());
            st.setInt(5, obj.getDepartamento().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deletarById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");

            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
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
        vendedor.setEmail(rs.getString("Email"));
        vendedor.setSalario(rs.getDouble("BaseSalary"));
        vendedor.setAniversarioData(rs.getDate("BirthDate"));
        vendedor.setDepartamento(departamento);

        return vendedor;
    }
}
