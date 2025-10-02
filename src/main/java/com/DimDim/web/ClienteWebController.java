package com.DimDim.web;

import com.DimDim.model.Cliente;
import com.DimDim.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ui/clientes")
public class ClienteWebController {

    private final ClienteRepository clienteRepository;

    public ClienteWebController(ClienteRepository clienteRepository){this.clienteRepository=clienteRepository;}

    @GetMapping
    public String listar(Model model){ model.addAttribute("clientes", clienteRepository.findAll()); return "clientes/list"; }

    @GetMapping("/novo")
    public String novo(Model model){ model.addAttribute("cliente", new Cliente()); return "clientes/form"; }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Cliente cliente, BindingResult br){ 
        if(br.hasErrors()) return "clientes/form"; 
        clienteRepository.save(cliente); 
        return "redirect:/ui/clientes"; 
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){ clienteRepository.findById(id).ifPresent(c->model.addAttribute("cliente", c)); return "clientes/form"; }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id){ clienteRepository.deleteById(id); return "redirect:/ui/clientes"; }
}
