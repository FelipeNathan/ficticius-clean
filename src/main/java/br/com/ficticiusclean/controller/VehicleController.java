package br.com.ficticiusclean.controller;

import br.com.ficticiusclean.controller.core.ControllerBase;
import br.com.ficticiusclean.dto.VehicleDTO;
import br.com.ficticiusclean.exception.InvalidEntityException;
import br.com.ficticiusclean.model.Vehicle;
import br.com.ficticiusclean.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController extends ControllerBase<Vehicle, VehicleDTO, VehicleService> {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> create(@RequestBody VehicleDTO dto) throws InvalidEntityException {
        return new ResponseEntity<>(this.service.createVehicle(dto), HttpStatus.CREATED);
    }

    @GetMapping("/consumption")
    public ResponseEntity<List<VehicleDTO>> calculateConsumption(double gasPrice, double kmInCity, double kmInRoad) {
        return new ResponseEntity<>(this.service.calculateConsumption(gasPrice, kmInCity, kmInRoad), HttpStatus.OK);
    }

}
