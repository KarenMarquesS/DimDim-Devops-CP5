package com.DimDim.web;

import com.DimDim.model.Transacao;
import com.DimDim.repository.TransacaoRepository;
import com.DimDim.repository.ContaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ui/transacoes")
public class TransacaoWebController {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;

    public TransacaoWebController(TransacaoRepository transacaoRepository, ContaRepository contaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("transacoes", transacaoRepository.findAll());
        return "transacoes/list";
    }

    @GetMapping("/conta/{contaId}")
    public String listarPorConta(@PathVariable Long contaId, Model model) {
        model.addAttribute("transacoes", transacaoRepository.findByContaId(contaId));
        model.addAttribute("conta", contaRepository.findById(contaId).orElse(null));
        return "transacoes/list";
    }
}
