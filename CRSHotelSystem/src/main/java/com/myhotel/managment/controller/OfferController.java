package com.myhotel.managment.controller;

import java.util.List;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhotel.managment.dto.OfferDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@Api(tags = "Offers", value = "Offer Controller")
@RequestMapping("/api/v1/hotels/{hotel_id}/")
public interface OfferController {

	@ApiOperation(value = "Add new Offer information.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Offer information added successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("offers")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<OfferDTO> add(@PathVariable("hotel_id") Long hotelId, @RequestBody OfferDTO offer);

	@ApiOperation(value = "Update Offer information.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Offer information updated successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping("offers/{offer_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<OfferDTO> update(@PathVariable("hotel_id") Long hotelId,
			@PathVariable("offer_id") Long offerId, @RequestBody OfferDTO offer);

	@ApiOperation(value = "Get all Offers information.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Offer information fetched successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("offers")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<OfferDTO>> getAll(@PathVariable("hotel_id") Long hotelId,
			@RequestParam("category_id") Long categoryId);

	@ApiOperation(value = "Get Offers information.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Offer information fetched successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("offers/{offer_id}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<OfferDTO> get(@PathVariable("hotel_id") Long hotelId, @PathVariable("offer_id") Long offerlId,
			@RequestParam("category_id") Long categoryId);

	@ApiOperation(value = "Delete Offer information.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Offer information deleted successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@DeleteMapping("offers/{offer_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Long> delete(@PathVariable("hotel_id") Long hotelId, @PathVariable("offer_id") Long offerId,
			@RequestParam("category_id") Long categoryId);
}
