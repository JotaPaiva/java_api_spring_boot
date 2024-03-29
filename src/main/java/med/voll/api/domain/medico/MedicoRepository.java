package med.voll.api.domain.medico;

import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico,Long> {

    // Utilizando padrão de nomenclatura do Spring. Query gerada automaticamente.
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("SELECT m FROM Medico m WHERE m.ativo = TRUE AND m.especialidade = :especialidade " +
            "AND m.id NOT IN (SELECT c.medico.id FROM Consulta c WHERE c.data = :data AND c.motivoCancelamento IS NULL) " +
            "ORDER BY rand() LIMIT 1")
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    @Query("SELECT m.ativo FROM Medico m WHERE m.id = :idMedico")
    Boolean findAtivoById(Long idMedico);

}