package com.myhotel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myhotel.dto.GuestDTO;
import com.sun.istack.NotNull;

@RequestMapping("/api/v1/guests")
public interface GuestController {

	@PostMapping
	public ResponseEntity<GuestDTO> add(@RequestBody GuestDTO guest);

	@PutMapping(value = "/{guest_id}")
	public ResponseEntity<GuestDTO> update(@NotNull @PathVariable("guest_id") Long guestId,
			@NotNull @RequestBody GuestDTO guestRequestDTO);

	@GetMapping
	public ResponseEntity<List<GuestDTO>> getAll();

	@GetMapping(value = "/{guest_id}")
	public ResponseEntity<GuestDTO> get(@NotNull @PathVariable("guest_id") Long guestId);
}
