package com.myhotel.service;

import com.myhotel.domain.Reservation;
import com.myhotel.dto.ReservationDTO;

public interface ReservationService {

	/**
	 * @param ReservationDTO
	 * @return ReservationDTO
	 */
	ReservationDTO create(ReservationDTO reservationReqObj);

	/**
	 * @param reservation
	 * @return ReservationDTO
	 */
	ReservationDTO update(ReservationDTO reservation);

	/**
	 * @param reservationId
	 * @return Reservation
	 */
	Reservation get(Long reservationId);

}
