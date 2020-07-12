package br.com.ficticiusclean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ficticiusclean.controller.VehicleBrandController;
import br.com.ficticiusclean.controller.VehicleController;

@SpringBootTest
class FicticiuscleanApplicationTests {

	@Inject
	private VehicleController vehicleController;

	@Inject
	private VehicleBrandController vehicleBrandController;

	@Test
	void contextLoads() {

		assertNotNull(vehicleController);
		assertNotNull(vehicleBrandController);
	}

}
