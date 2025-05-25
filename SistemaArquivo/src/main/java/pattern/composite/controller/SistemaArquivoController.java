package pattern.composite.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pattern.composite.model.Componente;
import pattern.composite.service.SistemaArquivoService;

@RestController
@RequestMapping("/ApiSF")
public class SistemaArquivoController {

    private SistemaArquivoService service = new SistemaArquivoService();

    @GetMapping("/raiz")
    public Componente obterRaiz(){
        return service.getRaiz();
    }

    @PostMapping("/adicionar")
    public Componente adicionarComponente(@RequestParam String caminhoDestino, @RequestBody Componente componente) {
        
        service.adicionarComponente(caminhoDestino, componente);

        return service.getRaiz();
    }

    @PostMapping("/renomear")
    public Componente renomearComponente(@RequestParam String caminho, @RequestParam String novoNome){

        service.renomearComponente(caminho, novoNome);

        return service.getRaiz();
    }

    @PostMapping("/moverPara")
    public Componente moverComponente(@RequestParam String origem, @RequestParam String destino){

        service.moverComponente(origem, destino);

        return service.getRaiz();
    }

    @DeleteMapping("/delete")
    public Componente deletarComponente(@RequestParam String caminho){

        service.deletarComponente(caminho);

        return service.getRaiz();
    }
}
