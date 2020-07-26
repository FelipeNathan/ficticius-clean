package br.com.ficticiusclean.rest;

import javax.inject.Inject;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ficticiusclean.dto.DTOBase;

@TestComponent
public class RestUtils {

	@Inject
	private TestRestTemplate testRestTemplate;

	private int port;

	public HttpHeaders getHttpHeaders() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}

	public String dtoToJson(DTOBase dto) throws JsonProcessingException {

		return new ObjectMapper().writeValueAsString(dto);
	}

	public HttpEntity<String> getHttpEntity(DTOBase dto) throws JsonProcessingException {

		return new HttpEntity<String>(dtoToJson(dto), getHttpHeaders());
	}

	public ResponseEntity<String> post(String uri, DTOBase dto) throws JsonProcessingException {

		return testRestTemplate.postForEntity("http://localhost:" + port + "/" + uri, getHttpEntity(dto), String.class);
	}

	public <T> T convert(String content, Class<T> clazz) throws JsonProcessingException {

		return new ObjectMapper().readValue(content, clazz);
	}

	public int getPort() {

		return port;
	}

	public void setPort(int port) {

		this.port = port;
	}
}
