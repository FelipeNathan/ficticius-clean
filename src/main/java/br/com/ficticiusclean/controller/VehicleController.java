package br.com.ficticiusclean.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ficticiusclean.controller.core.ControllerBase;
import br.com.ficticiusclean.dto.VehicleDTO;
import br.com.ficticiusclean.model.Vehicle;
import br.com.ficticiusclean.service.VehicleService;

@RestController
@RequestMapping("/vehicle")
public class VehicleController extends ControllerBase<Vehicle, VehicleDTO, VehicleService> {

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VehicleDTO> create(@RequestBody VehicleDTO dto) {

		return new ResponseEntity<>(this.service.createVehicle(dto), HttpStatus.CREATED);
	}

	@GetMapping("/consumption")
	public ResponseEntity<List<VehicleDTO>> calculateConsumption(BigDecimal gasPrice, BigDecimal kmInCity, BigDecimal kmInRoad) {

		return new ResponseEntity<>(this.service.calculateConsumption(gasPrice, kmInCity, kmInRoad), HttpStatus.OK);
	}

}
