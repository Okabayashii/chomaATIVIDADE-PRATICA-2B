package controller;

import model.Prioridade;
import model.Tarefa;
import services.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Validated
public class TarefaController {

    @Autowired
    private TarefaService taskService;

    @PostMapping
    public ResponseEntity<Tarefa> criarTask(@Valid @RequestBody Tarefa task) {
    	Tarefa novaTask = taskService.criarTask(task);
        return ResponseEntity.status(201).body(novaTask);
    }

    @GetMapping
    public List<Tarefa> listarTasks() {
        return taskService.listarTasks();
    }

    @PutMapping("/{id}/move")
    public Tarefa moverTask(@PathVariable Long id) {
        return taskService.moverTask(id);
    }

    @PutMapping("/{id}")
    public Tarefa atualizarTask(@PathVariable Long id, @Valid @RequestBody Tarefa task) {
        return taskService.atualizarTask(id, task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTask(@PathVariable Long id) {
        taskService.deletarTask(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints para funcionalidades extras

    @GetMapping("/filtrar")
    public List<Tarefa> filtrarTasks(@RequestParam(required = false) Prioridade prioridade,
                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataLimite) {
        if (prioridade != null) {
            return taskService.filtrarPorPrioridade(prioridade);
        } else if (dataLimite != null) {
            return taskService.filtrarPorDataLimite(dataLimite);
        } else {
            return taskService.listarTasks();
        }
    }

    @GetMapping("/relatorio")
    public List<Tarefa> gerarRelatorio() {
        return taskService.gerarRelatorio();
    }
}