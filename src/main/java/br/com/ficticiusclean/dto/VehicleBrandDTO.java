package br.com.ficticiusclean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleBrandDTO implements DTOBase {

	private Long id;

	private String name;

}
