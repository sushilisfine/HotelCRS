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

import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.HotelDTO;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.service.impl.HotelServiceImpl;

@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class HotelServiceTest {

	@InjectMocks
	private HotelServiceImpl hotelService;

	@Mock
	private HotelRepository hotelRepository;

	private Hotel hotelObj() {
		Hotel hotel = new Hotel();
		hotel.setId(1L);
		hotel.setAddress("Nagpur");
		hotel.setContact(9999999999L);
		return hotel;
	}

	private HotelDTO hotelDTOObj() {
		HotelDTO hotelDTO = new HotelDTO();
		hotelDTO.setAddress("Nagpur");
		hotelDTO.setContact(9999999999L);
		return hotelDTO;
	}

	@Test
	void test1AddHotel1() {
		Hotel hotelObjDb = hotelObj();
		HotelDTO hotelDTO = hotelDTOObj();

		lenient().doReturn(hotelObjDb).when(hotelRepository).save(Mockito.any(Hotel.class));
		HotelDTO createdHotel = hotelService.create(hotelDTO);

		assertEquals(hotelDTO.getAddress(), createdHotel.getAddress());
		assertEquals(hotelDTO.getContact(), createdHotel.getContact());
		assertNotNull(createdHotel.getId());
	}

	@Test
	void test1AddHotel2() {
		Hotel hotelObj = hotelObj();
		HotelDTO hotelDTO = hotelDTOObj();

		lenient().doReturn(hotelObj).when(hotelRepository).save(Mockito.any(Hotel.class));
		HotelDTO createdHotel = hotelService.create(hotelDTO);

		assertNotNull(createdHotel);
	}

	@Test
	void test2UpdateHotel() {
		Hotel hotel = hotelObj();
		HotelDTO hotelDTO = hotelDTOObj();
		hotelDTO.setId(1L);

		lenient().doReturn(Optional.of(hotel)).when(hotelRepository).findById(1L);
		lenient().doReturn(hotel).when(hotelRepository).save(Mockito.any(Hotel.class));
		HotelDTO updatedHotel = hotelService.update(hotelDTO);

		assertEquals(hotelDTO.getAddress(), updatedHotel.getAddress());
		assertEquals(hotelDTO.getContact(), updatedHotel.getContact());
		assertNotNull(updatedHotel.getId());
	}

	@Test
	void test3GetAllHotels() {
		List<HotelDTO> hotelResponseDTO = new ArrayList<>();
		hotelResponseDTO.add(hotelDTOObj());

		List<Hotel> hotelObj = new ArrayList<>();
		hotelObj.add(hotelObj());
		lenient().doReturn(hotelObj).when(hotelRepository).findAll();
		List<HotelDTO> createdHotel = hotelService.getAll();

		assertNotNull(createdHotel.get(0).getAddress());
		assertNotNull(createdHotel.get(0).getContact());
		assertNotNull(createdHotel.get(0).getId());
	}

	@Test
	void test4getHotel() {
		Hotel hotel = hotelObj();
		doReturn(Optional.of(hotel)).when(hotelRepository).findById(1L);
		Hotel hotelDb = hotelService.get(1L);
		assertEquals(1L, hotelDb.getId());
	}
}
