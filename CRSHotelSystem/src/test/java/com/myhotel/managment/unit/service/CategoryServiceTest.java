package com.myhotel.managment.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.CategoryDTO;
import com.myhotel.managment.repository.CategoryRepository;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class CategoryServiceTest {

	@InjectMocks
	private CategoryServiceImpl categoryService;

	@Mock
	private HotelRepository hotelRepository;

	@Mock
	private CategoryRepository categoryRepository;

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
		category.setHotel(Hotel.builder().id(1L).build());
		return category;
	}

	@Test
	void test1AddCategory() {
		CategoryDTO categoryDTO = getCategoryDtoObj();
		Category category = getCategoryObj();

		doReturn(category).when(categoryRepository).save(Mockito.any(Category.class));
		CategoryDTO createdCategory = categoryService.add(categoryDTO);
		assertNotNull(createdCategory);
	}

	@Test
	void test2AddCategory() {
		CategoryDTO categoryDTO = getCategoryDtoObj();
		Category category = getCategoryObj();

		lenient().doReturn(category).when(categoryRepository).save(Mockito.any(Category.class));

		CategoryDTO createdCategory = categoryService.add(categoryDTO);

		assertNotNull(createdCategory);
	}

	@Test
	void test3UpdateCategory() {
		CategoryDTO categoryDTO = getCategoryDtoObj();
		Category category = getCategoryObj();
		doReturn(category).when(categoryRepository).save(Mockito.any(Category.class));
		CategoryDTO updatedCategory = categoryService.update(categoryDTO);
		assertNotNull(updatedCategory);
	}

	@Test
	void test3GetAllCategory() {

		List<Category> categoryList = new ArrayList<>();
		Category category = getCategoryObj();
		categoryList.add(category);

		doReturn(categoryList).when(categoryRepository).findAllByHotel(Mockito.any(Hotel.class));

		List<CategoryDTO> categoryResponse = categoryService.getAll(1L);
		assertNotNull(categoryResponse);

	}

	@Test
	void testGetHotel() {
		Hotel hotel = getHotelObj();
		doReturn(Optional.of(hotel)).when(hotelRepository).findById(1L);
		Hotel hotelDb = categoryService.getHotel(1L);
		assertEquals(1L, hotelDb.getId());
	}

	@Test
	void testGetCategory() {
		Category category = getCategoryObj();
		doReturn(Optional.of(category)).when(categoryRepository).findById(1L);
		Category categoryDb = categoryService.getCategory(1L);
		assertEquals(1L, categoryDb.getId());

	}

}
