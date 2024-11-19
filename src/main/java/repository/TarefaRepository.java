package repository;

import model.Prioridade;
import model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findAllByOrderByStatusAscPrioridadeDesc();

    List<Tarefa> findByPrioridade(Prioridade prioridade);

    List<Tarefa> findByDataLimiteBefore(LocalDate date);
}