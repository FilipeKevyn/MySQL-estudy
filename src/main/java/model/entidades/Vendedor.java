package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Vendedor implements Serializable {
    private int id;
    private String nome;
    private String email;
    private LocalDate aniversarioData;
    private Departamento departamento;

    public Vendedor(int id, String nome, String email, LocalDate aniversarioData, Departamento departamento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.aniversarioData = aniversarioData;
        this.departamento = departamento;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getAniversarioData() {
        return aniversarioData;
    }

    public void setAniversarioData(LocalDate aniversarioData) {
        this.aniversarioData = aniversarioData;
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
        return "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
