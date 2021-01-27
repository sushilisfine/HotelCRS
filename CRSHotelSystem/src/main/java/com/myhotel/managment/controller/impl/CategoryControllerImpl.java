package com.myhotel.managment.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.managment.constants.HotelConstants;
import com.myhotel.managment.controller.CategoryController;
import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.CategoryDTO;
import com.myhotel.managment.service.CategoryService;
import com.myhotel.managment.util.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller Class for performing operations on Category entity.
 *
 * @author Sushil Yadav
 */
@RestController
@Slf4j
public class CategoryControllerImpl implements CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * Creates a Category for the hotel id provided. Use the returned instance for
	 * further operations on the clients end.
	 *
	 * @param Long        hotelId , for mapping category.
	 * @param CategoryDTO , for saving.
	 * @return the saved ResponseEntity<CategoryDTO>.
	 */
	@Override
	public ResponseEntity<Response<Object>> add(Long hotelId, CategoryDTO category) {

		category.setHotelId(hotelId);

		try {
			if (validateHotel(hotelId))
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(Response.builder().data(categoryService.add(category)).message(HotelConstants.SUCCESS)
								.status(HttpStatus.CREATED).build());
			else
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder().data(null)
						.message("Hotel not found").status(HttpStatus.BAD_REQUEST).build());

		} catch (RuntimeException e) {
			log.info("Creating new Category failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Updates a Category for the hotel id and category id provided. Use the
	 * returned instance for further operations on the clients end.
	 *
	 * @param Long        hotelId , to validate hotel present.
	 * @param Long        categoryId , to validate category present and then update
	 *                    it.
	 * @param CategoryDTO , for updating.
	 * @return the updated ResponseEntity<CategoryDTO>.
	 */
	@Override
	public ResponseEntity<Response<Object>> update(Long hotelId, Long categoryId, CategoryDTO category) {

		category.setHotelId(hotelId);
		category.setId(categoryId);

		try {

			if (validateHotelAndCategory(hotelId, categoryId))
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(Response.builder().data(categoryService.update(category)).message(HotelConstants.SUCCESS)
								.status(HttpStatus.OK).build());
			else
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder().data(null)
						.message("Hotel or category not found").status(HttpStatus.BAD_REQUEST).build());

		} catch (RuntimeException e) {
			log.info("Updating Category failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Get a Category for the hotel id and categoryId provided. Use the returned
	 * instance for further operations on the clients end.
	 *
	 * @param Long hotelId , to validate hotel present.
	 * @param Long categoryId , to validate category present and then get category
	 *             by id.
	 * @return the ResponseEntity<CategoryDTO>.
	 */
	@Override
	public ResponseEntity<Response<Object>> get(Long hotelId, Long categoryId) {

		try {

			if (validateHotelAndCategory(hotelId, categoryId))
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(Response.builder().data(categoryService.get(categoryId)).message(HotelConstants.SUCCESS)
								.status(HttpStatus.OK).build());
			else
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder().data(null)
						.message("Hotel or Category not found").status(HttpStatus.BAD_REQUEST).build());

		} catch (RuntimeException e) {
			log.info("Getting Categoriy failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Get all Categories for the hotel id provided. Use the returned instance for
	 * further operations on the clients end.
	 * 
	 * @param Long hotelId, to validate hotel present and fetch all categories
	 *             mapped.
	 * @return the ResponseEntity<List<CategoryDTO>>.
	 */
	@Override
	public ResponseEntity<Response<Object>> getAll(Long hotelId) {

		try {
			if (validateHotel(hotelId))

				return ResponseEntity.status(HttpStatus.CREATED)
						.body(Response.builder().data(categoryService.getAll(hotelId)).message(HotelConstants.SUCCESS)
								.status(HttpStatus.OK).build());
			else
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder().data(null)
						.message("Hotel not found").status(HttpStatus.BAD_REQUEST).build());

		} catch (RuntimeException e) {
			log.info("Getting All Categories failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Validate if the hotel is present.
	 * 
	 * @param Long hotelId, to validate hotel.
	 * @return boolean.
	 */
	private boolean validateHotel(Long hotelId) {

		Hotel hotel = categoryService.getHotel(hotelId);

		if (hotel.getId() == null) {
			log.info("Unable to find hotel with id : {}", hotelId);
			return false;
		}
		return true;
	}

	/**
	 * Validate if the category is present.
	 * 
	 * @param Long categoryId, to validate category.
	 * @return boolean.
	 */
	private boolean validateCategory(Long categoryId) {
		Category category = categoryService.getCategory(categoryId);
		if (category.getId() == null) {
			log.info("Unable to find category with id : {} ", categoryId);
			return false;
		}
		return true;
	}

	/**
	 * Validate if the hotel and category both are present.
	 * 
	 * @param categoryId, to category category.
	 * @param Long        hotelId, to validate hotel.
	 * @return boolean.
	 */
	private boolean validateHotelAndCategory(Long hotelId, Long categoryId) {
		return (validateHotel(hotelId) && validateCategory(categoryId));
	}

}
