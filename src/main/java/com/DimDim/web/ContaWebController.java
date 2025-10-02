package com.DimDim.web;

import com.DimDim.model.Conta;
import com.DimDim.model.Cliente;
import com.DimDim.repository.ContaRepository;
import com.DimDim.repository.ClienteRepository;
import com.DimDim.service.ContaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/ui/contas")
public class ContaWebController {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final ContaService contaService;

    public ContaWebController(ContaRepository contaRepository, ClienteRepository clienteRepository, ContaService contaService){
        this.contaRepository=contaRepository; this.clienteRepository=clienteRepository; this.contaService=contaService;
    }

    @GetMapping
    public String listar(Model model){ model.addAttribute("contas", contaRepository.findAll()); return "contas/list"; }

    @GetMapping("/novo")
    public String novo(Model model){ model.addAttribute("conta", new Conta()); model.addAttribute("clientes", clienteRepository.findAll()); return "contas/form"; }

    @PostMapping
    public String salvar(@ModelAttribute Conta conta){ contaRepository.save(conta); return "redirect:/ui/contas"; }

    @GetMapping("/deposito/{id}")
    public String depositoForm(@PathVariable Long id, Model model){ contaRepository.findById(id).ifPresent(c->model.addAttribute("conta", c)); return "contas/deposito"; }

    @PostMapping("/deposito/{id}")
    public String deposito(@PathVariable Long id, @RequestParam String valor){ contaService.depositar(id, new BigDecimal(valor)); return "redirect:/ui/contas"; }

    @GetMapping("/saque/{id}")
    public String saqueForm(@PathVariable Long id, Model model){ contaRepository.findById(id).ifPresent(c->model.addAttribute("conta", c)); return "contas/saque"; }

    @PostMapping("/saque/{id}")
    public String saque(@PathVariable Long id, @RequestParam String valor){ contaService.sacar(id, new BigDecimal(valor)); return "redirect:/ui/contas"; }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){ 
        contaRepository.findById(id).ifPresent(c->model.addAttribute("conta", c)); 
        model.addAttribute("clientes", clienteRepository.findAll()); 
        return "contas/form"; 
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id){ contaRepository.deleteById(id); return "redirect:/ui/contas"; }
}
