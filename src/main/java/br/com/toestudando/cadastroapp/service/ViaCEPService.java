package br.com.toestudando.cadastroapp.service;

import br.com.toestudando.cadastroapp.response.ViaCEPResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class ViaCEPService {

    public ViaCEPResponse consultarCEP(String cep) {
        ViaCEPResponse response = new ViaCEPResponse();
        response.setCep(cep);
        return response;
    }
}
