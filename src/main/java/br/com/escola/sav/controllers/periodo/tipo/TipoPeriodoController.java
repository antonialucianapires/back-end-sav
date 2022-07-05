package br.com.escola.sav.controllers.periodo.tipo;

import br.com.escola.sav.dto.request.periodo.ResultView;
import br.com.escola.sav.dto.request.periodo.tipo.TipoPeriodoRequestDTO;
import br.com.escola.sav.services.periodo.tipo.ITipoPeriodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/periodos/tipos")
public class TipoPeriodoController {

    private final ITipoPeriodoService tipoPeriodoService;

    @Autowired
    public TipoPeriodoController(ITipoPeriodoService tipoPeriodoService) {
        this.tipoPeriodoService = tipoPeriodoService;
    }

    @PostMapping
    public ResponseEntity<ResultView<Void>> criarTipoPeriodo(@RequestBody @Valid TipoPeriodoRequestDTO tipoPeriodoRequest) {
        tipoPeriodoService.criarTipoPeriodo(tipoPeriodoRequest.getNomePeriodo());

        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tipo de período criado com sucesso!")
                .build();

        return new ResponseEntity<>(resultView, HttpStatus.CREATED);
    }
}
