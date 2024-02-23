package br.com.toestudando.cadastroapp.controller;

import br.com.toestudando.cadastroapp.response.ViaCEPResponse;
import br.com.toestudando.cadastroapp.service.ViaCEPService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CEPController {

    private final ViaCEPService viaCEPService;

    public CEPController(ViaCEPService viaCEPService) {
        this.viaCEPService = viaCEPService;
    }

    @PostMapping("/consultaCEP")
    public ViaCEPResponse consultaCEP(@RequestBody @Valid ViaCEPResponse request) {
        return viaCEPService.consultarCEP(request.getCep());
    }
}