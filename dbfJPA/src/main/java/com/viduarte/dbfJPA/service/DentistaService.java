package com.viduarte.dbfJPA.service;

import com.viduarte.dbfJPA.domain.Dentista;
import com.viduarte.dbfJPA.repository.DentistaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistaService {

    private final Logger log = LoggerFactory.getLogger(DentistaService.class);

    private final DentistaRepository dentistaRepository;

    public DentistaService(DentistaRepository dentistaRepository) {
        this.dentistaRepository = dentistaRepository;
    }

    public List<Dentista> findAllList(){
        log.debug("Request to get All Dentista");
        return dentistaRepository.findAll();
    }

    public Optional<Dentista> findOne(Long id) {
        log.debug("Request to get Dentista : {}", id);
        return dentistaRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Dentista : {}", id);
        dentistaRepository.deleteById(id);
    }

    public Dentista save(Dentista dentista) {
        log.debug("Request to save Dentista : {}", dentista);
        dentista = dentistaRepository.save(dentista);
        return dentista;
    }

    public List<Dentista> saveAll(List<Dentista> dentistas) {
        log.debug("Request to save Dentista : {}", dentistas);
        dentistas = dentistaRepository.saveAll(dentistas);
        return dentistas;
    }
}
