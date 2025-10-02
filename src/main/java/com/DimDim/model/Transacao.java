package com.DimDim.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Transacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name="conta_id") private Conta conta;
    private String tipo;
    private BigDecimal valor;
    private Instant dataHora = Instant.now();
    private String requestId;

    // getters/setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public Conta getConta(){return conta;}
    public void setConta(Conta conta){this.conta=conta;}
    public String getTipo(){return tipo;}
    public void setTipo(String tipo){this.tipo=tipo;}
    public BigDecimal getValor(){return valor;}
    public void setValor(BigDecimal valor){this.valor=valor;}
    public Instant getDataHora(){return dataHora;}
    public void setDataHora(Instant dataHora){this.dataHora=dataHora;}
    public String getRequestId(){return requestId;}
    public void setRequestId(String requestId){this.requestId=requestId;}
}
