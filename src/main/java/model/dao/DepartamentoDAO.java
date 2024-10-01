package model.dao;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.entidades.Departamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO implements DAO<Departamento> {
    private Connection conn;
    public DepartamentoDAO(Connection conn){
        this.conn = conn;
    }
    @Override
    public void inserir(Departamento obj) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("INSERT INTO department " +
                    "(Name) " +
                    "VALUES " +
                    "(?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getNome());

            int linhasAfetadas = st.executeUpdate();
            if (linhasAfetadas > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void atualizar(Departamento obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE department " +
                    "SET Name = ? " +
                    "WHERE Id = ?");
            st.setString(1, obj.getNome());
            st.setInt(2, obj.getId());

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
            st = conn.prepareStatement(
                    "DELETE FROM department WHERE Id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Departamento encontrarById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT department.* FROM department WHERE Id = ?");
            st.setInt(1, id);

            rs = st.executeQuery();
            if (rs.next()){
                Departamento departamento = instanciarDepartamento(rs);

                return departamento;
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

    @Override
    public List<Departamento> encontrarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT department.* FROM department ORDER by Name");

            rs = st.executeQuery();
            List<Departamento> departamentoList = new ArrayList<>();
            while (rs.next()){
                Departamento departamento = instanciarDepartamento(rs);
                departamentoList.add(departamento);
            }

            return departamentoList;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public Departamento instanciarDepartamento(ResultSet rs) throws SQLException{
        Departamento departamento = new Departamento();
        departamento.setId(rs.getInt("Id"));
        departamento.setNome(rs.getString("Name"));

        return departamento;
    }
}


