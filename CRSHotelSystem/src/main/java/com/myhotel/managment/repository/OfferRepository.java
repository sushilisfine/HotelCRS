package com.myhotel.managment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

	Optional<List<Offer>> findByHotelAndCategory(Hotel hotel, Category category);

	Optional<Offer> findByIdAndHotelAndCategory(Long offerId, Hotel hotel, Category category);
}