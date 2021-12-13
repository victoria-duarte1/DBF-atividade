package com.viduarte.dbfJPA.service;

import com.viduarte.dbfJPA.domain.Pedido;
import com.viduarte.dbfJPA.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final Logger log = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> findAllList(){
        log.debug("Request to get All Pedido");
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findOne(Long id) {
        log.debug("Request to get Pedido : {}", id);
        return pedidoRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Pedido : {}", id);
        pedidoRepository.deleteById(id);
    }

    public Pedido save(Pedido pedido) {
        log.debug("Request to save Pedido : {}", pedido);
        pedido = pedidoRepository.save(pedido);
        return pedido;
    }

    public List<Pedido> saveAll(List<Pedido> pedidos) {
        log.debug("Request to save Pedido : {}", pedidos);
        pedidos = pedidoRepository.saveAll(pedidos);
        return pedidos;
    }
}
