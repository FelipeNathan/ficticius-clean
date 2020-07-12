package br.com.ficticiusclean.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.BigDecimalAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.ficticiusclean.dto.VehicleConsumptionDTO;
import br.com.ficticiusclean.dto.VehicleDTO;
import br.com.ficticiusclean.exception.EntityNotFoundException;
import br.com.ficticiusclean.exception.InvalidEntityException;
import br.com.ficticiusclean.mapper.VehicleMapper;
import br.com.ficticiusclean.model.Vehicle;
import br.com.ficticiusclean.model.VehicleBrand;
import br.com.ficticiusclean.repository.VehicleRepository;

@ExtendWith(SpringExtension.class)
public class VehicleServiceTest {

	private final BigDecimal ONE = BigDecimal.ONE.setScale(2, RoundingMode.HALF_EVEN);
	private final BigDecimal TWO = new BigDecimal("2.00");
	private final BigDecimal FOUR = new BigDecimal("4");
	private final BigDecimal EIGTH = new BigDecimal("8.00");
	private final BigDecimal TEN = BigDecimal.TEN.setScale(2, RoundingMode.HALF_EVEN);
	private final BigDecimal THIRTEEN = new BigDecimal("13");
	private final BigDecimal SIXTEEN = new BigDecimal("16");
	private final BigDecimal EIGHTEEN = new BigDecimal("18");
	private final BigDecimal NEGATIVE_NUMBER = new BigDecimal("-1");

	private final String HONDA = "HONDA";
	private final String CIVIC = "CIVIC";
	private final String FIESTA = "FIESTA";
	private final String SIENA = "SIENA";
	private final String MODEL_2010 = "2010";

	private final Long ENTITY_ID = 1L;

	@InjectMocks
	private VehicleService service;

	@Mock
	private VehicleBrandService brandService;

	@Mock
	private Vehicle entity;

	@Mock
	private VehicleMapper mapper;

	@Mock
	private VehicleRepository repository;

	@Mock
	private VehicleBrand brand;

	@Test
	public void shouldThrowEntityNotFoundException() {

		VehicleDTO dto = VehicleDTO.builder()
				.brand(HONDA)
				.name(CIVIC)
				.model(MODEL_2010)
				.gasConsumptionCity(FOUR)
				.gasConsumptionRoad(THIRTEEN)
				.build();

		when(brandService.findOneByName(HONDA)).thenReturn(null);

		assertThrows(EntityNotFoundException.class, () -> service.createVehicle(dto));
	}

	@Test
	public void shouldCreateVehicleWithSuccess() {

		VehicleDTO dto = VehicleDTO.builder()
				.brand(HONDA)
				.name(CIVIC)
				.model(MODEL_2010)
				.gasConsumptionCity(FOUR)
				.gasConsumptionRoad(THIRTEEN)
				.build();

		when(mapper.toEntity(dto)).thenReturn(entity);
		when(brandService.findOneByName(HONDA)).thenReturn(brand);
		when(entity.getId()).thenReturn(ENTITY_ID);
		when(repository.save(entity)).thenReturn(entity);

		dto = service.createVehicle(dto);

		assertEquals(HONDA, dto.getBrand());
		assertEquals(CIVIC, dto.getName());
		assertEquals(MODEL_2010, dto.getModel());
		assertEquals(ENTITY_ID, dto.getId());
	}

	@Test
	public void shouldThrowBrandIsRequired() {

		VehicleDTO dto = VehicleDTO.builder().build();

		assertThrows(InvalidEntityException.class, () -> service.validateDTO(dto));
	}

	@Test
	public void shouldThrowModelIsRequired() {

		VehicleDTO dto = VehicleDTO.builder().brand(HONDA).build();

		assertThrows(InvalidEntityException.class, () -> service.validateDTO(dto));
	}

	@Test
	public void shouldThrowNameIsRequired() {

		VehicleDTO dto = VehicleDTO.builder().brand(HONDA).model(MODEL_2010).build();

		assertThrows(InvalidEntityException.class, () -> service.validateDTO(dto));
	}

	@Test
	public void whenConsumptionIsEmptyShouldThrowGasConsumptionInCityIsRequired() {

		VehicleDTO dtoWithNoConsumption = VehicleDTO.builder().brand(HONDA).name(CIVIC).model(MODEL_2010).gasConsumptionRoad(THIRTEEN).build();

		assertThrows(InvalidEntityException.class, () -> service.validateDTO(dtoWithNoConsumption));
	}

	@Test
	public void whenConsumptionNegativeShouldThrowGasConsumptionInCityIsRequired() {

		VehicleDTO dtoWithNegativeConsumption = VehicleDTO.builder()
				.brand(HONDA)
				.name(CIVIC)
				.model(MODEL_2010)
				.gasConsumptionCity(NEGATIVE_NUMBER)
				.gasConsumptionRoad(THIRTEEN)
				.build();

		assertThrows(InvalidEntityException.class, () -> service.validateDTO(dtoWithNegativeConsumption));
	}

	@Test
	public void whenConsumptionIsEmptyShouldThrowGasConsumptionInRoadIsRequired() {

		VehicleDTO dtoWithNoConsumption = VehicleDTO.builder().brand(HONDA).name(CIVIC).model(MODEL_2010).gasConsumptionCity(FOUR).build();

		assertThrows(InvalidEntityException.class, () -> service.validateDTO(dtoWithNoConsumption));
	}

	@Test
	public void whenConsumptionNegativeShouldThrowGasConsumptionInRoadIsRequired() {

		VehicleDTO dtoWithNegativeConsumption = VehicleDTO.builder()
				.brand(HONDA)
				.name(CIVIC)
				.model(MODEL_2010)
				.gasConsumptionCity(FOUR)
				.gasConsumptionRoad(NEGATIVE_NUMBER)
				.build();

		assertThrows(InvalidEntityException.class, () -> service.validateDTO(dtoWithNegativeConsumption));
	}

	@Test
	public void shouldValidateWithSuccess() {

		VehicleDTO dto = VehicleDTO.builder()
				.brand(HONDA)
				.name(CIVIC)
				.model(MODEL_2010)
				.gasConsumptionCity(FOUR)
				.gasConsumptionRoad(THIRTEEN)
				.build();

		service.validateDTO(dto);

		assertEquals(HONDA, dto.getBrand());
		assertEquals(CIVIC, dto.getName());
		assertEquals(MODEL_2010, dto.getModel());
		assertEquals(FOUR, dto.getGasConsumptionCity());
		assertEquals(THIRTEEN, dto.getGasConsumptionRoad());
	}

	@Test
	public void shouldIgnoreConsumptionRoadWhenConsumptionRoadIsZero() {

		VehicleDTO civic = VehicleDTO.builder().name(CIVIC).gasConsumptionCity(BigDecimal.TEN).gasConsumptionRoad(BigDecimal.ZERO).build();
		VehicleConsumptionDTO civicConsumption = VehicleConsumptionDTO.builder().name(CIVIC).build();

		service.calculateConsumptionByVehicle(civic, civicConsumption, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);

		assertEquals(TEN, civicConsumption.getPriceConsumptionTotal());
		assertEquals(ONE, civicConsumption.getGasConsumptionTotal());
	}

	@Test
	public void shouldIgnoreConsumptionCityWhenConsumptionCityIsZero() {

		VehicleDTO fiesta = VehicleDTO.builder().name(FIESTA).gasConsumptionCity(BigDecimal.ZERO).gasConsumptionRoad(BigDecimal.TEN).build();
		VehicleConsumptionDTO fiestaConsumption = VehicleConsumptionDTO.builder().name(FIESTA).build();

		service.calculateConsumptionByVehicle(fiesta, fiestaConsumption, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);

		assertEquals(TEN, fiestaConsumption.getPriceConsumptionTotal());
		assertEquals(ONE, fiestaConsumption.getGasConsumptionTotal());
	}

	@Test
	public void shouldCalculateConsumption() {

		VehicleDTO civic = VehicleDTO.builder().name(CIVIC).gasConsumptionCity(BigDecimal.TEN).gasConsumptionRoad(THIRTEEN).build();
		VehicleConsumptionDTO civicConsumption = VehicleConsumptionDTO.builder().name(CIVIC).build();

		service.calculateConsumptionByVehicle(civic, civicConsumption, FOUR, BigDecimal.TEN, THIRTEEN);

		BigDecimalAssert civicConsumptionTotal = new BigDecimalAssert(civicConsumption.getGasConsumptionTotal());
		BigDecimalAssert civicPriceConsumptionTotal = new BigDecimalAssert(civicConsumption.getPriceConsumptionTotal());

		civicConsumptionTotal.isEqualTo(TWO);
		civicPriceConsumptionTotal.isEqualTo(EIGTH);
	}

	@Test
	public void rankUpToDownVehicleByConsumptionCost() {

		VehicleDTO civicDTO = VehicleDTO.builder().name(CIVIC).gasConsumptionCity(BigDecimal.TEN).gasConsumptionRoad(EIGHTEEN).build();
		VehicleDTO sienaDTO = VehicleDTO.builder().name(SIENA).gasConsumptionCity(THIRTEEN).gasConsumptionRoad(SIXTEEN).build();
		VehicleDTO fiestaDTO = VehicleDTO.builder().name(FIESTA).gasConsumptionCity(BigDecimal.TEN).gasConsumptionRoad(THIRTEEN).build();

		when(service.findAllDTO()).thenReturn(Arrays.asList(civicDTO, sienaDTO, fiestaDTO));

		List<VehicleConsumptionDTO> dtos = service.calculateConsumption(FOUR, BigDecimal.TEN, THIRTEEN);

		assertNotNull(dtos);
		assertEquals(3, dtos.size());
		assertEquals(dtos.get(0).getName(), FIESTA);
		assertEquals(dtos.get(1).getName(), CIVIC);
		assertEquals(dtos.get(2).getName(), SIENA);
	}
}
