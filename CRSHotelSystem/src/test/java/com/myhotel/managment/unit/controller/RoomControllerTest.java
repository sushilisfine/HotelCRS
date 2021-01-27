package com.myhotel.managment.unit.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;

import com.myhotel.managment.controller.impl.HotelControllerImpl;
import com.myhotel.managment.controller.impl.RoomControllerImpl;
import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Room;
import com.myhotel.managment.dto.RoomDTO;
import com.myhotel.managment.service.RoomService;

class RoomControllerTest extends AbstractTest {

	private MockMvc mockMvc;

	@Mock
	private RoomService roomService;

	@InjectMocks
	private RoomControllerImpl roomController;

	@InjectMocks
	private HotelControllerImpl hotelController;

	@BeforeEach
	public void setup() {

		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(roomController, hotelController).build();
	}

	private RoomDTO roomDtoObj() {
		RoomDTO room = new RoomDTO();
		room.setHotelId(1L);
		room.setCategoryId(1L);
		return room;
	}

	private Room getRoomObj() {
		Room room = new Room();
		room.setId(1L);
		return room;
	}

	private Hotel getHotelObj() {
		Hotel hotel = new Hotel();
		hotel.setId(1L);
		hotel.setAddress("Nagpur");
		hotel.setContact(9999999999L);
		return hotel;
	}

	private Category getCategoryObj() {
		Category category = new Category();
		category.setId(1L);
		category.setCharges(1000.00);
		category.setDescription("Single");
		return category;
	}

	@Test
	void test1SaveRoom() throws Exception {

		RoomDTO roomDTO = roomDtoObj();

		lenient().when(roomService.getHotel(1L)).thenReturn(getHotelObj());
		lenient().when(roomService.add(roomDTO)).thenReturn(roomDTO);

		mockMvc.perform(post("/api/v1/hotels/{hotel_id}//rooms", 1).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(roomDTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		assertNotNull(roomDTO);
	}

	@Test
	void test1UpdateRoom() throws Exception {

		RoomDTO roomDTO = roomDtoObj();

		lenient().when(roomService.get(1L)).thenReturn(getRoomObj());
		lenient().when(roomService.getCategory(1L)).thenReturn(getCategoryObj());
		lenient().when(roomService.getHotel(1L)).thenReturn(getHotelObj());

		lenient().when(roomService.update(roomDTO)).thenReturn(roomDTO);

		mockMvc.perform(put("/api/v1/hotels/{hotel_id}/rooms/{room_id}", 1, 1).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(roomDTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertNotNull(roomDTO);
	}

	@Test
	void test1GetAllRooms() throws Exception {

		List<RoomDTO> roomDTOObj = new ArrayList<>();
		roomDTOObj.add(roomDtoObj());

		lenient().when(roomService.getHotel(1L)).thenReturn(getHotelObj());

		lenient().when(roomService.getAll(1L)).thenReturn(roomDTOObj);

		mockMvc.perform(get("/api/v1/hotels/{hotel_id}/rooms", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertNotNull(roomDTOObj);
	}

	@Test
	void test1GeRoomsByParams() throws Exception {

		List<RoomDTO> roomDTOObj = new ArrayList<>();
		roomDTOObj.add(roomDtoObj());

		lenient().when(roomService.getCategory(1L)).thenReturn(getCategoryObj());
		lenient().when(roomService.getHotel(1L)).thenReturn(getHotelObj());

		lenient().doReturn(roomDTOObj).when(roomService).getByParams(1L, LocalDate.parse("2020-02-01"),
				LocalDate.parse("2020-02-05"), 1L);

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

		requestParams.add("from", "2020-02-01");
		requestParams.add("to", "2020-02-04");
		requestParams.add("categoryId", "1");

		mockMvc.perform(get("/api/v1/hotels/{hotel_id}/rooms/availability", 1).queryParams(requestParams)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertNotNull(roomDTOObj);

	}

}
