package com.myhotel.managment.controller;

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

import com.myhotel.managment.dto.CategoryDTO;
import com.myhotel.managment.util.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@Api(tags = "Categories", value = "Category Controller")
@RequestMapping("/api/v1/hotels/{hotel_id}/")
public interface CategoryController {

	@ApiOperation(value = "Add new Category information.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Category information added successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("categories")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Object>> add(@NotNull @PathVariable("hotel_id") Long hotelId,
			@NotNull @RequestBody CategoryDTO category);

	@ApiOperation(value = "Update Category information.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Category information updated successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping("categories/{category_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Object>> update(@NotNull @PathVariable("hotel_id") Long hotelId,
			@NotNull @PathVariable("category_id") Long categoryId, @NotNull @RequestBody CategoryDTO category);

	@ApiOperation(value = "Get All Categories information.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "All Category information fetched successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("categories")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Response<Object>> getAll(@NotNull @PathVariable("hotel_id") Long hotelId);

	@ApiOperation(value = "Get Category information.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Category information fetched successfully."),
			@ApiResponse(code = 401, message = "Authentication failed."),
			@ApiResponse(code = 400, message = "Inputs not correct"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("categories/{category_id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Response<Object>> get(@NotNull @PathVariable("hotel_id") Long hotelId,
			@NotNull @PathVariable("category_id") Long categoryId);

}
