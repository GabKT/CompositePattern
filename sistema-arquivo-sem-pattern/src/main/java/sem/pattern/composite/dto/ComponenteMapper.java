package sem.pattern.composite.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sem.pattern.composite.model.Arquivo;
import sem.pattern.composite.model.Pasta;

public class ComponenteMapper {

    public static Map<String, Object> pastaParaDTO(Pasta pasta) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("tipo", "pasta");
        dto.put("id", pasta.getId());
        dto.put("nome", pasta.getNome());
        dto.put("tamanho", pasta.getTamanho());

        List<Map<String, Object>> componentesDTO = new ArrayList<>();

        for (Object item : pasta.getComponentes()) {
            if (item instanceof Arquivo arquivo) {
                Map<String, Object> arquivoDTO = new HashMap<>();
                arquivoDTO.put("tipo", "arquivo");
                arquivoDTO.put("id", arquivo.getId());
                arquivoDTO.put("nome", arquivo.getNome());
                arquivoDTO.put("tamanho", arquivo.getTamanho());
                componentesDTO.add(arquivoDTO);
            } else if (item instanceof Pasta subpasta) {
                componentesDTO.add(pastaParaDTO(subpasta));
            } else {
            }
        }

        dto.put("componentes", componentesDTO);
        return dto;
    }

    public static Object dtoParaModelo(ComponenteDTO dto) {
        if ("pasta".equalsIgnoreCase(dto.getTipo())) {
            Pasta pasta = new Pasta(dto.getNome());
            if (dto.getComponentes() != null) {
                for (ComponenteDTO filho : dto.getComponentes()) {
                    Object modeloFilho = dtoParaModelo(filho);
                    pasta.adicionarComponente(modeloFilho);
                    if (modeloFilho instanceof Arquivo a) a.definirPai(pasta);
                    if (modeloFilho instanceof Pasta p) p.definirPai(pasta);
                }
            }
            return pasta;
        } else if ("arquivo".equalsIgnoreCase(dto.getTipo())) {
            Arquivo arquivo = new Arquivo(dto.getNome(), dto.getTamanho());
            return arquivo;
        }
        throw new IllegalArgumentException("Tipo desconhecido: " + dto.getTipo());
    }
}