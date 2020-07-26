package br.com.ficticiusclean.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import javax.inject.Inject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.ficticiusclean.FicticiuscleanApplication;
import br.com.ficticiusclean.dto.VehicleBrandDTO;
import br.com.ficticiusclean.dto.VehicleDTO;
import br.com.ficticiusclean.rest.RestUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = { FicticiuscleanApplication.class, RestUtils.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class VehicleControllerTest {

	private final String HONDA = "HONDA";
	private final String CIVIC = "CIVIC";
	private final String MODEL_2010 = "2010";
	private final String VEHICLE_BRAND_URI = "vehicle-brand";
	private final String VEHICLE_URI = "vehicle";
	private final BigDecimal FOUR = new BigDecimal("4");
	private final BigDecimal THIRTEEN = new BigDecimal("13");

	@LocalServerPort
	private int port;

	@Inject
	private RestUtils restUtils;

	@BeforeAll
	void configure() {

		restUtils.setPort(port);
	}

	@Test
	void shouldReturnBadRequestWithBrandRequiredAsReponseBody() throws JsonProcessingException {

		VehicleDTO dto = VehicleDTO.builder().build();
		ResponseEntity<String> response = restUtils.post(VEHICLE_URI, dto);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Brand is required", response.getBody());
	}

	@Test
	void shouldReturnBadRequestWithModelRequiredAsReponseBody() throws JsonProcessingException {

		VehicleDTO dto = VehicleDTO.builder().brand(HONDA).build();
		ResponseEntity<String> response = restUtils.post(VEHICLE_URI, dto);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Model is required", response.getBody());
	}

	@Test
	void shouldReturnBadRequestWithNameRequiredAsReponseBody() throws JsonProcessingException {

		VehicleDTO dto = VehicleDTO.builder().brand(HONDA).model(MODEL_2010).build();
		ResponseEntity<String> response = restUtils.post(VEHICLE_URI, dto);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Name is required", response.getBody());
	}

	@Test
	void shouldReturnBadRequestWithBrandNotFound() throws JsonProcessingException {

		VehicleDTO dto = VehicleDTO.builder()
				.brand(HONDA)
				.name(CIVIC)
				.model(MODEL_2010)
				.gasConsumptionCity(FOUR)
				.gasConsumptionRoad(THIRTEEN)
				.build();

		ResponseEntity<String> response = restUtils.post(VEHICLE_URI, dto);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Entity not found with the given name: " + HONDA, response.getBody());
	}

	@Test
	void shouldReturnTheEntityUpdated() throws JsonProcessingException {

		VehicleBrandDTO brand = VehicleBrandDTO.builder().name(HONDA).build();
		VehicleDTO dto = VehicleDTO.builder()
				.brand(HONDA)
				.name(CIVIC)
				.model(MODEL_2010)
				.gasConsumptionCity(FOUR)
				.gasConsumptionRoad(THIRTEEN)
				.build();

		restUtils.post(VEHICLE_BRAND_URI, brand);
		ResponseEntity<String> response = restUtils.post(VEHICLE_URI, dto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertAll(response::getBody, () -> restUtils.convert(response.getBody(), VehicleDTO.class).getId());
	}
}
