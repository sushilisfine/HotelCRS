package com.myhotel.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.myhotel.controller.impl.GuestControllerImpl;
import com.myhotel.domain.Guest;
import com.myhotel.dto.GuestDTO;
import com.myhotel.service.GuestService;

class GuestControllerTest extends AbstractTest {

	private MockMvc mockMvc;

	@Mock
	private GuestService guestService;

	@InjectMocks
	private GuestControllerImpl guestController;

	@BeforeEach
	public void setup() {

		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(guestController).build();
	}

	private GuestDTO guestDTOObj() {
		GuestDTO guest = new GuestDTO();
		guest.setId(1L);
		guest.setEmail("test@test.com");
		guest.setName("test");
		guest.setContact(9999999999L);
		return guest;
	}

	private Guest guestObj() {
		Guest guest = new Guest();
		guest.setId(1L);
		guest.setEmail("test@test.com");
		guest.setName("test");
		guest.setContact(9999999999L);
		return guest;
	}

	@Test
	void test1SaveGuest1() throws Exception {

		GuestDTO guestDTOObj = guestDTOObj();

		lenient().doReturn(guestDTOObj).when(guestService).create(guestDTOObj);

		mockMvc.perform(post("/api/v1/guests").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(guestDTOObj)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		assertEquals(guestDTOObj.getContact(), guestDTOObj.getContact());

	}

	@Test
	void test1UpdateGuest() throws Exception {

		GuestDTO guestDTOObj = guestDTOObj();

		lenient().doReturn(guestObj()).when(guestService).getGuestById(1L);
		lenient().doReturn(guestDTOObj).when(guestService).update(guestDTOObj);

		mockMvc.perform(put("/api/v1/guests/1").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(guestDTOObj)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertEquals(guestDTOObj.getContact(), guestDTOObj.getContact());

	}

	@Test
	void test1UpateGuest2() throws Exception {

		GuestDTO guestDTOObj = guestDTOObj();

		lenient().doReturn(new Guest()).when(guestService).getGuestById(2L);
		mockMvc.perform(put("/api/v1/guests/2").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(guestDTOObj)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	void test1CreateCategory3() throws Exception {
		GuestDTO guestDTOObj = guestDTOObj();
		mockMvc.perform(put("/api/v1/guests/2", 2).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(guestDTOObj)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());

	}

	@Test
	void test1GetGuestByName() throws Exception {

		GuestDTO guestResObj = guestDTOObj();

		lenient().doReturn(guestResObj).when(guestService).getByName("user");

		mockMvc.perform(get("/api/v1/guests").param("guest_name", "user").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertNotNull(guestResObj);

	}

	@Test
	void test1GetGuestByguestId() throws Exception {

		GuestDTO guestDTOObj = guestDTOObj();

		lenient().doReturn(guestDTOObj).when(guestService).get(1L);

		mockMvc.perform(get("/api/v1/guests/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertNotNull(guestDTOObj);
		assertEquals(1, guestDTOObj.getId());

	}

}
