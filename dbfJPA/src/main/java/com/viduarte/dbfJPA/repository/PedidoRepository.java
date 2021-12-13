package com.viduarte.dbfJPA.repository;

import com.viduarte.dbfJPA.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}
