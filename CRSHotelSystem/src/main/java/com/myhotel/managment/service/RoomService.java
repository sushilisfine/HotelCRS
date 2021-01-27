package com.myhotel.managment.service;

import java.time.LocalDate;
import java.util.List;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Room;
import com.myhotel.managment.dto.RoomDTO;

public interface RoomService {

	RoomDTO add(RoomDTO roomDTO);

	RoomDTO update(RoomDTO roomDTO);

	List<RoomDTO> getAll(Long hotelId);

	List<RoomDTO> getByParams(Long hotelId, LocalDate from, LocalDate to, Long categoryId);

	List<LocalDate> getListOfDates(LocalDate from, LocalDate to);

	Category getCategory(Long categoryId);

	Hotel getHotel(Long hotelId);

	Room get(Long roomId);

	List<Room> get(Long hotelId, Long categoryId);

}
