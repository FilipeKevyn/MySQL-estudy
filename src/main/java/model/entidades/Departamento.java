package model;

import java.io.Serializable;
import java.util.Objects;

public class Departamento implements Serializable {
    private static long serialVersionUID = 1L;
    private int id;
    private String nome;

    public Departamento(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departamento that = (Departamento) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
