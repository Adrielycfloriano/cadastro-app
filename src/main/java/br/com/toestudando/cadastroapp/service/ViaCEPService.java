package br.com.toestudando.cadastroapp.service;

import br.com.toestudando.cadastroapp.response.ViaCEPResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViaCEPService {

    @Autowired
    private HttpServletResponse response;

    public ViaCEPResponse consultarCEP(String cep) {
        try {
            verificaCEP(cep);
            // Se houver CEP, continua com a consulta
            ViaCEPResponse viaCEPResponse = new ViaCEPResponse();
            viaCEPResponse.setCep(cep);
            return viaCEPResponse;
        } catch (IllegalArgumentException e) {
            // Captura a exceção de CEP inválido e retorna uma resposta adequada
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ViaCEPResponse(e.getMessage());
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