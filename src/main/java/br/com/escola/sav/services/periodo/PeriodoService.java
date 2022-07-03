package br.com.escola.sav.services.periodo;

import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.entities.periodo.tipo.TipoPeriodo;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.repositories.periodo.PeriodoRepository;
import br.com.escola.sav.repositories.periodo.tipo.TipoPeriodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PeriodoService implements IPeriodoService{

    private final TipoPeriodoRepository tipoPeriodoRepository;
    private final PeriodoRepository periodoRepository;

    @Autowired
    public PeriodoService(TipoPeriodoRepository tipoPeriodoRepository, PeriodoRepository periodoRepository) {
        this.tipoPeriodoRepository = tipoPeriodoRepository;
        this.periodoRepository = periodoRepository;
    }

    @Override
    public void criarPeriodo(String nomePeriodo, Date dataInicio, Date dataFim, int tipoPeriodo) {

        TipoPeriodo tipoPeriodoEntity = tipoPeriodoRepository.findById(tipoPeriodo).orElseThrow(() -> new ObjectNotFound("Não encontramos o tipo de período informado"));

        Periodo novoPeriodo = new Periodo(nomePeriodo,dataInicio,dataFim,tipoPeriodoEntity);

        periodoRepository.save(novoPeriodo);


    }
}
