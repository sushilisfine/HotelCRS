package com.myhotel.managment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.CategoryDTO;
import com.myhotel.managment.repository.CategoryRepository;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.service.CategoryService;

/**
 * Service implementation Class for performing operations on Category entity.
 *
 * @author Sushil Yadav
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;
	private HotelRepository hotelRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository, HotelRepository hotelRepository) {
		this.categoryRepository = categoryRepository;
		this.hotelRepository = hotelRepository;
	}

	/**
	 * Creates a Category
	 *
	 * @param CategoryDTO , for saving.
	 * @return the saved CategoryDTO.
	 */
	@Override
	public CategoryDTO add(CategoryDTO categoryDTO) {
		Category category = convertDTOToEntity(categoryDTO);
		return convertEntityToDTO(categoryRepository.save(category));
	}

	/**
	 * Updates a Category
	 * 
	 * @param CategoryDTO , for updating.
	 * @return the updated CategoryDTO.
	 */
	@Override
	public CategoryDTO update(CategoryDTO categoryDTO) {

		Category category = convertDTOToEntity(categoryDTO);
		categoryRepository.save(category);
		return convertEntityToDTO(category);
	}

	/**
	 * Get all Categories for the hotel id provided.
	 * 
	 * @param Long hotelId, to fetch all categories mapped.
	 * @return the list of CategoryDTO.
	 */
	@Override
	public List<CategoryDTO> getAll(Long hotelId) {

		Hotel hotel = Hotel.builder().id(hotelId).build();
		List<Category> categories = categoryRepository.findAllByHotel(hotel);
		return convertEntityToDTO(categories);
	}

	/**
	 * Get a Category for the categoryId provided.
	 * 
	 * @param Long categoryId , to get category by id.
	 * @return the CategoryDTO.
	 */
	@Override
	public CategoryDTO get(Long categoryId) {
		return convertEntityToDTO(getCategory(categoryId));
	}

	/**
	 * Get Hotel by hotelId provided.
	 * 
	 * @param Long hotelId.
	 * @return Hotel entity.
	 */
	@Override
	public Hotel getHotel(Long hotellId) {

		Optional<Hotel> hotel = hotelRepository.findById(hotellId);
		return hotel.isPresent() ? hotel.get() : new Hotel();
	}

	/**
	 * Get Category by categoryId provided.
	 * 
	 * @param Long categoryId.
	 * @return Category entity.
	 */
	@Override
	public Category getCategory(Long categoryId) {

		Optional<Category> category = categoryRepository.findById(categoryId);
		return category.isPresent() ? category.get() : new Category();
	}

	private Category convertDTOToEntity(CategoryDTO categoryDTO) {

		return Category.builder().id(categoryDTO.getId()).description(categoryDTO.getDescription())
				.charges(categoryDTO.getCharges()).hotel(Hotel.builder().id(categoryDTO.getHotelId()).build()).build();

	}

	/**
	 * Convert Category Entity to CategoryDTO entity
	 * 
	 * @param Category , for converting.
	 * @return the converted CategoryDTO.
	 */
	private CategoryDTO convertEntityToDTO(Category category) {

		return CategoryDTO.builder().id(category.getId()).description(category.getDescription())
				.charges(category.getCharges()).hotelId(category.getHotel().getId()).build();

	}

	/**
	 * Convert List<Category> Entity to List<CategoryDTO> entity
	 * 
	 * @param List<Category> , for converting.
	 * @return the converted List<CategoryDTO>.
	 */
	private List<CategoryDTO> convertEntityToDTO(List<Category> categories) {

		List<CategoryDTO> categoryDTO = new ArrayList<>();
		categories.forEach(category -> categoryDTO.add(convertEntityToDTO(category)));
		return categoryDTO;
	}

}
