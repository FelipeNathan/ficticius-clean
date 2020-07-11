package br.com.ficticiusclean.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ficticiusclean.dto.VehicleBrandDTO;
import br.com.ficticiusclean.exception.InvalidEntityException;

@SpringBootTest
public class VehicleBrandServiceTest {

	@InjectMocks
	private VehicleBrandService service;

	@Test
	public void shouldThrowInvalidEntityException() {

		VehicleBrandDTO dto = VehicleBrandDTO.builder().build();
		Assertions.assertThrows(InvalidEntityException.class, () -> service.validateDTO(dto));
	}

	@Test
	public void shouldNotThrowException() {

		VehicleBrandDTO dto = VehicleBrandDTO.builder().name("HONDA").build();
		Assertions.assertDoesNotThrow(() -> service.validateDTO(dto));
	}
}
