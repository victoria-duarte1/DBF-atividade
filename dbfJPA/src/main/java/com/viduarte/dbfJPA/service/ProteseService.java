package com.viduarte.dbfJPA.service;

import com.viduarte.dbfJPA.domain.Protese;
import com.viduarte.dbfJPA.repository.ProteseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProteseService {

    private final Logger log = LoggerFactory.getLogger(ProteseService.class);

    private final ProteseRepository proteseRepository;

    public ProteseService(ProteseRepository proteseRepository) {
        this.proteseRepository = proteseRepository;
    }

    public List<Protese> findAllList(){
        log.debug("Request to get All Protese");
        return proteseRepository.findAll();
    }

    public Optional<Protese> findOne(Long id) {
        log.debug("Request to get Protese : {}", id);
        return proteseRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Protese : {}", id);
        proteseRepository.deleteById(id);
    }

    public Protese save(Protese protese) {
        log.debug("Request to save Protese : {}", protese);
        protese = proteseRepository.save(protese);
        return protese;
    }

    public List<Protese> saveAll(List<Protese> proteses) {
        log.debug("Request to save Protese : {}", proteses);
        proteses = proteseRepository.saveAll(proteses);
        return proteses;
    }
}
