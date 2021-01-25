package com.myhotel.managment.controller;

import java.util.List;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myhotel.managment.dto.HotelDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@Api(tags = "Hotels", value = "Hotel Controller")
@RequestMapping("/api/v1/hotels")
public interface HotelController {

	@ApiOperation(value = "Add new Hotel information.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Hotel information added successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HotelDTO> add(@RequestBody HotelDTO hotel);

	@ApiOperation(value = "Update Hotel information.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Hotel information updated successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping("/{hotel_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HotelDTO> update(@PathVariable("hotel_id") Long hotelId, @RequestBody HotelDTO hotelDTO);

	@ApiOperation(value = "Get All Hotels information.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "All Hotel information fetched successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<HotelDTO>> getAll();

}
