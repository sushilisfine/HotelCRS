package com.myhotel.controller;

import javax.validation.constraints.NotNull;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhotel.dto.GuestDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@Api(tags = "Guests", value = "Guest Controller", description = "Guest Controller")
@RequestMapping("/api/v1/guests")
public interface GuestController {

	@PostMapping
	@ApiOperation(value = "Add new Guest information.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Guest information added successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<GuestDTO> add(@RequestBody GuestDTO guest);

	@ApiOperation(value = "Update Guest information.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Guest information Updated successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping(value = "/{guest_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<GuestDTO> update(@NotNull @PathVariable("guest_id") Long guestId,
			@NotNull @RequestBody GuestDTO guestRequestDTO);

	@ApiOperation(value = "Fetch Guest information by Id.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Fetched guest information successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/{guest_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<GuestDTO> get(@NotNull @PathVariable("guest_id") Long guestId);

	@GetMapping
	@ApiOperation(value = "Fetch Guest information by name.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Fetched guest information successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<GuestDTO> getByName(@NotNull @RequestParam("guest_name") String guestUserName);

}
