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

@RestController
@RequestMapping("/periodos/subperiodos")
public class SubperiodoController {

    private final ISubperiodoService subperiodoService;

    @Autowired
    public SubperiodoController(ISubperiodoService subperiodoService) {
        this.subperiodoService = subperiodoService;
    }

    @PostMapping
    public ResponseEntity<ResultView<Void>> criarSubperiodo(@RequestBody @Valid SubperiodoRequestDTO subperiodoRequestDTO) {
        subperiodoService.criarSubperiodo(subperiodoRequestDTO.getNomeSubperiodo(),subperiodoRequestDTO.getCodigoPeriodo(),subperiodoRequestDTO.getDataInicio(), subperiodoRequestDTO.getDataFim());

        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Subperíodo criado com sucesso!")
                .build();

        return new ResponseEntity<>(resultView,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultView<SubperiodoResponseDTO>> atualizarSubperiodo(@PathVariable(name = "id") int idSubperiodo, @RequestBody @Valid SubperiodoRequestDTO subperiodoRequestDTO) {
        SubperiodoResponseDTO subperiodoResponseDTO = subperiodoService.atualizarSubperiodo(idSubperiodo, subperiodoRequestDTO.getNomeSubperiodo(), subperiodoRequestDTO.getCodigoPeriodo(), subperiodoRequestDTO.getDataInicio(), subperiodoRequestDTO.getDataFim());

        ResultView<SubperiodoResponseDTO> resultView = ResultView.<SubperiodoResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Subperíodo atualizado com sucesso!")
                .payload(subperiodoResponseDTO)
                .build();

        return new ResponseEntity<>(resultView,HttpStatus.CREATED);
    }

}
