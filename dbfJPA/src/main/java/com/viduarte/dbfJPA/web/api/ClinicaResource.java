package com.viduarte.dbfJPA.web.api;

import com.viduarte.dbfJPA.domain.Clinica;
import com.viduarte.dbfJPA.service.ClinicaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clinicas")
public class ClinicaResource {
    
    private final Logger log = LoggerFactory.getLogger(ClinicaResource.class);

    private final ClinicaService clinicaService;

    public ClinicaResource(ClinicaService clinicaService) {
        this.clinicaService = clinicaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clinica> mostrarClinicaPorId(@PathVariable Long id) {
        log.debug("REST request to get Clinica : {}", id);
        Optional<Clinica> clinica = clinicaService.findOne(id);
        if(clinica.isPresent()) {
            return ResponseEntity.ok().body(clinica.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Clinica>> listarClinicas(){
        List<Clinica> lista = clinicaService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Clinica> AtualizarClinica(@RequestBody Clinica clinica) throws URISyntaxException {
        log.debug("REST request to update Clinica : {}", clinica);
        if (clinica.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Clinica id null");
        }
        Clinica result = clinicaService.save(clinica);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/")
    public ResponseEntity<Clinica> AdicionarClinica(@RequestBody Clinica clinica) throws URISyntaxException {
        log.debug("REST request to save Clinica : {}", clinica);
        if (clinica.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Uma nova clinica não pode ter um ID");
        }
        Clinica result = clinicaService.save(clinica);
        return ResponseEntity.created(new URI("/api/clinicas/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Clinica> baixarListaDeClinicas(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Clinica> savedNotes = new ArrayList<>();
        List<Clinica> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Clinica::parseNote).collect(Collectors.toList());
        clinicaService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> ExcluirClinica(@PathVariable Long id) {
        log.debug("REST request to delete Clinica : {}", id);

        clinicaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
