package com.DimDim.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import com.dimdim.app.model.Conta;

@Entity
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    private String cpf;
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Conta> contas;

    public Cliente() {
    }

    public Cliente(Long id, String nome, String cpf, String email, List<Conta> contas) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.contas = contas;
    }

    // getters and setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getNome(){return nome;}
    public void setNome(String nome){this.nome=nome;}
    public String getCpf(){return cpf;}
    public void setCpf(String cpf){this.cpf=cpf;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}
    public List<Conta> getContas(){return contas;}
    public void setContas(List<Conta> contas){this.contas=contas;}
}
