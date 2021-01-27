package com.myhotel.managment.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;

import java.time.LocalDate;
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
import com.myhotel.managment.domain.Room;
import com.myhotel.managment.dto.RoomDTO;
import com.myhotel.managment.repository.CategoryRepository;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.repository.RoomRepository;
import com.myhotel.managment.service.impl.RoomServiceImpl;

@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class RoomServiceTest {

	@InjectMocks
	private RoomServiceImpl roomService;

	@Mock
	private RoomRepository roomRepository;

	@Mock
	private HotelRepository hotelRepository;

	@Mock
	private CategoryRepository categoryRepository;

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

	private RoomDTO roomDtoObj() {
		RoomDTO room = new RoomDTO();
		room.setCategoryId(1L);
		room.setHotelId(1L);
		room.setId(1L);
		List<LocalDate> bookingDates = new ArrayList<>();

		bookingDates.add(LocalDate.of(2021, 2, 12));
		bookingDates.add(LocalDate.of(2021, 2, 13));
		bookingDates.add(LocalDate.of(2021, 2, 14));
		bookingDates.add(LocalDate.of(2021, 2, 3));

		room.setBookedDates(bookingDates);
		return room;
	}

	private Room roomObj() {
		Room room = new Room();
		room.setId(1L);
		room.setCategory(Category.builder().id(1L).build());
		room.setHotel(Hotel.builder().id(1L).build());

		List<LocalDate> bookingDates = new ArrayList<>();

		bookingDates.add(LocalDate.of(2021, 2, 12));
		bookingDates.add(LocalDate.of(2021, 2, 13));
		bookingDates.add(LocalDate.of(2021, 2, 14));
		bookingDates.add(LocalDate.of(2021, 2, 3));

		room.setBookedDates(bookingDates);
		return room;
	}

	private Room roomObj2() {
		Room room = new Room();
		room.setId(2L);
		room.setCategory(Category.builder().id(1L).build());
		room.setHotel(Hotel.builder().id(1L).build());

		List<LocalDate> bookingDates = new ArrayList<>();

		bookingDates.add(LocalDate.of(2021, 2, 1));
		bookingDates.add(LocalDate.of(2021, 2, 3));
		bookingDates.add(LocalDate.of(2021, 2, 16));

		room.setBookedDates(bookingDates);
		return room;
	}

	@Test
	void test1AddRoom() {
		RoomDTO roomCreateRequestDTO = roomDtoObj();
		Room room = roomObj();

		doReturn(room).when(roomRepository).save(Mockito.any(Room.class));

		RoomDTO createdRoom = roomService.add(roomCreateRequestDTO);

		assertNotNull(createdRoom);
	}

	@Test
	void test2AddRoom() {
		RoomDTO roomCreateRequestDTO = roomDtoObj();
		Room room = roomObj();

		lenient().doReturn(room).when(roomRepository).save(Mockito.any(Room.class));

		RoomDTO createdRoom = roomService.add(roomCreateRequestDTO);

		assertNotNull(createdRoom);
	}

	@Test
	void test3UpdateRoom() {
		RoomDTO roomDTO = roomDtoObj();
		Room room = roomObj();

		room.getBookedDates().addAll(roomDTO.getBookedDates());

		lenient().doReturn(Optional.of(room)).when(roomRepository).findById(1L);

		doReturn(room).when(roomRepository).save(Mockito.any(Room.class));

		RoomDTO updatedRoom = roomService.update(roomDTO);

		assertEquals(room.getBookedDates(), updatedRoom.getBookedDates());
	}

	@Test
	void test3GetAllRooms() {
		List<Room> roomResDTO = new ArrayList<>();
		roomResDTO.add(roomObj());

		List<RoomDTO> roomResponse = roomService.getAll(1L);

		assertNotNull(roomResponse);

	}

	@Test
	void testGetAllRoomsWithParams1() {

		List<Room> roomObj = new ArrayList<>();
		roomObj.add(roomObj());
		roomObj.add(roomObj2());

		lenient().doReturn(Optional.of(roomObj)).when(roomRepository)
				.findAllByHotelAndCategory(Mockito.any(Hotel.class), Mockito.any(Category.class));

		List<RoomDTO> roomResponse = roomService.getByParams(1L, LocalDate.parse("2021-02-04"),
				LocalDate.parse("2021-02-11"), 1L);

		assertNotNull(roomResponse);
		assertEquals(2, roomResponse.size());

	}

	@Test
	void testGetAllRoomsWithParams2() {

		List<Room> roomObj = new ArrayList<>();
		roomObj.add(roomObj());
		roomObj.add(roomObj2());

		lenient().doReturn(Optional.of(roomObj)).when(roomRepository)
				.findAllByHotelAndCategory(Mockito.any(Hotel.class), Mockito.any(Category.class));

		List<RoomDTO> roomResponse = roomService.getByParams(1L, LocalDate.parse("2021-02-01"),
				LocalDate.parse("2021-02-05"), 1L);

		assertNotNull(roomResponse);
		assertEquals(0, roomResponse.size());

	}

	@Test
	void testGetAllRoomsWithParams3() {

		List<Room> roomObj = new ArrayList<>();
		roomObj.add(roomObj());
		roomObj.add(roomObj2());

		lenient().doReturn(Optional.of(roomObj)).when(roomRepository)
				.findAllByHotelAndCategory(Mockito.any(Hotel.class), Mockito.any(Category.class));

		List<RoomDTO> roomResponse = roomService.getByParams(1l, LocalDate.parse("2021-02-12"),
				LocalDate.parse("2021-02-14"), 1L);

		assertNotNull(roomResponse);
		assertEquals(1, roomResponse.size());
		assertEquals(2, roomResponse.get(0).getId());

	}

	@Test
	void testGetHotel() {
		Hotel hotel = hotelObj();
		doReturn(Optional.of(hotel)).when(hotelRepository).findById(1L);
		Hotel hotelDb = roomService.getHotel(1L);
		assertEquals(1L, hotelDb.getId());
	}

	@Test
	void testGetCategory() {
		Category category = categoryObj();
		doReturn(Optional.of(category)).when(categoryRepository).findById(1L);
		Category categoryDb = roomService.getCategory(1L);
		assertEquals(1L, categoryDb.getId());

	}

}
