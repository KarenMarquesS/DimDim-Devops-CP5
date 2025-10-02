package com.DimDim.service;

import com.DimDim.model.Conta;
import com.DimDim.model.Transacao;
import com.DimDim.repository.ContaRepository;
import com.DimDim.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ContaService {
    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public ContaService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional
    public Conta depositar(Long contaId, BigDecimal valor){
        if(valor.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        Conta c = contaRepository.findById(contaId).orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
        c.setSaldo(c.getSaldo().add(valor));
        contaRepository.save(c);
        Transacao t = new Transacao();
        t.setConta(c);
        t.setTipo("DEPOSITO");
        t.setValor(valor);
        t.setRequestId(UUID.randomUUID().toString());
        transacaoRepository.save(t);
        return c;
    }

    @Transactional
    public Conta sacar(Long contaId, BigDecimal valor){
        if(valor.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        Conta c = contaRepository.findById(contaId).orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
        if(c.getSaldo().compareTo(valor) < 0) throw new IllegalStateException("Saldo insuficiente");
        c.setSaldo(c.getSaldo().subtract(valor));
        contaRepository.save(c);
        Transacao t = new Transacao();
        t.setConta(c);
        t.setTipo("SAQUE");
        t.setValor(valor);
        t.setRequestId(UUID.randomUUID().toString());
        transacaoRepository.save(t);
        return c;
    }
}
