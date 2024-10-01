package model.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Vendedor implements Serializable {
    private Integer id;
    private String nome;
    private String email;
    private Double salario;
    private Date aniversarioData;
    private Departamento departamento;

    public Vendedor(Integer id, String nome, String email, Date aniversarioData, Double salario, Departamento departamento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.aniversarioData = aniversarioData;
        this.salario = salario;
        this.departamento = departamento;
    }

    public Vendedor() {

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

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getAniversarioData() {
        return aniversarioData;
    }

    public void setAniversarioData(Date aniversarioData) {
        this.aniversarioData = aniversarioData;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vendedor vendedor = (Vendedor) o;
        return id == vendedor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Id = " + id +
                ",\nNome = " + nome;
    }
}
