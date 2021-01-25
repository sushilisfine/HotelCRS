package com.myhotel.controller.impl;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.controller.GuestController;
import com.myhotel.domain.Guest;
import com.myhotel.dto.GuestDTO;
import com.myhotel.service.GuestService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GuestControllerImpl implements GuestController {

	@Autowired
	private GuestService guestService;

	@Override
	public ResponseEntity<GuestDTO> add(GuestDTO guest) {
		try {
			return new ResponseEntity<>(guestService.create(guest), HttpStatus.CREATED);
		} catch (RuntimeException e) {
			log.info("Creating new guest failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<GuestDTO> update(Long guestId, GuestDTO guestRequestDTO) {
		guestRequestDTO.setId(guestId);

		try {
			if (validate(guestId))
				return new ResponseEntity<>(guestService.update(guestRequestDTO), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Update guest failed for guest id: {}", guestId);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<GuestDTO> get(Long guestId) {
		try {
			return new ResponseEntity<>(guestService.get(guestId), HttpStatus.OK);
		} catch (RuntimeException e) {
			log.info("Getting guest with id : {} failed", guestId);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<GuestDTO> getByName(@NotNull String guestUserName) {
		try {
			return new ResponseEntity<>(guestService.getByName(guestUserName), HttpStatus.OK);
		} catch (RuntimeException e) {
			log.info("Getting guest with username : {} failed", guestUserName);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean validate(Long guestId) {

		Guest guest = guestService.getGuestById(guestId);

		if (guest.getId() == null) {
			log.info("Unable to find guest with id : {}", guestId);
			return false;
		}
		return true;
	}

}
