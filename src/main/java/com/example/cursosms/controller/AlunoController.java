package com.example.cursosms.controller;

import com.example.cursosms.model.requests.AlunoRequest;
import com.example.cursosms.model.resources.AlunoResource;
import com.example.cursosms.service.AlunoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/alunos")
@AllArgsConstructor
public class AlunoController {

    private AlunoService alunoService;
    private PagedResourcesAssembler<AlunoResource> pagedResourceAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<AlunoResource>> registrarAluno(
            @RequestBody @Valid AlunoRequest alunoRequest){

        AlunoResource alunoResource = alunoService.save(alunoRequest);
        return ResponseEntity.created(null).body(EntityModel.of(alunoResource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AlunoResource>> buscarAlunoPorId(@PathVariable UUID id){
        AlunoResource alunoResource = alunoService.findByUsuarioId(id);
        return ResponseEntity.ok().body(EntityModel.of(alunoResource));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<PagedModel<EntityModel<AlunoResource>>> buscarAlunosPorCursoId(@PathVariable Long cursoId, Pageable pageable){
        Page<AlunoResource> alunos = alunoService.findAlunosByCursoId(cursoId, pageable);

        PagedModel<EntityModel<AlunoResource>> pagedModel = pagedResourceAssembler
                .toModel(alunos, linkTo(methodOn(AlunoController.class)
                        .buscarAlunosPorCursoId(cursoId, pageable))
                        .withSelfRel());

        return ResponseEntity.ok().body(pagedModel);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AlunoResource>>> buscarTodosAlunoes(Pageable pageable){
        Page<AlunoResource> alunos = alunoService.findAll(pageable);

        PagedModel<EntityModel<AlunoResource>> pagedModel = pagedResourceAssembler
                .toModel(alunos, linkTo(methodOn(AlunoController.class)
                        .buscarTodosAlunoes(pageable))
                        .withSelfRel());

        return ResponseEntity.ok().body(pagedModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<AlunoResource>> deletarAluno(@PathVariable UUID id){
        AlunoResource alunoResource = alunoService.delete(id);
        return ResponseEntity.ok().body(EntityModel.of(alunoResource));
    }
}
