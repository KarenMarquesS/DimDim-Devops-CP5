package com.DimDim.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Conta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String numero;

    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;

    @Column(nullable=false)
    private BigDecimal saldo = BigDecimal.ZERO;

    private String tipo;

    public Conta() {
    }

    public Conta(Long id, String numero, Cliente cliente, BigDecimal saldo, String tipo) {
        this.id = id;
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = saldo;
        this.tipo = tipo;
    }

    // getters and setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getNumero(){return numero;}
    public void setNumero(String numero){this.numero=numero;}
    public Cliente getCliente(){return cliente;}
    public void setCliente(Cliente cliente){this.cliente=cliente;}
    public BigDecimal getSaldo(){return saldo;}
    public void setSaldo(BigDecimal saldo){this.saldo=saldo;}
    public String getTipo(){return tipo;}
    public void setTipo(String tipo){this.tipo=tipo;}
}
