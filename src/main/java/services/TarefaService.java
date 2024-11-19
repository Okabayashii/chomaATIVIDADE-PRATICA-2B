package services;


import model.Prioridade;
import model.Status;
import model.Tarefa;
import repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository taskRepository;

    public Tarefa criarTask(Tarefa task) {
        task.setStatus(Status.A_FAZER);
        return taskRepository.save(task);
    }

    public List<Tarefa> listarTasks() {
        return taskRepository.findAllByOrderByStatusAscPrioridadeDesc();
    }

    public Tarefa moverTask(Long id) {
    	Tarefa task = obterTaskPorId(id);
        switch (task.getStatus()) {
            case A_FAZER:
                task.setStatus(Status.EM_PROGRESSO);
                break;
            case EM_PROGRESSO:
                task.setStatus(Status.CONCLUIDO);
                break;
            default:
                throw new IllegalStateException("A tarefa já está concluída.");
        }
        return taskRepository.save(task);
    }

    public Tarefa atualizarTask(Long id, Tarefa detalhesTask) {
    	Tarefa task = obterTaskPorId(id);
        task.setTitulo(detalhesTask.getTitulo());
        task.setDescricao(detalhesTask.getDescricao());
        task.setPrioridade(detalhesTask.getPrioridade());
        task.setDataLimite(detalhesTask.getDataLimite());
        return taskRepository.save(task);
    }

    public void deletarTask(Long id) {
    	Tarefa task = obterTaskPorId(id);
        taskRepository.delete(task);
    }

    private Tarefa obterTaskPorId(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));
    }

    // Métodos adicionais para filtragem e relatório

    public List<Tarefa> filtrarPorPrioridade(Prioridade prioridade) {
        return taskRepository.findByPrioridade(prioridade);
    }

    public List<Tarefa> filtrarPorDataLimite(LocalDate dataLimite) {
        return taskRepository.findByDataLimiteBefore(dataLimite);
    }

    public List<Tarefa> gerarRelatorio() {
        LocalDate hoje = LocalDate.now();
        return taskRepository.findAll().stream()
                .filter(task -> task.getDataLimite() != null
                        && task.getDataLimite().isBefore(hoje)
                        && task.getStatus() != Status.CONCLUIDO)
                .collect(Collectors.toList());
    }
}