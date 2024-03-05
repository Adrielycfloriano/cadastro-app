package br.com.toestudando.cadastroapp.controller;

import br.com.toestudando.cadastroapp.service.ViaCEPService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CEPController {

    private final ViaCEPService viaCEPService;

    public CEPController(ViaCEPService viaCEPService) {
        this.viaCEPService = viaCEPService;
    }

    @GetMapping("/consultaCEP/{cep}")
    public String consultaCEP(@PathVariable String cep) {
        return viaCEPService.consultarCEP(cep).getBody();
    }
}