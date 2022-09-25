package br.com.escola.sav.services.periodo.tipo;

import br.com.escola.sav.dto.response.periodo.tipo.TipoPeriodoResponseDTO;
import br.com.escola.sav.entities.periodo.tipo.TipoPeriodo;
import br.com.escola.sav.repositories.periodo.tipo.TipoPeriodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoPeriodoService implements ITipoPeriodoService{

    private final TipoPeriodoRepository tipoPeriodoRepository;

    @Autowired
    public TipoPeriodoService(TipoPeriodoRepository tipoPeriodoRepository) {
        this.tipoPeriodoRepository = tipoPeriodoRepository;
    }

    @Override
    public void criarTipoPeriodo(String nome) {
        TipoPeriodo tipoPeriodo = new TipoPeriodo(nome.toUpperCase());
        tipoPeriodoRepository.save(tipoPeriodo);
    }

    @Override
    public List<TipoPeriodoResponseDTO> listarTipoPeriodos() {
        return tipoPeriodoRepository.findAll().stream().map(TipoPeriodoResponseDTO::new).collect(Collectors.toList());
    }
}
