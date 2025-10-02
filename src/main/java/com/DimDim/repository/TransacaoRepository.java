package com.DimDim.repository;

import com.DimDim.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    // Buscar todas as transações de uma conta
    List<Transacao> findByContaId(Long contaId);

    // Buscar transações de um cliente (através da conta)
    List<Transacao> findByContaClienteId(Integer clienteId);
}
