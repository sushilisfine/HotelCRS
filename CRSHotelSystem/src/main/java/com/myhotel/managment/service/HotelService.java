package com.myhotel.managment.service;

import java.util.List;

import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.HotelDTO;

public interface HotelService {

	HotelDTO create(HotelDTO hotelDTO);

	List<HotelDTO> getAll();

	HotelDTO update(HotelDTO hotelDTO);

	Hotel get(Long hotelId);

}
