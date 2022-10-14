package br.com.escola.sav.controllers.periodo.tipo;

import br.com.escola.sav.dto.request.compartilhado.ResultView;
import br.com.escola.sav.dto.request.periodo.tipo.TipoPeriodoRequestDTO;
import br.com.escola.sav.dto.response.periodo.tipo.TipoPeriodoResponseDTO;
import br.com.escola.sav.services.periodo.tipo.ITipoPeriodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/periodos/tipos")
public class TipoPeriodoController {

    private final ITipoPeriodoService tipoPeriodoService;

    @Autowired
    public TipoPeriodoController(ITipoPeriodoService tipoPeriodoService) {
        this.tipoPeriodoService = tipoPeriodoService;
    }

    @PostMapping
    public ResponseEntity<ResultView<Void>> criarTipoPeriodo(@RequestBody @Valid TipoPeriodoRequestDTO tipoPeriodoRequest) {
        tipoPeriodoService.criarTipoPeriodo(tipoPeriodoRequest.getNomeTipoPeriodo());

        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tipo de período criado com sucesso!")
                .build();

        return new ResponseEntity<>(resultView, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResultView<List<TipoPeriodoResponseDTO>>> recuperarTiposDePeriodo() {

        List<TipoPeriodoResponseDTO> tiposDePeriodo = tipoPeriodoService.listarTipoPeriodos();

        ResultView<List<TipoPeriodoResponseDTO>> resultView = ResultView.<List<TipoPeriodoResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .payload(tiposDePeriodo)
                .build();

        return new ResponseEntity<>(resultView, HttpStatus.OK);
    }


}
