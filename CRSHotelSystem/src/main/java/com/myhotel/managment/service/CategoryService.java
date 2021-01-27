package com.myhotel.managment.service;

import java.util.List;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.CategoryDTO;

public interface CategoryService {

	CategoryDTO add(CategoryDTO category);

	CategoryDTO update(CategoryDTO category);

	List<CategoryDTO> getAll(Long hotelId);

	Hotel getHotel(Long hotellId);

	Category getCategory(Long categoryId);

	CategoryDTO get(Long categoryId);

}
