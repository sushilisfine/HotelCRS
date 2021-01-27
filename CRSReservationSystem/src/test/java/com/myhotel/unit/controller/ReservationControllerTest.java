package com.myhotel.unit.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.myhotel.controller.impl.ReservationControllerImpl;
import com.myhotel.dto.ReservationDTO;
import com.myhotel.service.ReservationService;

class ReservationControllerTest extends AbstractTest {

	private MockMvc mockMvc;

	@InjectMocks
	ReservationControllerImpl reservationController;

	@Mock
	ReservationService reservationService;

	@BeforeEach
	public void setup() {

		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
	}

	private ReservationDTO reservationRequestObj() {
		ReservationDTO reservation = new ReservationDTO();
		reservation.setHotelId(1L);
		reservation.setCategoryId(1L);
		reservation.setStartDate(LocalDate.parse("2021-02-03"));
		reservation.setEndDate(LocalDate.parse("2021-02-07"));
		return reservation;
	}

	private ReservationDTO reservationResponseObj() {
		ReservationDTO reservation = new ReservationDTO();
		reservation.setId(1L);
		reservation.setCharges(10000.00);
		reservation.setGuestId(1L);
		reservation.setRoomId(1L);
		reservation.setOfferId(1L);
		reservation.setHotelId(1L);
		reservation.setCategoryId(1L);
		reservation.setStartDate(LocalDate.parse("2021-02-03"));
		reservation.setEndDate(LocalDate.parse("2021-02-07"));
		return reservation;
	}

	@Test
	void testReservation() throws Exception {

		ReservationDTO reservationReqObj = reservationRequestObj();
		ReservationDTO reservationResObj = reservationResponseObj();

		lenient().doReturn(reservationResObj).when(reservationService).create(Mockito.any(ReservationDTO.class));

		mockMvc.perform(post("/api/v1/reservations").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(reservationReqObj)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.guestId", is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));

	}

}
