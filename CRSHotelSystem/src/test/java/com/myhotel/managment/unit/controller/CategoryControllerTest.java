package com.myhotel.managment.unit.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
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

import com.myhotel.managment.controller.impl.CategoryControllerImpl;
import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.CategoryDTO;
import com.myhotel.managment.service.CategoryService;

class CategoryControllerTest extends AbstractTest {

	private MockMvc mockMvc;

	@Mock
	private CategoryService categoryService;

	@InjectMocks
	private CategoryControllerImpl categoryController;

	@BeforeEach
	public void setup() {

		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	private Hotel getHotelObj() {
		Hotel hotel = new Hotel();
		hotel.setId(1L);
		hotel.setAddress("Nagpur");
		hotel.setContact(9999999999L);
		return hotel;
	}

	private CategoryDTO getCategoryDtoObj() {
		CategoryDTO category = new CategoryDTO();
		category.setId(1L);
		category.setCharges(1000.00);
		category.setDescription("Single");
		return category;
	}

	private Category getCategoryObj() {
		Category category = new Category();
		category.setId(1L);
		category.setCharges(1000.00);
		category.setDescription("Single");
		return category;
	}

	@Test
	void test1CreateCategory1() throws Exception {
		CategoryDTO categoryDTO = getCategoryDtoObj();

		lenient().when(categoryService.add(categoryDTO)).thenReturn(categoryDTO);
		lenient().when(categoryService.getHotel(1L)).thenReturn(getHotelObj());

		mockMvc.perform(post("/api/v1/hotels/{hotel_id}/categories", 1).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(categoryDTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		assertNotNull(categoryDTO);
	}

	@Test
	void test1CreateCategory2() throws Exception {
		CategoryDTO categoryDTO = getCategoryDtoObj();

		lenient().when(categoryService.getHotel(2L)).thenReturn(new Hotel());
		lenient().when(categoryService.add(categoryDTO)).thenReturn(categoryDTO);

		mockMvc.perform(post("/api/v1/hotels/{hotel_id}/categories", 2).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(categoryDTO)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	void test1CreateCategory3() throws Exception {
		CategoryDTO categoryDTO = getCategoryDtoObj();

		mockMvc.perform(post("/api/v1/hotels/{hotel_id}/categories", 2).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(categoryDTO)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());

	}

	@Test
	void test3UpdateCategory1() throws Exception {

		CategoryDTO categoryDTO = getCategoryDtoObj();

		lenient().when(categoryService.getHotel(1L)).thenReturn(getHotelObj());
		lenient().when(categoryService.getCategory(1L)).thenReturn(getCategoryObj());

		lenient().when(categoryService.update(categoryDTO)).thenReturn(categoryDTO);

		mockMvc.perform(
				put("/api/v1/hotels/{hotel_id}/categories/{category_id}", 1, 1).contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(categoryDTO)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertNotNull(categoryDTO);
	}

	@Test
	void test3UpdateCategory2() throws Exception {

		CategoryDTO categoryDTO = getCategoryDtoObj();

		lenient().when(categoryService.getHotel(1L)).thenReturn(getHotelObj());
		lenient().when(categoryService.getCategory(2L)).thenReturn(new Category());

		lenient().when(categoryService.update(categoryDTO)).thenReturn(categoryDTO);

		mockMvc.perform(
				put("/api/v1/hotels/{hotel_id}/categories/{category_id}", 1, 2).contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(categoryDTO)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		assertNotNull(categoryDTO);
	}

	@Test
	void test3UpdateCategory3() throws Exception {

		CategoryDTO categoryDTO = getCategoryDtoObj();

		mockMvc.perform(
				put("/api/v1/hotels/{hotel_id}/categories/{category_id}", 1, 1).contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(categoryDTO)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());

	}

	@Test
	void test1GetAllCategories1() throws Exception {

		List<CategoryDTO> categoryDTO = new ArrayList<>();
		categoryDTO.add(getCategoryDtoObj());

		lenient().when(categoryService.getHotel(1L)).thenReturn(getHotelObj());

		lenient().when(categoryService.getAll(1L)).thenReturn(categoryDTO);

		mockMvc.perform(get("/api/v1/hotels/{hotel_id}/categories", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertNotNull(categoryDTO);
	}

	@Test
	void test1GetAllCategories2() throws Exception {

		List<CategoryDTO> categoryDTO = new ArrayList<>();
		categoryDTO.add(getCategoryDtoObj());

		lenient().when(categoryService.getHotel(2L)).thenReturn(new Hotel());

		lenient().when(categoryService.getAll(1L)).thenReturn(categoryDTO);

		mockMvc.perform(get("/api/v1/hotels/{hotel_id}/categories", 2).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void test1GetAllCategories3() throws Exception {

		List<CategoryDTO> categoryDTO = new ArrayList<>();
		categoryDTO.add(getCategoryDtoObj());

		mockMvc.perform(get("/api/v1/hotels/{hotel_id}/categories", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());

	}
}
