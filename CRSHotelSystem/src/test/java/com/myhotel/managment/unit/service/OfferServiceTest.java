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
import com.myhotel.managment.domain.Offer;
import com.myhotel.managment.dto.OfferDTO;
import com.myhotel.managment.repository.CategoryRepository;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.repository.OfferRepository;
import com.myhotel.managment.service.impl.OfferServiceImpl;

@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class OfferServiceTest {

	@InjectMocks
	private OfferServiceImpl offerService;

	@Mock
	private OfferRepository offerRepository;

	@Mock
	private HotelRepository hotelRepository;

	@Mock
	private CategoryRepository categoryRepository;

	private OfferDTO offerDtoObj() {
		OfferDTO offer = new OfferDTO();
		offer.setValue(1000.00);
		offer.setCategoryId(1L);
		offer.setHotelId(1L);
		offer.setId(1L);
		return offer;
	}

	private Offer offerObj() {
		Offer offer = new Offer();
		offer.setId(1L);
		offer.setCategory(Category.builder().id(1L).build());
		offer.setHotel(Hotel.builder().id(1L).build());
		offer.setValue(1000.00);
		return offer;
	}

	private Hotel hotelObj() {
		Hotel hotel = new Hotel();
		hotel.setId(1L);
		hotel.setAddress("Nagpur");
		hotel.setContact(9999999999L);
		return hotel;
	}

	private Category categoryObj() {
		Category category = new Category();
		category.setId(1L);
		category.setCharges(1000.00);
		category.setDescription("Single");
		return category;
	}

	@Test
	void test1AddOffer() {
		OfferDTO offerDTO = offerDtoObj();
		Offer offer = offerObj();

		doReturn(offer).when(offerRepository).save(Mockito.any(Offer.class));
		OfferDTO createdOffer = offerService.add(offerDTO);
		assertEquals(createdOffer.getValue(), offerDTO.getValue());
	}

	@Test
	void test2AddOffer() {
		OfferDTO offerDTO = offerDtoObj();
		Offer offer = offerObj();

		doReturn(offer).when(offerRepository).save(Mockito.any(Offer.class));
		OfferDTO createdOffer = offerService.add(offerDTO);
		assertNotNull(createdOffer);
	}

	@Test
	void test3UpdateOffer() {
		OfferDTO offerDTO = offerDtoObj();

		OfferDTO updatedOffer = offerService.update(offerDTO);
		assertEquals(updatedOffer.getValue(), offerDTO.getValue());
	}

	@Test
	void test3DeleteOffer() {

		Long deletedOfferId = offerService.delete(1L);
		assertEquals(1L, deletedOfferId);
	}

	@Test
	void test4getAllOffers() {

		Offer offer = offerObj();
		Hotel hotel = hotelObj();
		Category category = categoryObj();
		List<Offer> offers = new ArrayList<>();
		offers.add(offer);

		lenient().doReturn(Optional.of(offers)).when(offerRepository).findByHotelAndCategory(Mockito.any(Hotel.class),
				Mockito.any(Category.class));
		List<OfferDTO> offersRes = offerService.getAll(hotel.getId(), category.getId());

		assertEquals(hotel.getId(), offersRes.get(0).getHotelId());
		assertEquals(category.getId(), offersRes.get(0).getCategoryId());

	}

	@Test
	void test5getOffers() {

		Offer offer = offerObj();
		Hotel hotel = hotelObj();
		Category category = categoryObj();

		List<Offer> offers = new ArrayList<>();
		offers.add(offer);

		lenient().doReturn(Optional.of(offers)).when(offerRepository).findByHotelAndCategory(Mockito.any(Hotel.class),
				Mockito.any(Category.class));
		List<Offer> offersRes = offerService.getOffers(hotel.getId(), category.getId());

		assertEquals(hotel.getId(), offersRes.get(0).getHotel().getId());
		assertEquals(category.getId(), offersRes.get(0).getCategory().getId());

	}

	@Test
	void test6GetOffer() {

		Offer offer = offerObj();
		doReturn(Optional.of(offer)).when(offerRepository).findById(1L);
		Offer offerDb = offerService.getOffer(1L);
		assertEquals(1L, offerDb.getId());
	}

	@Test
	void test7GetHotel() {
		Hotel hotel = hotelObj();
		doReturn(Optional.of(hotel)).when(hotelRepository).findById(1L);
		Hotel hotelDb = offerService.getHotel(1L);
		assertEquals(1L, hotelDb.getId());
	}

	@Test
	void test8GetCategory() {
		Category category = categoryObj();
		doReturn(Optional.of(category)).when(categoryRepository).findById(1L);
		Category categoryDb = offerService.getCategory(1L);
		assertEquals(1L, categoryDb.getId());

	}

}
