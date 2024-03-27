package br.com.toestudando.cadastroapp.service;

import br.com.toestudando.cadastroapp.response.ViaCEPResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCEPService {

    private final RestTemplate restTemplate;

    public ViaCEPService(RestTemplateBuilder restTemplateBuilder) {

        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<String> consultarCEP(String cep) {
        verificaCEP(cep);

        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json";
            ViaCEPResponse viaCEPResponse = restTemplate.getForObject(url, ViaCEPResponse.class);

            String responseString = "CEP: " + viaCEPResponse.getCep() +
                    "\nLogradouro: " + viaCEPResponse.getLogradouro() +
                    "\nBairro: " + viaCEPResponse.getBairro() +
                    "\nLocalidade: " + viaCEPResponse.getLocalidade() +
                    "\nUF: " + viaCEPResponse.getUf();

            return ResponseEntity.ok(responseString);
        } catch (IllegalArgumentException e) {
            // Captura a exceção do CEP
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
        }
    }

    private void verificaCEP(String cep) {
        if (cep == null || cep.isEmpty()) {
            throw new IllegalArgumentException("Campo CEP é obrigatório.");
        } else if (!cep.matches("^\\d{8}$")) {
            throw new IllegalArgumentException("Formato do CEP inválido.");
        }
    }
}