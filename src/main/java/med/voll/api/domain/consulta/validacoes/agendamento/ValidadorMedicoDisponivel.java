package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoDisponivel implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {

        // Validando se o médico possui uma consulta no mesmo horário na tabela CONSULTAS
        var medicoPossuiConsultaNoMesmoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(),dados.data());

        if (medicoPossuiConsultaNoMesmoHorario) {
            throw new ValidacaoException("O médico selecionado já possui outra consulta agendada no horário selecionado.");
        }

    }

}
