package com.viduarte.dbfJPA.web.api;

import com.viduarte.dbfJPA.domain.Dentista;
import com.viduarte.dbfJPA.service.DentistaService;
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
@RequestMapping("/dentistas")
public class DentistaResource {
    private final Logger log = LoggerFactory.getLogger(DentistaResource.class);

    private final DentistaService dentistaService;

    public DentistaResource(DentistaService dentistaService) {
        this.dentistaService = dentistaService;
    }

    /**
     * {@code GET  /pessoas/:id} : get the "id" dentista.
     *
     * @param id o id do pessoa que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o dentista, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Dentista> getDentista(@PathVariable Long id) {
        log.debug("REST request to get Dentista : {}", id);
        Optional<Dentista> dentista = dentistaService.findOne(id);
        if(dentista.isPresent()) {
            return ResponseEntity.ok().body(dentista.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Dentista>> getDentistas(){
        List<Dentista> lista = dentistaService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /dentistas} : Atualiza um pessoa existenteUpdate.
     *
     * @param dentista o dentista a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o pessoa atualizado,
     * ou com status {@code 400 (Bad Request)} se o pessoa não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o pessoa não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Dentista> updateDentista(@RequestBody Dentista dentista) throws URISyntaxException {
        log.debug("REST request to update Dentista : {}", dentista);
        if (dentista.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Dentista id null");
        }
        Dentista result = dentistaService.save(dentista);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new dentista.
     *
     * @param dentista the pessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dentista, or with status {@code 400 (Bad Request)} if the pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Dentista> createDentista(@RequestBody Dentista dentista) throws URISyntaxException {
        log.debug("REST request to save Dentista : {}", dentista);
        if (dentista.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo dentista não pode ter um ID");
        }
        Dentista result = dentistaService.save(dentista);
        return ResponseEntity.created(new URI("/api/dentistas/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Dentista> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Dentista> savedNotes = new ArrayList<>();
        List<Dentista> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Dentista::parseNote).collect(Collectors.toList());
        dentistaService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" dentista.
     *
     * @param id o id do dentistas que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDentista(@PathVariable Long id) {
        log.debug("REST request to delete Dentista : {}", id);

        dentistaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
