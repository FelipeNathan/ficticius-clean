package br.com.ficticiusclean.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ficticiusclean.dto.VehicleBrandDTO;
import br.com.ficticiusclean.exception.InvalidEntityException;

@SpringBootTest
public class VehicleBrandServiceTest {

	private final String HONDA = "HONDA";

	@InjectMocks
	private VehicleBrandService service;

	@Test
	public void shouldThrowInvalidEntityException() {

		VehicleBrandDTO dto = VehicleBrandDTO.builder().build();

		assertThrows(InvalidEntityException.class, () -> service.validateDTO(dto));
	}

	@Test
	public void shouldNotThrowException() {

		VehicleBrandDTO dto = VehicleBrandDTO.builder().name(HONDA).build();

		assertDoesNotThrow(() -> service.validateDTO(dto));
	}
}
