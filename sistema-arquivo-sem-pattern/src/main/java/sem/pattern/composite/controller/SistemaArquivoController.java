package sem.pattern.composite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sem.pattern.composite.dto.ComponenteDTO;
import sem.pattern.composite.dto.ComponenteMapper;
import sem.pattern.composite.service.SistemaArquivoService;


@RequestMapping("/ApiSF")
@RestController
public class SistemaArquivoController {

    @Autowired
    private SistemaArquivoService service;

    @GetMapping("/raiz")
    public Map<String, Object> getRaiz() {
        return service.getRaizDTO();
    }

    @PostMapping("/adicionar")
    public Map<String, Object> adicionarComponente(
            @RequestParam String caminhoDestino,
            @RequestBody ComponenteDTO componenteDTO) {
        try {
            Object componenteModelo = ComponenteMapper.dtoParaModelo(componenteDTO);
            service.adicionarComponente(caminhoDestino, componenteModelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.getRaizDTO();
    }

    @PostMapping("/renomear")
    public Map<String, Object> renomearComponente(@RequestParam String caminho, @RequestParam String novoNome){

        service.renomearComponente(caminho, novoNome);

        return service.getRaizDTO();
    }

    @PostMapping("/moverPara")
    public Map<String, Object> moverComponente(@RequestParam String origem, @RequestParam String destino){

        service.moverComponente(origem, destino);

        return service.getRaizDTO();
    }

    @DeleteMapping("/delete")
    public Map<String, Object> deletarComponente(@RequestParam String caminho){

        service.deletarComponente(caminho);

        return service.getRaizDTO();
    }
}
