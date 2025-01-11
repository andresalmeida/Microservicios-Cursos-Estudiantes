package com.espe.micro_cursos.controllers;

import com.espe.micro_cursos.model.entity.Curso;
import java.util.List;
import java.util.Optional;

import com.espe.micro_cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Curso curso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));
    }

    @GetMapping
    public List<Curso> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarporId(@PathVariable Long id) {
        Optional<Curso> cursoOptional = service.findById(id);
        if (cursoOptional.isPresent()) {
            return ResponseEntity.ok().body(cursoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Curso curso) {
        Optional<Curso> cursoOptional = service.findById(id);
        if (cursoOptional.isPresent()) {
            Curso cursoDB = cursoOptional.get();
            cursoDB.setNombre(curso.getNombre());
            cursoDB.setDescripcion(curso.getDescripcion());
            cursoDB.setCreditos(curso.getCreditos());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar (@PathVariable Long id) {
        Optional<Curso> cursoOptional = service.findById(id);
        if (cursoOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}