package com.viduarte.dbfJPA.repository;

import com.viduarte.dbfJPA.domain.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
    
}
