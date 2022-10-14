package br.com.escola.sav.controllers.periodo;

import br.com.escola.sav.dto.request.compartilhado.ResultView;
import br.com.escola.sav.dto.request.periodo.PeriodoRequestDTO;
import br.com.escola.sav.dto.response.periodo.PeriodoResponseDTO;
import br.com.escola.sav.services.periodo.IPeriodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/periodos")
public class PeriodoController {

    private final IPeriodoService periodoService;

    @Autowired
    public PeriodoController(IPeriodoService periodoService) {
        this.periodoService = periodoService;
    }

    @PostMapping
    public ResponseEntity<ResultView<Void>> criarPeriodo(@RequestBody @Valid PeriodoRequestDTO periodoRequestDTO) {

        periodoService.criarPeriodo(periodoRequestDTO.getNomePeriodo(), periodoRequestDTO.getDataInicio(), periodoRequestDTO.getDataFim(),periodoRequestDTO.getTipoPeriodo(), periodoRequestDTO.getSubperiodos());

        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Período criado com sucesso!")
                .build();

        return new ResponseEntity<>(resultView, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<ResultView<List<PeriodoResponseDTO>>> consultarPeriodos(@RequestParam (name = "com_subperiodos",required = false, defaultValue = "false") boolean comSubperiodos) {

        List<PeriodoResponseDTO> periodos = periodoService.consultarPeriodos(comSubperiodos);

        ResultView<List<PeriodoResponseDTO>> resultView = ResultView.<List<PeriodoResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .payload(periodos)
                .build();

        return new ResponseEntity<>(resultView, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultView<PeriodoResponseDTO>> atualizarPeriodo(@PathVariable(name = "id") int idPeriodo, @RequestBody PeriodoRequestDTO periodoRequestDTO) {

        PeriodoResponseDTO periodoAtualizado = periodoService.atualizarPeriodo(idPeriodo, periodoRequestDTO.getNomePeriodo(), periodoRequestDTO.getDataInicio(), periodoRequestDTO.getDataFim(),periodoRequestDTO.getTipoPeriodo(), periodoRequestDTO.getSubperiodos());

        ResultView<PeriodoResponseDTO> resultView = ResultView.<PeriodoResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Período atualizado com sucesso!")
                .payload(periodoAtualizado)
                .build();

        return new ResponseEntity<>(resultView,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultView<Void>> excluirPeriodo(@PathVariable(name = "id") int idPeriodo) {
        periodoService.excluirPeriodo(idPeriodo);

        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Período excluído com sucesso!")
                .build();

        return new ResponseEntity<>(resultView, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultView<PeriodoResponseDTO>> recuperarPeriodoPorId(@PathVariable(name = "id") int idPeriodo, @RequestParam (name = "com_subperiodos",required = false, defaultValue = "false") boolean comSubperiodos) {
        PeriodoResponseDTO periodoResponseDTO = periodoService.recuperarPeriodoPorId(idPeriodo, comSubperiodos);

        ResultView<PeriodoResponseDTO> resultView = ResultView.<PeriodoResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .payload(periodoResponseDTO)
                .build();

        return new ResponseEntity<>(resultView, HttpStatus.OK);
    }

}
