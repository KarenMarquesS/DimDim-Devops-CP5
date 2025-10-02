package com.DimDim.repository;

import com.DimDim.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;


public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumero(String numero);
    List<Conta> findByClienteId(Integer clienteId);

}
