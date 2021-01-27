package com.myhotel.managment.unit.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.myhotel.managment.controller.impl.HotelControllerImpl;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.HotelDTO;
import com.myhotel.managment.service.HotelService;

class HotelControllerTest extends AbstractTest {

	private MockMvc mockMvc;

	@Mock
	private HotelService hotelService;

	@InjectMocks
	private HotelControllerImpl hotelController;

	@BeforeEach
	public void setup() {

		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
	}

	private HotelDTO hotelDtoObj() {
		HotelDTO hotel = new HotelDTO();
		hotel.setId(1L);
		hotel.setAddress("Nagpur");
		hotel.setContact(9999999999L);
		return hotel;
	}

	private Hotel hotelObj() {
		Hotel hotel = new Hotel();
		hotel.setId(1L);
		hotel.setAddress("Nagpur");
		hotel.setContact(9999999999L);
		return hotel;
	}

	@Test
	void test1SaveHotel() throws Exception {

		HotelDTO hotelDtoObj = hotelDtoObj();

		lenient().doReturn(hotelDtoObj).when(hotelService).create(hotelDtoObj);

		mockMvc.perform(post("/api/v1/hotels").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(hotelDtoObj)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		assertEquals(hotelDtoObj.getContact(), hotelDtoObj.getContact());

	}

	@Test
	void test1UpdateHotel() throws Exception {

		HotelDTO hotelDtoObj = hotelDtoObj();
		Hotel hotelObj = hotelObj();

		lenient().doReturn(hotelObj).when(hotelService).get(1L);
		lenient().doReturn(hotelDtoObj).when(hotelService).update(hotelDtoObj);

		mockMvc.perform(put("/api/v1/hotels/{hotel_id}", 1).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(hotelDtoObj)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertEquals(hotelDtoObj.getContact(), hotelObj.getContact());

	}

	@Test
	void test1GetAllHotels() throws Exception {

		List<HotelDTO> hotelDtoObj = new ArrayList<>();
		hotelDtoObj.add(hotelDtoObj());

		lenient().doReturn(hotelDtoObj).when(hotelService).getAll();

		mockMvc.perform(get("/api/v1/hotels").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].address", is("Nagpur")));
	}

	@Test
	void testValidateHotel() {
		Hotel hotelObj = hotelObj();
		lenient().doReturn(hotelObj).when(hotelService).get(1L);
		boolean valid = hotelController.validateHotel(1L);
		assertEquals(true, valid);
	}
}
