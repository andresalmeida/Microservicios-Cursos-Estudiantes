package com.espe.micro_estudiantes.controllers;

import com.espe.micro_estudiantes.model.entity.Estudiante;
import com.espe.micro_estudiantes.services.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService service;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Estudiante estudiante) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(estudiante));
    }

    @GetMapping
    public List<Estudiante> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Estudiante> estudianteOptional = service.findById(id);
        if (estudianteOptional.isPresent()) {
            return ResponseEntity.ok().body(estudianteOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        Optional<Estudiante> estudianteOptional = service.findById(id);
        if (estudianteOptional.isPresent()) {
            Estudiante estudianteDB = estudianteOptional.get();
            estudianteDB.setNombre(estudiante.getNombre());
            estudianteDB.setApellido(estudiante.getApellido());
            estudianteDB.setEmail(estudiante.getEmail());
            estudianteDB.setFechaNacimiento(estudiante.getFechaNacimiento());
            estudianteDB.setTelefono(estudiante.getTelefono());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(estudianteDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Estudiante> estudianteOptional = service.findById(id);
        if (estudianteOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}