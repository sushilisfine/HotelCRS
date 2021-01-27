package com.myhotel.managment.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.myhotel.managment.controller.impl.OfferControllerImpl;
import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Offer;
import com.myhotel.managment.dto.OfferDTO;
import com.myhotel.managment.service.OfferService;

class OffersControllerTest extends AbstractTest {

	private MockMvc mockMvc;

	@Mock
	private OfferService offerService;

	@InjectMocks
	private OfferControllerImpl offerController;

	@BeforeEach
	public void setup() {

		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(offerController).build();
	}

	private OfferDTO getOfferDtoObj() {
		OfferDTO offerDTO = new OfferDTO();
		offerDTO.setCategoryId(1L);
		offerDTO.setHotelId(1L);
		offerDTO.setValue(1000.00);
		return offerDTO;
	}

	private Offer getOfferObj() {
		Offer offer = new Offer();
		offer.setId(1L);
		offer.setValue(1000.00);
		return offer;
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
	void test1CreateOffer() throws Exception {
		OfferDTO offerDTO = getOfferDtoObj();

		lenient().when(offerService.add(offerDTO)).thenReturn(offerDTO);

		lenient().when(offerService.getHotel(1L)).thenReturn(getHotelObj());
		lenient().when(offerService.getCategory(1L)).thenReturn(getCategoryObj());

		mockMvc.perform(post("/api/v1/hotels/{hotel_id}/offers", 1).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(offerDTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		assertEquals(offerDTO.getValue(), offerDTO.getValue());
	}

	@Test
	void test3UpdateOffer() throws Exception {

		OfferDTO offerDTO = getOfferDtoObj();

		lenient().when(offerService.getHotel(1L)).thenReturn(getHotelObj());
		lenient().when(offerService.getCategory(1L)).thenReturn(getCategoryObj());
		lenient().when(offerService.getOffer(1L)).thenReturn(getOfferObj());

		lenient().when(offerService.update(offerDTO)).thenReturn(offerDTO);

		mockMvc.perform(put("/api/v1/hotels/{hotel_id}/offers/{offer_id}", 1, 1).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(offerDTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertEquals(offerDTO.getValue(), offerDTO.getValue());
	}

	@Test
	void test1GetAllOffers() throws Exception {

		List<OfferDTO> offerDTOObj = new ArrayList<>();
		offerDTOObj.add(getOfferDtoObj());

		lenient().when(offerService.getHotel(1L)).thenReturn(getHotelObj());
		lenient().when(offerService.getCategory(1L)).thenReturn(getCategoryObj());
		lenient().when(offerService.getAll(1L, 1L)).thenReturn(offerDTOObj);

		mockMvc.perform(
				get("/api/v1/hotels/{hotel_id}/offers", 1).param("category_id", "1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertNotNull(offerDTOObj);
	}

	@Test
	void test4DeleteOffer() throws Exception {

		lenient().when(offerService.getHotel(1L)).thenReturn(getHotelObj());
		lenient().when(offerService.getCategory(1L)).thenReturn(getCategoryObj());
		lenient().when(offerService.getOffer(1L)).thenReturn(getOfferObj());

		lenient().when(offerService.delete(1L)).thenReturn(1L);

		mockMvc.perform(delete("/api/v1/hotels/{hotel_id}/offers/{offer_id}", 1, 1).param("category_id", "1")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}
}
