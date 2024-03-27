package br.com.toestudando.cadastroapp;

import br.com.toestudando.cadastroapp.response.ViaCEPResponse;
import br.com.toestudando.cadastroapp.service.ViaCEPService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ViaCEPServiceTest {

	private ViaCEPService viaCEPService;

	@Mock
	private RestTemplate restTemplate;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		viaCEPService = new ViaCEPService(new RestTemplateBuilder());
	}

	@Test
	@DisplayName("Deve retornar status 200 quando o CEP for válido")
	void consultarCEPValido() {
		String cep = "12345678";
		ViaCEPResponse viaCEPResponse = new ViaCEPResponse();
		viaCEPResponse.setCep("08225260");
		viaCEPResponse.setLogradouro("Rua Teste");
		viaCEPResponse.setBairro("Bairro Teste");
		viaCEPResponse.setLocalidade("Cidade Teste");
		viaCEPResponse.setUf("TS");

		// Defina o comportamento esperado para a chamada ao método consultarCEP
		when(restTemplate.getForObject(eq("https://viacep.com.br/ws/12345678/json"), any()))
				.thenReturn(viaCEPResponse);

		// Teste se a resposta contém as informações esperadas
		ResponseEntity<String> responseEntity = viaCEPService.consultarCEP(cep);

		// Verifique se o status da resposta é o esperado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Deve lançar exceção quando o campo estiver com um formato de CEP inválido")
	void consultarCEPFormatoInvalido() {
		// Arrange
		String cep = "1234567"; // CEP com formato inválido (menos de 8 dígitos)
		String mensagemEsperada = "Formato do CEP inválido.";

		// Act & Assert
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			viaCEPService.consultarCEP(cep);
		});

		assertEquals(mensagemEsperada, exception.getMessage());
	}

	@Test
	@DisplayName("Deve lançar exceção quando o campo estiver vázio")
	void consultarCEPCampoNull() {

		String cep = "";
		String mensagemEsperada = "Campo CEP é obrigatório.";

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			viaCEPService.consultarCEP(cep);
		});


		assertEquals(mensagemEsperada, exception.getMessage());
	}
}