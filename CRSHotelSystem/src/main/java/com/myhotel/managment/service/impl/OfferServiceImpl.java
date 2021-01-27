package com.myhotel.managment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Offer;
import com.myhotel.managment.dto.OfferDTO;
import com.myhotel.managment.repository.CategoryRepository;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.repository.OfferRepository;
import com.myhotel.managment.service.OfferService;

/**
 * Service Class for performing operations on Offer entity.
 *
 * @author Sushil Yadav
 */
@Service
public class OfferServiceImpl implements OfferService {

	private OfferRepository offerRepository;
	private HotelRepository hotelRepository;
	private CategoryRepository categoryRepository;

	public OfferServiceImpl(OfferRepository offerRepository, HotelRepository hotelRepository,
			CategoryRepository categoryRepository) {
		this.offerRepository = offerRepository;
		this.hotelRepository = hotelRepository;
		this.categoryRepository = categoryRepository;
	}

	/**
	 * Creates a Offer.
	 *
	 * @param OfferDTO , for saving.
	 * @return the saved OfferDTO.
	 */
	@Override
	public OfferDTO add(OfferDTO offerDTO) {

		Offer offer = converteDTOToEntity(offerDTO);
		return converteEntityToDTO(offerRepository.save(offer));

	}

	/**
	 * Updates a Offer.
	 *
	 * @return the updated OfferDTO.
	 */
	@Override
	public OfferDTO update(OfferDTO offerDTO) {
		Offer offer = converteDTOToEntity(offerDTO);
		offerRepository.save(offer);
		return converteEntityToDTO(offer);
	}

	/**
	 * Delete a Offer for the offer id provided.
	 * 
	 * @param Long offerId delete offer by id.
	 * @return the Long offer id for deleted entity.
	 */
	@Override
	public Long delete(Long offerId) {
		offerRepository.deleteById(offerId);
		return offerId;
	}

	/**
	 * Get all Offers for the hotel id and category id provided.
	 * 
	 * @param Long hotelId, Long categoryId to fetch all offers mapped.
	 * @return the List<OfferDTO>.
	 */
	@Override
	public List<OfferDTO> getAll(Long hotelId, Long categoryId) {
		List<Offer> offer = getOffers(hotelId, categoryId);
		return converteEntityToDTO(offer);
	}

	/**
	 * Get a Offer for the offer id provided.
	 * 
	 * @param Long offerId , to get offer by id.
	 * @return the OfferDTO.
	 */
	@Override
	public OfferDTO get(Long offerlId) {
		return converteEntityToDTO(getOffer(offerlId));
	}

	/**
	 * Get a Offer for the offer id provided.
	 * 
	 * @param Long offerId , to get offer by id.
	 * @return the Offer.
	 */
	@Override
	public Offer getOffer(Long offerId) {
		Optional<Offer> offer = offerRepository.findById(offerId);
		return offer.isPresent() ? offer.get() : new Offer();
	}

	/**
	 * Convert OfferDTO Entity to Offer entity
	 * 
	 * @param OfferDTO , for converting.
	 * @return the converted Offer.
	 */
	private Offer converteDTOToEntity(OfferDTO offerDTO) {
		Hotel hotel = Hotel.builder().id(offerDTO.getHotelId()).build();
		Category category = Category.builder().hotel(hotel).id(offerDTO.getCategoryId()).build();
		return Offer.builder().id(offerDTO.getId()).value(offerDTO.getValue()).hotel(hotel).category(category).build();
	}

	/**
	 * Convert Offer Entity to OfferDTO entity
	 * 
	 * @param Offer , for converting.
	 * @return the converted OfferDTO.
	 */
	private OfferDTO converteEntityToDTO(Offer offer) {
		return OfferDTO.builder().id(offer.getId()).value(offer.getValue()).categoryId(offer.getCategory().getId())
				.hotelId(offer.getHotel().getId()).build();
	}

	/**
	 * Convert List<Offer> Entity to List<OfferDTO> entity
	 * 
	 * @param List<Offer> , for converting.
	 * @return the converted List<OfferDTO>.
	 */
	private List<OfferDTO> converteEntityToDTO(List<Offer> offers) {
		List<OfferDTO> offerDTO = new ArrayList<>();
		offers.forEach(offer -> offerDTO.add(converteEntityToDTO(offer)));
		return offerDTO;
	}

	/**
	 * Responsible to get the Hotel entity from repository by the hotelId provided
	 * 
	 * @param Long hotelId to fetch Hotel entity.
	 * @return the Hotel entity fetched.
	 */
	public Hotel getHotel(Long hotelId) {
		Optional<Hotel> hotel = hotelRepository.findById(hotelId);
		return hotel.isPresent() ? hotel.get() : new Hotel();

	}

	/**
	 * Get all Offers for the hotel id and category id provided.
	 * 
	 * @param Long hotelId, Long categoryId to fetch all offers mapped.
	 * @return the List<Offer>.
	 */
	public List<Offer> getOffers(Long hotelId, Long categoryId) {
		Hotel hotel = Hotel.builder().id(hotelId).build();
		Category category = Category.builder().hotel(hotel).id(categoryId).build();
		Optional<List<Offer>> offer = offerRepository.findByHotelAndCategory(hotel, category);
		return offer.isPresent() ? offer.get() : new ArrayList<>();
	}

	/**
	 * Responsible to get the Category entity from repository by the categoryId
	 * provided
	 * 
	 * @param Long categoryId to fetch Category entity.
	 * @return the Category entity fetched.
	 */
	public Category getCategory(Long categoryId) {
		Optional<Category> category = categoryRepository.findById(categoryId);
		return category.isPresent() ? category.get() : new Category();
	}

}
