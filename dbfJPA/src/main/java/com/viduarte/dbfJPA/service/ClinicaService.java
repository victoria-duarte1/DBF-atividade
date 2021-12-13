package com.viduarte.dbfJPA.service;

import com.viduarte.dbfJPA.domain.Clinica;
import com.viduarte.dbfJPA.repository.ClinicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicaService {
    private final Logger log = LoggerFactory.getLogger(ClinicaService.class);

    private final ClinicaRepository clinicaRepository;

    public ClinicaService(ClinicaRepository clinicaRepository) {
        this.clinicaRepository = clinicaRepository;
    }

    public List<Clinica> findAllList(){
        log.debug("Request to get All Clinica");
        return clinicaRepository.findAll();
    }

    public Optional<Clinica> findOne(Long id) {
        log.debug("Request to get Clinica : {}", id);
        return clinicaRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Clinica : {}", id);
        clinicaRepository.deleteById(id);
    }

    public Clinica save(Clinica clinica) {
        log.debug("Request to save Clinica : {}", clinica);
        clinica = clinicaRepository.save(clinica);
        return clinica;
    }

    public List<Clinica> saveAll(List<Clinica> clinicas) {
        log.debug("Request to save Clinica : {}", clinicas);
        clinicas = clinicaRepository.saveAll(clinicas);
        return clinicas;
    }
}
