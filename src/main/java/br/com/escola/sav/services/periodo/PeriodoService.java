package br.com.escola.sav.services.periodo;

import br.com.escola.sav.dto.request.periodo.subperiodo.SubperiodoRequestDTO;
import br.com.escola.sav.dto.response.periodo.PeriodoResponseDTO;
import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import br.com.escola.sav.entities.periodo.tipo.TipoPeriodo;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.exception.SavException;
import br.com.escola.sav.repositories.periodo.PeriodoRepository;
import br.com.escola.sav.repositories.periodo.tipo.TipoPeriodoRepository;
import br.com.escola.sav.services.periodo.subperiodo.ISubperiodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeriodoService implements IPeriodoService{

    private final TipoPeriodoRepository tipoPeriodoRepository;
    private final PeriodoRepository periodoRepository;

    private final ISubperiodoService subperiodoService;

    @Autowired
    public PeriodoService(TipoPeriodoRepository tipoPeriodoRepository, PeriodoRepository periodoRepository, ISubperiodoService subperiodoService) {
        this.tipoPeriodoRepository = tipoPeriodoRepository;
        this.periodoRepository = periodoRepository;
        this.subperiodoService = subperiodoService;
    }

    @Override
    public void criarPeriodo(String nomePeriodo, Date dataInicio, Date dataFim, int tipoPeriodo, List<SubperiodoRequestDTO> subperiodos) {

        TipoPeriodo tipoPeriodoEntity = tipoPeriodoRepository.findById(tipoPeriodo).orElseThrow(() -> new ObjectNotFound("Não encontramos o tipo de período informado"));

        Periodo novoPeriodo = new Periodo(nomePeriodo,dataInicio,dataFim,tipoPeriodoEntity);

        var subperiodosEntities = new ArrayList<SubPeriodo>();
        if(!subperiodos.isEmpty()) {
            subperiodos.forEach(sub -> {
                subperiodosEntities.add(new SubPeriodo(sub.getNomeSubperiodo(), novoPeriodo, sub.getDataInicio(), sub.getDataFim()));
            });
        } else {
            throw new SavException("Preencha os subperídos para finalizar a criação deste período.");
        }

        periodoRepository.save(novoPeriodo);
        subperiodoService.criarSubperiodos(subperiodosEntities);


    }

    @Override
    public List<PeriodoResponseDTO> consultarPeriodos(boolean comSubperiodos) {
        return periodoRepository.findAll().stream().map(periodo -> new PeriodoResponseDTO(periodo, comSubperiodos)).collect(Collectors.toList());
    }

    @Override
    public PeriodoResponseDTO atualizarPeriodo(int idPeriodo, String nomePeriodo, Date dataInicio, Date dataFim, int tipoPeriodo, List<SubperiodoRequestDTO> subperiodos) {

        TipoPeriodo tipoPeriodoEntity = tipoPeriodoRepository.findById(tipoPeriodo).orElseThrow(() -> new ObjectNotFound("Não encontramos o tipo de período informado"));
        Periodo periodo = periodoRepository.findById(idPeriodo).orElseThrow(() -> new ObjectNotFound("O período solicitado não existe"));

        periodo.setNome(nomePeriodo);
        periodo.setDataInicio(dataInicio);
        periodo.setDataFim(dataFim);
        periodo.setTipoPeriodo(tipoPeriodoEntity);

        if(!subperiodos.isEmpty()) {
            var subperiodosAtualizados = subperiodos.stream().map(sub -> new SubPeriodo(sub.getIdSubperiodo(), sub.getNomeSubperiodo(), periodo, sub.getDataInicio(), sub.getDataFim()));

            subperiodoService.atualizarSubperiodos(subperiodosAtualizados.collect(Collectors.toList()));

            periodo.setSubperiodos(subperiodosAtualizados.collect(Collectors.toUnmodifiableSet()));
        }

        Periodo periodoAtualizado = periodoRepository.saveAndFlush(periodo);

        return new PeriodoResponseDTO(periodoAtualizado);


    }

    @Override
    @Transactional
    public void excluirPeriodo(int idPeriodo) {
        Periodo periodo = periodoRepository.findById(idPeriodo).orElseThrow(() -> new ObjectNotFound("O período informado não existe"));

        if(!periodo.getTurmas().isEmpty()) {
            throw new SavException("Este período possui turmas associadas e por esse motivo não pode ser excluído. Considere excluir a turma primeiro.");
        }

        if(!periodo.getSubperiodos().isEmpty()) {
            subperiodoService.excluirSubperiodosPorIdPeriodo(periodo.getId());
        }

        periodoRepository.delete(periodo);
    }

    @Override
    public PeriodoResponseDTO recuperarPeriodoPorId(int idPeriodo, boolean comSubperiodos) {
        Periodo periodo = periodoRepository.findById(idPeriodo).orElseThrow(() -> new ObjectNotFound("O período informado não existe"));
        return new PeriodoResponseDTO(periodo, comSubperiodos);
    }

    @Override
    public Periodo recuperarPeriodoPorId(int idPeriodo) {
        return periodoRepository.findById(idPeriodo).orElseThrow(() -> new ObjectNotFound("O período informado não existe"));
    }
}
