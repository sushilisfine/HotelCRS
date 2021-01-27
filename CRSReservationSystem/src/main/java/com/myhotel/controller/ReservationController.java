package com.myhotel.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myhotel.dto.ReservationDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@RequestMapping("/api/v1/reservations")
@Api(tags = "Reservations", value = "Reservation Controller", description = "Reservation Controller")
public interface ReservationController {

	@ApiOperation(value = "Create new Reservation information.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Reservation information created successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@PostMapping
	public ResponseEntity<ReservationDTO> add(@RequestBody ReservationDTO reservation);

}
