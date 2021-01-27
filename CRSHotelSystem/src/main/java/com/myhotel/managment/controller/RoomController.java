package com.myhotel.managment.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhotel.managment.dto.RoomDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@Api(tags = "Rooms", value = "Room Controller")
@RequestMapping("/api/v1/hotels/{hotel_id}/")
public interface RoomController {

	@ApiOperation(value = "Add new Room information.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Room information added successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("rooms")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RoomDTO> add(@PathVariable("hotel_id") Long hotelId, @RequestBody RoomDTO room);

	@ApiOperation(value = "Update Room information.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Room information updated successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping("rooms/{room_id}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<RoomDTO> update(@PathVariable("hotel_id") Long hotelId, @PathVariable("room_id") Long roomId,
			@RequestBody RoomDTO roomDTO);

	@ApiOperation(value = "Get all Rooms information.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Rooms information fetched successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("rooms")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<RoomDTO>> getAll(@PathVariable("hotel_id") Long hotelId);

	@ApiOperation(value = "Get Available Rooms.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Available Rooms  information fetched successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("rooms/availability")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<RoomDTO>> getAvailable(@PathVariable(required = true, name = "hotel_id") Long hotelId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to, @RequestParam Long categoryId);

}
