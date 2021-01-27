package com.myhotel.managment.service;

import java.util.List;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Offer;
import com.myhotel.managment.dto.OfferDTO;

public interface OfferService {

	OfferDTO add(OfferDTO offer);

	OfferDTO update(OfferDTO offer);

	Long delete(Long offerId);

	List<OfferDTO> getAll(Long hotelId, Long categoryId);

	Hotel getHotel(Long hotelId);

	Category getCategory(Long categoryId);

	Offer getOffer(Long offerId);

	/**
	 * @param offerlId
	 * @returnObject
	 */
	OfferDTO get(Long offerlId);

}
