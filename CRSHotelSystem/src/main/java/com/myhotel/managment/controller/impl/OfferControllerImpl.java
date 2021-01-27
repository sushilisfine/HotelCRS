package com.myhotel.managment.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.managment.controller.OfferController;
import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Offer;
import com.myhotel.managment.dto.OfferDTO;
import com.myhotel.managment.service.OfferService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller Class for performing operations on Offer entity.
 *
 * @author Sushil Yadav
 */
@RestController
@Slf4j
public class OfferControllerImpl implements OfferController {

	@Autowired
	private OfferService offerService;

	/**
	 * Creates a Offer for the hotel id provided. Use the returned instance for
	 * further operations on the clients end.
	 *
	 * @param Long     hotelId , for mapping category.
	 * @param OfferDTO , for saving.
	 * @return the saved ResponseEntity<OfferDTO>.
	 */
	@Override
	public ResponseEntity<OfferDTO> add(Long hotelId, OfferDTO offer) {

		offer.setHotelId(hotelId);

		try {
			if (validateHotelAndCategory(hotelId, offer.getCategoryId()))
				return new ResponseEntity<>(offerService.add(offer), HttpStatus.CREATED);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Creating new Offer failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updates a Offer for the hotel id and offer id provided. Use the returned
	 * instance for further operations on the clients end.
	 *
	 * @param Long hotelId , to validate hotel present.
	 * @param Long offerId , to validate offer present and then update it.
	 * @return the updated ResponseEntity<OfferDTO>.
	 */
	@Override
	public ResponseEntity<OfferDTO> update(Long hotelId, Long offerId, OfferDTO offer) {

		offer.setHotelId(hotelId);
		offer.setId(offerId);

		try {
			if (validateHotelAndCategoryAndOffer(hotelId, offer.getCategoryId(), offerId))
				return new ResponseEntity<>(offerService.update(offer), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Updating Offer failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get all Offers for the hotel id and category id provided. Use the returned
	 * instance for further operations on the clients end.
	 * 
	 * @param Long hotelId, Long categoryId, to validate offer present and fetch all
	 *             offers mapped.
	 * @return the ResponseEntity<List<OfferDTO>>.
	 */
	@Override
	public ResponseEntity<List<OfferDTO>> getAll(Long hotelId, Long categoryId) {

		try {
			if (validateHotelAndCategory(hotelId, categoryId))
				return new ResponseEntity<>(offerService.getAll(hotelId, categoryId), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Getting all Offer failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get a Offer for the hotel id and category id and offer id provided. Use the
	 * returned instance for further operations on the clients end.
	 *
	 * @param Long hotelId , to validate hotel present.
	 * @param Long categoryId , to validate category present.
	 * @param Long offerId , to validate offer present and then get offer by id.
	 * @return the ResponseEntity<OfferDTO>.
	 */
	@Override
	public ResponseEntity<OfferDTO> get(Long hotelId, Long offerlId, Long categoryId) {

		try {

			log.info("test sleuth2");
			if (validateHotelAndCategory(hotelId, categoryId))
				return new ResponseEntity<>(offerService.get(offerlId), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Getting Offer failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Delete a Offer for the hotel id , category id and offerId provided. Use the
	 * returned instance for further operations on the clients end.
	 *
	 * @param Long hotelId , to validate hotel present.
	 * @param Long categoryId , to validate category present.
	 * @param Long offerId , to validate offer present and then delete offer by id.
	 * @return the Long offer id for deleted entity.
	 */
	@Override
	public ResponseEntity<Long> delete(Long hotelId, Long categoryId, Long offerId) {

		try {
			if (validateHotelAndCategoryAndOffer(hotelId, categoryId, offerId))
				return new ResponseEntity<>(offerService.delete(offerId), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Deleting Offer failed");
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

		Hotel hotel = offerService.getHotel(hotelId);

		if (hotel.getId() == null) {
			log.info("Unable to find guest with id : {}", hotelId);
			return false;
		}
		return true;
	}

	/**
	 * Validate if the category is present.
	 * 
	 * @param Long categoryId, to category category.
	 * @return boolean.
	 */
	private boolean validateCategory(Long categoryId) {

		Category category = offerService.getCategory(categoryId);
		if (category.getId() == null) {
			log.info("Unable to find category with id : {} ", categoryId);
			return false;
		}
		return true;
	}

	/**
	 * Validate if the offer is present.
	 * 
	 * @param Long offerId, to validate offer.
	 * @return boolean.
	 */
	private boolean validateOffer(Long offerId) {
		Offer offer = offerService.getOffer(offerId);
		if (offer.getId() == null) {
			log.info("Unable to find offer with id : {} ", offerId);
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
		return validateHotel(hotelId) && validateCategory(categoryId);
	}

	/**
	 * Validate if the hotel and offer both are present.
	 * 
	 * @param categoryId, to category category.
	 * @param Long        offerId, to validate offer.
	 * @return boolean.
	 */
	private boolean validateHotelAndCategoryAndOffer(Long hotelId, Long categoryId, Long offerId) {
		return validateHotel(hotelId) && validateCategory(categoryId) && validateOffer(offerId);
	}

}
