package com.viduarte.dbfJPA.repository;

import com.viduarte.dbfJPA.domain.Protese;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProteseRepository extends JpaRepository<Protese, Long> {

}
