package com.myhotel.managment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

	Optional<List<Room>> findAllByHotel(Hotel hotel);

	Optional<List<Room>> findAllByHotelAndCategory(Hotel hotel, Category category);

	Optional<Room> findByIdAndHotelAndCategory(Long roomId, Hotel hotel, Category category);
}