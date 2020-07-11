package br.com.ficticiusclean.controller;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.ficticiusclean.FicticiuscleanApplication;
import br.com.ficticiusclean.dto.VehicleBrandDTO;
import br.com.ficticiusclean.dto.VehicleDTO;
import br.com.ficticiusclean.rest.RestUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = { FicticiuscleanApplication.class, RestUtils.class })
public class VehicleControllerTest {

	@LocalServerPort
	private int port;

	@Inject
	private RestUtils restUtils;

	@BeforeAll
	public void configure() {

		restUtils.setPort(port);
	}

	@Test
	public void shouldReturnBadRequestWithBrandRequiredAsReponseBody() throws JsonProcessingException {

		VehicleDTO dto = VehicleDTO.builder().build();
		ResponseEntity<String> response = restUtils.post("vehicle", dto);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertEquals("Brand is required", response.getBody());
	}

	@Test
	public void shouldReturnBadRequestWithModelRequiredAsReponseBody() throws JsonProcessingException {

		VehicleDTO dto = VehicleDTO.builder().brand("HONDA").build();
		ResponseEntity<String> response = restUtils.post("vehicle", dto);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertEquals("Model is required", response.getBody());
	}

	@Test
	public void shouldReturnBadRequestWithNameRequiredAsReponseBody() throws JsonProcessingException {

		VehicleDTO dto = VehicleDTO.builder().brand("HONDA").model("2010").build();
		ResponseEntity<String> response = restUtils.post("vehicle", dto);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertEquals("Name is required", response.getBody());
	}

	@Test
	public void shouldReturnBadRequestWithBrandNotFound() throws JsonProcessingException {

		VehicleDTO dto = VehicleDTO.builder().brand("HONDA").model("2010").name("Fiesta").build();
		ResponseEntity<String> response = restUtils.post("vehicle", dto);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertEquals("Entity not found with the given name: HONDA", response.getBody());
	}

	@Test
	public void shouldReturnTheEntityUpdated() throws JsonProcessingException {

		VehicleBrandDTO brand = VehicleBrandDTO.builder().name("HONDA").build();
		restUtils.post("vehicle-brand", brand);

		VehicleDTO dto = VehicleDTO.builder().brand("HONDA").model("2010").name("Fiesta").build();
		ResponseEntity<String> response = restUtils.post("vehicle", dto);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assertions.assertAll(response::getBody, () -> restUtils.convert(response.getBody(), VehicleDTO.class).getId());
	}
}
