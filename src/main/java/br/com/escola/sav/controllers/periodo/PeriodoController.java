package br.com.escola.sav.controllers.periodo;

import br.com.escola.sav.dto.request.periodo.PeriodoRequestDTO;
import br.com.escola.sav.dto.request.periodo.ResultView;
import br.com.escola.sav.services.periodo.IPeriodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/periodos")
public class PeriodoController {

    private final IPeriodoService periodoService;

    @Autowired
    public PeriodoController(IPeriodoService periodoService) {
        this.periodoService = periodoService;
    }

    @PostMapping
    public ResponseEntity<ResultView<Void>> criarPeriodo(@RequestBody PeriodoRequestDTO periodoRequestDTO) {

        periodoService.criarPeriodo(periodoRequestDTO.getNomePeriodo(), periodoRequestDTO.getDataInicio(), periodoRequestDTO.getDataFim(),periodoRequestDTO.getTipoPeriodo());

        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Período criado com sucesso!")
                .build();

        return new ResponseEntity<>(resultView, HttpStatus.CREATED);

    }
}
