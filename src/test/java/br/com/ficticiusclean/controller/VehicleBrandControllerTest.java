package br.com.ficticiusclean.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import br.com.ficticiusclean.rest.RestUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = { FicticiuscleanApplication.class, RestUtils.class })
public class VehicleBrandControllerTest {

	private final String HONDA = "HONDA";
	private final String VEHICLE_BRAND_URI = "vehicle-brand";

	@LocalServerPort
	private int port;

	@Inject
	private RestUtils restUtils;

	@BeforeAll
	public void configure() {

		restUtils.setPort(port);
	}

	@Test
	public void shouldReturnBadRequest() throws JsonProcessingException {

		VehicleBrandDTO dto = VehicleBrandDTO.builder().build();

		ResponseEntity<String> response = restUtils.post(VEHICLE_BRAND_URI, dto);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void shouldReturnTheEntityUpdated() throws JsonProcessingException {

		VehicleBrandDTO dto = VehicleBrandDTO.builder().name(HONDA).build();

		ResponseEntity<String> response = restUtils.post(VEHICLE_BRAND_URI, dto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertAll(response::getBody, () -> restUtils.convert(response.getBody(), VehicleBrandDTO.class).getId());
	}
}
