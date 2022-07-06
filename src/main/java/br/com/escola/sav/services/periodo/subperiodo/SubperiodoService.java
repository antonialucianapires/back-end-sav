package br.com.escola.sav.services.periodo.subperiodo;

import br.com.escola.sav.dto.response.periodo.subperiodo.SubperiodoResponseDTO;
import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.repositories.periodo.PeriodoRepository;
import br.com.escola.sav.repositories.periodo.subperiodo.SubperiodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SubperiodoService implements ISubperiodoService{

    private final SubperiodoRepository subperiodoRepository;
    private final PeriodoRepository periodoRepository;

    @Autowired
    public SubperiodoService(SubperiodoRepository subperiodoRepository, PeriodoRepository periodoRepository) {
        this.subperiodoRepository = subperiodoRepository;
        this.periodoRepository = periodoRepository;
    }

    @Override
    public void criarSubperiodo(String nomeSubperiodo, int codigoPeriodo, Date dataInicio, Date dataFim) {

        Periodo periodo = periodoRepository.findById(codigoPeriodo).orElseThrow(() -> new ObjectNotFound("Período não encontrado"));

        SubPeriodo novoSubperiodo = new SubPeriodo(nomeSubperiodo,periodo, dataInicio, dataFim);

        subperiodoRepository.save(novoSubperiodo);

    }

    @Override
    public SubperiodoResponseDTO atualizarSubperiodo(int idSubperiodo, String nomeSubperiodo, int codigoPeriodo, Date dataInicio, Date dataFim) {

        Periodo periodo = periodoRepository.findById(codigoPeriodo).orElseThrow(() -> new ObjectNotFound("Período não encontrado"));

        SubPeriodo subPeriodo = subperiodoRepository.findById(idSubperiodo).orElseThrow(() -> new ObjectNotFound("Subperíodo não encontrado"));

        subPeriodo.setNome(nomeSubperiodo);
        subPeriodo.setPeriodo(periodo);
        subPeriodo.setDataInicio(dataInicio);
        subPeriodo.setDataFim(dataFim);

        subperiodoRepository.save(subPeriodo);

        return new SubperiodoResponseDTO(subPeriodo);
    }
}
