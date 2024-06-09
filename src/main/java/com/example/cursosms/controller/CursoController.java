package com.example.cursosms.controller;

import com.example.cursosms.model.requests.CursoRequest;
import com.example.cursosms.model.resources.CursoResource;
import com.example.cursosms.service.ICursoService;
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
@RequestMapping("/cursos")
@AllArgsConstructor
public class CursoController {

    private ICursoService cursoService;
    private PagedResourcesAssembler<CursoResource> pagedResourceAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<CursoResource>> registrarCurso(
            @RequestBody @Valid CursoRequest cursoRequest){

        CursoResource cursoResource = cursoService.save(cursoRequest);
        return ResponseEntity.created(null).body(EntityModel.of(cursoResource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CursoResource>> buscarCursoPorId(@PathVariable Long id){
        CursoResource cursoResource = cursoService.findById(id);
        return ResponseEntity.ok().body(EntityModel.of(cursoResource));
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<PagedModel<EntityModel<CursoResource>>> buscarCursosPorProfessorId(@PathVariable UUID professorId, Pageable pageable){
        Page<CursoResource> cursos = cursoService.findByProfessorId(professorId, pageable);

        PagedModel<EntityModel<CursoResource>> pagedModel = pagedResourceAssembler
                .toModel(cursos, linkTo(methodOn(CursoController.class)
                        .buscarCursosPorProfessorId(professorId, pageable))
                        .withSelfRel());

        return ResponseEntity.ok().body(pagedModel);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<PagedModel<EntityModel<CursoResource>>> buscarCursosPorAlunoId(@PathVariable UUID alunoId, Pageable pageable){
        Page<CursoResource> cursos = cursoService.findByAlunoId(alunoId, pageable);

        PagedModel<EntityModel<CursoResource>> pagedModel = pagedResourceAssembler
                .toModel(cursos, linkTo(methodOn(CursoController.class)
                        .buscarCursosPorAlunoId(alunoId, pageable))
                        .withSelfRel());

        return ResponseEntity.ok().body(pagedModel);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CursoResource>>> buscarTodosCursos(Pageable pageable){
        Page<CursoResource> cursos = cursoService.findAll(pageable);

        PagedModel<EntityModel<CursoResource>> pagedModel = pagedResourceAssembler
                .toModel(cursos, linkTo(methodOn(CursoController.class)
                        .buscarTodosCursos(pageable))
                        .withSelfRel());

        return ResponseEntity.ok().body(pagedModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CursoResource>> atualizarCurso(@PathVariable Long id, @RequestBody @Valid CursoRequest cursoRequest){
        CursoResource cursoResource = cursoService.update(id, cursoRequest);
        return ResponseEntity.ok().body(EntityModel.of(cursoResource));
    }

    @PatchMapping("/{id}/add/{alunoId}")
    public ResponseEntity<EntityModel<CursoResource>> adicionarAluno(@PathVariable Long id, @PathVariable UUID alunoId){
        CursoResource cursoResource = cursoService.addAluno(id, alunoId);
        return ResponseEntity.ok().body(EntityModel.of(cursoResource));
    }

    @PatchMapping("/{id}/remove/{alunoId}")
    public ResponseEntity<EntityModel<CursoResource>> removerAluno(@PathVariable Long id, @PathVariable UUID alunoId){
        CursoResource cursoResource = cursoService.removeAluno(id, alunoId);
        return ResponseEntity.ok().body(EntityModel.of(cursoResource));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<CursoResource>> deletarCurso(@PathVariable Long id){
        CursoResource cursoResource = cursoService.delete(id);
        return ResponseEntity.ok().body(EntityModel.of(cursoResource));
    }
}
