package br.com.ficticiusclean.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.BigDecimalAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ficticiusclean.dto.VehicleConsumptionDTO;
import br.com.ficticiusclean.dto.VehicleDTO;
import br.com.ficticiusclean.exception.EntityNotFoundException;
import br.com.ficticiusclean.exception.InvalidEntityException;
import br.com.ficticiusclean.mapper.VehicleMapper;
import br.com.ficticiusclean.model.Vehicle;
import br.com.ficticiusclean.model.VehicleBrand;
import br.com.ficticiusclean.repository.VehicleRepository;

@SpringBootTest
public class VehicleServiceTest {

	@InjectMocks
	private VehicleService service;

	@Mock
	private VehicleBrandService brandService;

	@Mock
	private Vehicle entity;

	@Mock
	private VehicleDTO dto;

	@Mock
	private VehicleMapper mapper;

	@Mock
	private VehicleRepository repository;

	@Mock
	private VehicleBrand brand;

	@Test
	public void shouldThrowEntityNotFoundException() {

		Mockito.when(dto.getBrand()).thenReturn("HONDA");
		Mockito.when(dto.getName()).thenReturn("Civic");
		Mockito.when(dto.getModel()).thenReturn("2010");
		Mockito.when(brandService.findOneByName("HONDA")).thenReturn(null);

		Assertions.assertThrows(EntityNotFoundException.class, () -> service.createVehicle(dto));
	}

	@Test
	public void shouldNotThrowEntityNotFoundException() {

		Mockito.when(dto.getBrand()).thenReturn("HONDA");
		Mockito.when(dto.getName()).thenReturn("Civic");
		Mockito.when(dto.getModel()).thenReturn("2010");
		Mockito.when(mapper.toEntity(dto)).thenReturn(entity);
		Mockito.when(brandService.findOneByName("HONDA")).thenReturn(brand);
		Mockito.when(repository.save(entity)).thenReturn(entity);

		Assertions.assertDoesNotThrow(() -> service.createVehicle(dto));
	}

	@Test
	public void shouldThrowBrandIsRequired() {

		Assertions.assertThrows(InvalidEntityException.class, () -> service.validateDTO(dto));
	}

	@Test
	public void shouldThrowModelIsRequired() {

		Mockito.when(dto.getBrand()).thenReturn("HONDA");
		Assertions.assertThrows(InvalidEntityException.class, () -> service.validateDTO(dto));
	}

	@Test
	public void shouldThrowNameIsRequired() {

		Mockito.when(dto.getBrand()).thenReturn("HONDA");
		Mockito.when(dto.getModel()).thenReturn("2010");
		Assertions.assertThrows(InvalidEntityException.class, () -> service.validateDTO(dto));
	}

	@Test
	public void shouldNotThrowInvalidEntityException() {

		Mockito.when(dto.getName()).thenReturn("Civic");
		Mockito.when(dto.getBrand()).thenReturn("HONDA");
		Mockito.when(dto.getModel()).thenReturn("2010");
		Assertions.assertDoesNotThrow(() -> service.validateDTO(dto));
	}

	@Test
	public void shouldIgnoreCalculationWhenZeroDivisor() {

		VehicleDTO fiesta = VehicleDTO.builder().name("Fiesta").gasConsumptionCity(BigDecimal.TEN).gasConsumptionRoad(BigDecimal.ZERO).build();
		VehicleConsumptionDTO fiestaConsumption = VehicleConsumptionDTO.builder().name("Fiesta").build();

		Assertions.assertDoesNotThrow(() -> service.calculateConsumptionByVehicle(fiesta, fiestaConsumption, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN));
	}

	@Test
	public void calculateConsumption() {

		BigDecimal quatro = new BigDecimal("4");
		BigDecimal treze = new BigDecimal("13");
		BigDecimal dezesseis = new BigDecimal("16");
		BigDecimal dezoito = new BigDecimal("18");

		VehicleDTO fiesta = VehicleDTO.builder().name("Fiesta").gasConsumptionCity(BigDecimal.TEN).gasConsumptionRoad(treze).build();
		VehicleConsumptionDTO fiestaConsumption = VehicleConsumptionDTO.builder().name("Fiesta").build();

		VehicleDTO civic = VehicleDTO.builder().name("Civic").gasConsumptionCity(BigDecimal.TEN).gasConsumptionRoad(dezoito).build();
		VehicleConsumptionDTO civicConsumption = VehicleConsumptionDTO.builder().name("Civic").build();

		VehicleDTO siena = VehicleDTO.builder().name("Siena").gasConsumptionCity(treze).gasConsumptionRoad(dezesseis).build();
		VehicleConsumptionDTO sienaConsumption = VehicleConsumptionDTO.builder().name("Siena").build();

		service.calculateConsumptionByVehicle(fiesta, fiestaConsumption, quatro, BigDecimal.TEN, treze);
		service.calculateConsumptionByVehicle(civic, civicConsumption, quatro, BigDecimal.TEN, treze);
		service.calculateConsumptionByVehicle(siena, sienaConsumption, quatro, BigDecimal.TEN, treze);

		BigDecimalAssert fiestaConsumptionTotal = new BigDecimalAssert(fiestaConsumption.getGasConsumptionTotal());
		BigDecimalAssert fiestaPriceConsumptionTotal = new BigDecimalAssert(fiestaConsumption.getPriceConsumptionTotal());

		fiestaConsumptionTotal.isEqualTo(new BigDecimal("2.00"));
		fiestaPriceConsumptionTotal.isEqualTo(new BigDecimal("8.00"));

		BigDecimalAssert civicConsumptionTotal = new BigDecimalAssert(civicConsumption.getGasConsumptionTotal());
		BigDecimalAssert civicPriceConsumptionTotal = new BigDecimalAssert(civicConsumption.getPriceConsumptionTotal());

		civicConsumptionTotal.isEqualTo(new BigDecimal("1.72"));
		civicPriceConsumptionTotal.isEqualTo(new BigDecimal("6.88"));

		BigDecimalAssert sienaConsumptionTotal = new BigDecimalAssert(sienaConsumption.getGasConsumptionTotal());
		BigDecimalAssert sienaPriceConsumptionTotal = new BigDecimalAssert(sienaConsumption.getPriceConsumptionTotal());

		sienaConsumptionTotal.isEqualTo(new BigDecimal("1.58"));
		sienaPriceConsumptionTotal.isEqualTo(new BigDecimal("6.32"));
	}

	@Test
	public void rankUpToDownVehicleByConsumptionCost() {

		BigDecimal quatro = new BigDecimal("4");
		BigDecimal treze = new BigDecimal("13");
		BigDecimal dezesseis = new BigDecimal("16");
		BigDecimal dezoito = new BigDecimal("18");

		// Cost: $ 6,88
		VehicleDTO civicDTO = VehicleDTO.builder().name("Civic").gasConsumptionCity(BigDecimal.TEN).gasConsumptionRoad(dezoito).build();
		// Cost: $ 6,32
		VehicleDTO sienaDTO = VehicleDTO.builder().name("Siena").gasConsumptionCity(treze).gasConsumptionRoad(dezesseis).build();
		// Cost: $ 8
		VehicleDTO fiestaDTO = VehicleDTO.builder().name("Fiesta").gasConsumptionCity(BigDecimal.TEN).gasConsumptionRoad(treze).build();

		Mockito.when(service.findAllDTO()).thenReturn(Arrays.asList(civicDTO, sienaDTO, fiestaDTO));

		List<VehicleConsumptionDTO> dtos = service.calculateConsumption(quatro, BigDecimal.TEN, treze);

		Assertions.assertNotNull(dtos);
		Assertions.assertEquals(3, dtos.size());
		Assertions.assertEquals(dtos.get(0).getName(), "Fiesta");
		Assertions.assertEquals(dtos.get(1).getName(), "Civic");
		Assertions.assertEquals(dtos.get(2).getName(), "Siena");
	}
}
