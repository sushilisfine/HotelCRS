package com.myhotel.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.controller.ReservationController;
import com.myhotel.domain.Reservation;
import com.myhotel.dto.ReservationDTO;
import com.myhotel.service.ReservationService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller Class for performing operations on Reservation entity.
 *
 * @author Sushil Yadav
 */
@RestController
@Slf4j
public class ReservationControllerImpl implements ReservationController {

	@Autowired
	private ReservationService reservationService;

	/**
	 * Creates a Reservation. Use the returned instance for further operations on
	 * the clients end.
	 * 
	 * @param ReservationDTO , for creating Reservation.
	 * @return the saved ResponseEntity<ReservationDTO>.
	 */
	@Override
	public ResponseEntity<ReservationDTO> add(ReservationDTO reservation) {
		try {
			return new ResponseEntity<>(reservationService.create(reservation), HttpStatus.CREATED);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updates a Reservation. Use the returned instance for further operations on
	 * the clients end.
	 * 
	 * @param Long            reservation id, to validate reservation present and
	 *                        update it
	 * @param ReservationDTO, for creating Reservation.
	 * @return the saved ResponseEntity<ReservationDTO>.
	 */
	@Override
	public ResponseEntity<ReservationDTO> update(Long reservationId, ReservationDTO reservation) {
		reservation.setId(reservationId);
		try {

			if (validateReservation(reservationId))
				return new ResponseEntity<>(reservationService.update(reservation), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		} catch (RuntimeException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Validate if the Reservation is present.
	 * 
	 * @param Long reservationId, to validate Reservation present.
	 * @return boolean.
	 */
	public boolean validateReservation(Long reservationId) {

		Reservation reservation = reservationService.get(reservationId);

		if (reservation.getId() == null) {
			log.info("Unable to find reservation with id : {}", reservationId);
			return false;
		}
		return true;
	}
}
