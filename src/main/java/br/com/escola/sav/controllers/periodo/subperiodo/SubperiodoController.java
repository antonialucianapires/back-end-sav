package br.com.escola.sav.controllers.periodo.subperiodo;

import br.com.escola.sav.dto.request.periodo.ResultView;
import br.com.escola.sav.dto.request.periodo.subperiodo.SubperiodoRequestDTO;
import br.com.escola.sav.dto.response.periodo.subperiodo.SubperiodoResponseDTO;
import br.com.escola.sav.services.periodo.subperiodo.ISubperiodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/periodos")
public class SubperiodoController {

    private final ISubperiodoService subperiodoService;

    @Autowired
    public SubperiodoController(ISubperiodoService subperiodoService) {
        this.subperiodoService = subperiodoService;
    }

    @PostMapping("/subperiodos")
    public ResponseEntity<ResultView<Void>> criarSubperiodo(@RequestBody @Valid SubperiodoRequestDTO subperiodoRequestDTO) {
        subperiodoService.criarSubperiodo(subperiodoRequestDTO.getNomeSubperiodo(),subperiodoRequestDTO.getCodigoPeriodo(),subperiodoRequestDTO.getDataInicio(), subperiodoRequestDTO.getDataFim());

        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Subperíodo criado com sucesso!")
                .build();

        return new ResponseEntity<>(resultView,HttpStatus.CREATED);
    }

    @PutMapping("/subperiodos/{id}")
    public ResponseEntity<ResultView<SubperiodoResponseDTO>> atualizarSubperiodo(@PathVariable(name = "id") int idSubperiodo, @RequestBody @Valid SubperiodoRequestDTO subperiodoRequestDTO) {
        SubperiodoResponseDTO subperiodoResponseDTO = subperiodoService.atualizarSubperiodo(idSubperiodo, subperiodoRequestDTO.getNomeSubperiodo(), subperiodoRequestDTO.getCodigoPeriodo(), subperiodoRequestDTO.getDataInicio(), subperiodoRequestDTO.getDataFim());

        ResultView<SubperiodoResponseDTO> resultView = ResultView.<SubperiodoResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Subperíodo atualizado com sucesso!")
                .payload(subperiodoResponseDTO)
                .build();

        return new ResponseEntity<>(resultView,HttpStatus.CREATED);
    }

    @GetMapping("/{id_periodo}/subperiodos")
    public ResponseEntity<ResultView<List<SubperiodoResponseDTO>>> recuperarSubperiodosPorPeriodo(@PathVariable(name = "id_periodo") int idPeriodo) {
        List<SubperiodoResponseDTO> subperiodos = subperiodoService.listarSubperiodosPorPeriodo(idPeriodo);

        ResultView<List<SubperiodoResponseDTO>> resultView = ResultView.<List<SubperiodoResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .payload(subperiodos)
                .build();

        return new ResponseEntity<>(resultView,HttpStatus.OK);
    }

    @DeleteMapping("/subperiodos/{id}")
    public ResponseEntity<ResultView<Void>> excluirSubperiodo(@PathVariable(name = "id") int idSubperiodo) {
        subperiodoService.excluirSubperiodo(idSubperiodo);

        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Subperíodo excluído com sucesso")
                .build();

        return new ResponseEntity<>(resultView, HttpStatus.OK);
    }

}
