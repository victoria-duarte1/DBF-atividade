package com.viduarte.dbfJPA.web.api;

import com.viduarte.dbfJPA.domain.Protese;
import com.viduarte.dbfJPA.service.ProteseService;
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
@RequestMapping("/proteses")
public class ProteseResource {
    private final Logger log = LoggerFactory.getLogger(ProteseResource.class);

    private final ProteseService proteseService;

    public ProteseResource(ProteseService proteseService) {
        this.proteseService = proteseService;
    }

    /**
     * {@code GET  /pessoas/:id} : get the "id" protese.
     *
     * @param id o id do protese que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o protese, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Protese> getProtese(@PathVariable Long id) {
        log.debug("REST request to get Protese : {}", id);
        Optional<Protese> protese = proteseService.findOne(id);
        if(protese.isPresent()) {
            return ResponseEntity.ok().body(protese.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Protese>> getProteses(){
        List<Protese> lista = proteseService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /proteses} : Atualiza uma protese existenteUpdate.
     *
     * @param protese o protese a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo a protese atualizado,
     * ou com status {@code 400 (Bad Request)} se o pessoa não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o pessoa não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Protese> updateDentista(@RequestBody Protese protese) throws URISyntaxException {
        log.debug("REST request to update Dentista : {}", protese);
        if (protese.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Protese id null");
        }
        Protese result = proteseService.save(protese);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new protese.
     *
     * @param protese the protese to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoa, or with status {@code 400 (Bad Request)} if the pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Protese> createProtese(@RequestBody Protese protese) throws URISyntaxException {
        log.debug("REST request to save Protese : {}", protese);
        if (protese.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Uma nova protese não pode ter um ID");
        }
        Protese result = proteseService.save(protese);
        return ResponseEntity.created(new URI("/api/proteses/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Protese> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Protese> savedNotes = new ArrayList<>();
        List<Protese> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Protese::parseNote).collect(Collectors.toList());
        proteseService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" protese.
     *
     * @param id o id do proteses que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProtese(@PathVariable Long id) {
        log.debug("REST request to delete Dentista : {}", id);

        proteseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
