package com.myhotel.managment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findAllByHotel(Hotel hotel);

	Optional<Category> findByIdAndHotel(Long categoryCode, Hotel hotel);

}