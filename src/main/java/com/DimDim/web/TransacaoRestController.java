package com.DimDim.web;

import com.DimDim.model.Transacao;
import com.DimDim.repository.TransacaoRepository;
import com.DimDim.repository.ContaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transacoes")
@Tag(name = "Transações", description = "API para gerenciamento de transações")
public class TransacaoRestController {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @GetMapping
    @Operation(summary = "Listar todas as transações")
    public ResponseEntity<List<Transacao>> listarTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar transação por ID")
    public ResponseEntity<Transacao> buscarTransacao(@PathVariable Long id) {
        Optional<Transacao> transacao = transacaoRepository.findById(id);
        return transacao.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/conta/{contaId}")
    @Operation(summary = "Listar transações de uma conta")
    public ResponseEntity<List<Transacao>> listarTransacoesPorConta(@PathVariable Long contaId) {
        List<Transacao> transacoes = transacaoRepository.findByContaId(contaId);
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar transações de um cliente")
    public ResponseEntity<List<Transacao>> listarTransacoesPorCliente(@PathVariable Long clienteId) {
        List<Transacao> transacoes = transacaoRepository.findByContaClienteId(clienteId.intValue());
        return ResponseEntity.ok(transacoes);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir transação")
    public ResponseEntity<Void> excluirTransacao(@PathVariable Long id) {
        if (transacaoRepository.existsById(id)) {
            transacaoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
