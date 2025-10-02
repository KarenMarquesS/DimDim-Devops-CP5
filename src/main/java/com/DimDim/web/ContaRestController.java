package com.DimDim.web;

import com.DimDim.model.Conta;
import com.DimDim.model.Cliente;
import com.DimDim.repository.ContaRepository;
import com.DimDim.repository.ClienteRepository;
import com.DimDim.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contas")
@Tag(name = "Contas", description = "API para gerenciamento de contas")
public class ContaRestController {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaService contaService;

    @GetMapping
    @Operation(summary = "Listar todas as contas")
    public ResponseEntity<List<Conta>> listarContas() {
        List<Conta> contas = contaRepository.findAll();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta por ID")
    public ResponseEntity<Conta> buscarConta(@PathVariable Long id) {
        Optional<Conta> conta = contaRepository.findById(id);
        return conta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{numero}")
    @Operation(summary = "Buscar conta por número")
    public ResponseEntity<Conta> buscarContaPorNumero(@PathVariable String numero) {
        Optional<Conta> conta = contaRepository.findByNumero(numero);
        return conta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar nova conta")
    public ResponseEntity<Conta> criarConta(@Valid @RequestBody Conta conta) {
        try {
            // Verificar se o cliente existe
            if (conta.getCliente() != null && conta.getCliente().getId() != null) {
                Optional<Cliente> cliente = clienteRepository.findById(conta.getCliente().getId());
                if (cliente.isEmpty()) {
                    return ResponseEntity.badRequest().build();
                }
                conta.setCliente(cliente.get());
            }
            
            Conta contaSalva = contaRepository.save(conta);
            return ResponseEntity.status(HttpStatus.CREATED).body(contaSalva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conta")
    public ResponseEntity<Conta> atualizarConta(@PathVariable Long id, @Valid @RequestBody Conta contaAtualizada) {
        Optional<Conta> contaExistente = contaRepository.findById(id);
        if (contaExistente.isPresent()) {
            contaAtualizada.setId(id);
            Conta contaSalva = contaRepository.save(contaAtualizada);
            return ResponseEntity.ok(contaSalva);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir conta")
    public ResponseEntity<Void> excluirConta(@PathVariable Long id) {
        if (contaRepository.existsById(id)) {
            contaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/deposito")
    @Operation(summary = "Realizar depósito")
    public ResponseEntity<Conta> depositar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        try {
            Conta conta = contaService.depositar(id, valor);
            return ResponseEntity.ok(conta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/saque")
    @Operation(summary = "Realizar saque")
    public ResponseEntity<Conta> sacar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        try {
            Conta conta = contaService.sacar(id, valor);
            return ResponseEntity.ok(conta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar contas de um cliente")
    public ResponseEntity<List<Conta>> listarContasPorCliente(@PathVariable Long clienteId) {
        List<Conta> contas = contaRepository.findByClienteId(clienteId.intValue());
        return ResponseEntity.ok(contas);
    }
}
