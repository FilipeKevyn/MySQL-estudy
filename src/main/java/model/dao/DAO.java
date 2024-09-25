package model.dao;

import java.util.List;

public interface DAO<T> {
    void inserir(T obj);
    void atualizar(T obj);
    void deletarById(Integer id);
    T encontrarById(Integer id);
    List<T> encontrarTodos();
}
