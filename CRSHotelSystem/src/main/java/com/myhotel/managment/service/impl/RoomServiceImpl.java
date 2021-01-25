package com.myhotel.managment.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Room;
import com.myhotel.managment.dto.RoomDTO;
import com.myhotel.managment.repository.CategoryRepository;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.repository.RoomRepository;
import com.myhotel.managment.service.RoomService;

/**
 * Service Class for performing operations on Room entity.
 *
 * @author Sushil Yadav
 */
@Service
public class RoomServiceImpl implements RoomService {

	private RoomRepository roomRepository;
	private HotelRepository hotelRepository;
	private CategoryRepository categoryRepository;

	public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository,
			CategoryRepository categoryRepository) {
		this.roomRepository = roomRepository;
		this.hotelRepository = hotelRepository;
		this.categoryRepository = categoryRepository;
	}

	/**
	 * Creates a Room.
	 *
	 * @param RoomDTO , for saving.
	 * @return the saved RoomDTO.
	 */
	@Override
	public RoomDTO add(RoomDTO roomDTO) {
		Room room = converteDTOToEntity(roomDTO);
		return convertEntityToDTO(roomRepository.save(room));
	}

	/**
	 * Updates a Room.
	 * 
	 * @param RoomDTO , for updating.
	 * @return the updated roomDTO.
	 */
	@Override
	public RoomDTO update(RoomDTO roomDTO) {
		addBookingDates(roomDTO);
		Room room = converteDTOToEntity(roomDTO);
		return convertEntityToDTO(roomRepository.save(room));
	}

	// Adding new booking dates
	private RoomDTO addBookingDates(RoomDTO room) {
		if (room.getBookedDates() != null) {
			Room roomDb = get(room.getId());
			room.getBookedDates().addAll(roomDb.getBookedDates());
		}
		return room;
	}

	/**
	 * Get all Rooms for the hotel id provided.
	 * 
	 * @param Long hotelId to fetch all rooms mapped.
	 * @return the List<RoomDTO>.
	 */
	@Override
	public List<RoomDTO> getAll(Long hotelId) {
		Hotel hotel = Hotel.builder().id(hotelId).build();
		Optional<List<Room>> rooms = roomRepository.findAllByHotel(hotel);
		List<Room> roomsList = rooms.isPresent() ? rooms.get() : new ArrayList<>();
		return convertEntityToDTO(roomsList);
	}

	private Room converteDTOToEntity(RoomDTO roomDTO) {
		Hotel hotel = Hotel.builder().id(roomDTO.getHotelId()).build();
		Category category = Category.builder().hotel(hotel).id(roomDTO.getCategoryId()).build();
		return Room.builder().id(roomDTO.getId()).hotel(hotel).category(category).bookedDates(roomDTO.getBookedDates())
				.build();
	}

	private RoomDTO convertEntityToDTO(Room room) {
		return RoomDTO.builder().id(room.getId()).categoryId(room.getCategory().getId())
				.hotelId(room.getHotel().getId()).bookedDates(room.getBookedDates()).build();
	}

	private List<RoomDTO> convertEntityToDTO(List<Room> rooms) {
		List<RoomDTO> roomCreateResponseDTO = new ArrayList<>();
		rooms.forEach(room -> roomCreateResponseDTO.add(convertEntityToDTO(room)));
		return roomCreateResponseDTO;
	}

	/**
	 * Get available rooms for the hotel id ,category id and the dates provided.
	 * 
	 * @param Long hotelId.
	 * @parm LocalDate from, LocalDate to for the date range.
	 * @param Long categoryId.
	 * @return the List<RoomDTO>.
	 */
	@Override
	public List<RoomDTO> getByParams(Long hotelId, LocalDate from, LocalDate to, Long categoryId) {
		List<Room> rooms = get(hotelId, categoryId);
		List<Room> availableRooms = filterAvailableRooms(rooms, from, to);
		return convertEntityToDTO(availableRooms);
	}

	private List<Room> filterAvailableRooms(List<Room> rooms, LocalDate from, LocalDate to) {

		List<LocalDate> requestedDates = getListOfDates(from, to);

		rooms = rooms.stream().filter(room -> {
			List<LocalDate> clonedRequestedDates = new ArrayList<>(requestedDates);
			clonedRequestedDates.removeAll(room.getBookedDates());
			return requestedDates.size() == clonedRequestedDates.size();
		}).collect(Collectors.toList());

		return rooms;
	}

	/**
	 * Responsible to fetch List<LocalDate> from the to date range provided.
	 * 
	 * @param LocalDate from
	 * @param LocalDate to.
	 * @return List<LocalDate>.
	 */
	@Override
	public List<LocalDate> getListOfDates(LocalDate from, LocalDate to) {
		Stream<LocalDate> dates = from.datesUntil(to.plusDays(1));
		return dates.collect(Collectors.toList());
	}

	/**
	 * Responsible to fetch Hotel.
	 * 
	 * @param Long hotelId to fetch Hotel from repository by id.
	 * @return Hotel entity.
	 */
	@Override
	public Hotel getHotel(Long hotelId) {
		Optional<Hotel> hotel = hotelRepository.findById(hotelId);
		return hotel.isPresent() ? hotel.get() : new Hotel();
	}

	/**
	 * Responsible to fetch Category.
	 * 
	 * @param Long categoryId to fetch Category from repository by id.
	 * @return Category entity.
	 */
	@Override
	public Category getCategory(Long categoryId) {
		Optional<Category> category = categoryRepository.findById(categoryId);
		return category.isPresent() ? category.get() : new Category();
	}

	/**
	 * Responsible to fetch Room.
	 * 
	 * @param Long roomId to fetch Room from repository by id.
	 * @return Room entity.
	 */
	@Override
	public Room get(Long roomId) {
		Optional<Room> room = roomRepository.findById(roomId);
		return room.isPresent() ? room.get() : new Room();
	}

	/**
	 * Get All rooms for the hotel id ,category id.
	 * 
	 * @param Long hotelId.
	 * @param Long categoryId.
	 * @return the List<RoomDTO>.
	 */
	@Override
	public List<Room> get(Long hotelId, Long categoryId) {
		Hotel hotel = Hotel.builder().id(hotelId).build();
		Category category = Category.builder().id(categoryId).hotel(hotel).build();
		Optional<List<Room>> rooms = roomRepository.findAllByHotelAndCategory(hotel, category);
		return rooms.isPresent() ? rooms.get() : new ArrayList<>();
	}

}
