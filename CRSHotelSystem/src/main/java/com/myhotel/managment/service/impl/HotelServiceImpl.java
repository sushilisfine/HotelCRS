package com.myhotel.managment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.HotelDTO;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.service.HotelService;

/**
 * Service implementation Class for performing operations on Hotel entity.
 *
 * @author Sushil Yadav
 */
@Service
public class HotelServiceImpl implements HotelService {

	private HotelRepository hotelRepository;

	public HotelServiceImpl(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	/**
	 * Creates a Hotel.
	 * 
	 * @param HotelDTO , for saving.
	 * @return the saved HotelDTO.
	 */
	@Override
	public HotelDTO create(HotelDTO hotelDTO) {
		Hotel hotel = convertDTOToEntity(hotelDTO);
		return convertEntityToDTO(hotelRepository.save(hotel));
	}

	/**
	 * Updates a Hotel
	 * 
	 * @param HotelDTO , for updating.
	 * @return the updated HotelDTO.
	 */
	@Override
	public HotelDTO update(HotelDTO hotelDTO) {

		Hotel hotel = convertDTOToEntity(hotelDTO);
		hotelRepository.save(hotel);
		return convertEntityToDTO(hotel);
	}

	/**
	 * Get all Hotels
	 * 
	 * @return the list of HotelDTO.
	 */
	@Override
	public List<HotelDTO> getAll() {

		List<Hotel> hotels = hotelRepository.findAll();
		return convertEntityToDTO(hotels);
	}

	/**
	 * Convert HotelDTO Entity to Hotel entity
	 * 
	 * @param HotelDTO , for converting.
	 * @return the converted Hotel.
	 */
	private Hotel convertDTOToEntity(HotelDTO hotelDTO) {

		return Hotel.builder().id(hotelDTO.getId()).address(hotelDTO.getAddress()).contact(hotelDTO.getContact())
				.build();

	}

	/**
	 * Convert Hotel Entity to HotelDTO entity
	 * 
	 * @param Hotel , for converting.
	 * @return the converted HotelDTO.
	 */
	private HotelDTO convertEntityToDTO(Hotel hotel) {

		return HotelDTO.builder().address(hotel.getAddress()).contact(hotel.getContact()).id(hotel.getId()).build();

	}

	/**
	 * Convert List<Hotel> Entity to List<HotelDTO> entity
	 * 
	 * @param List<Hotel> , for converting.
	 * @return the converted List<HotelDTO>.
	 */
	private List<HotelDTO> convertEntityToDTO(List<Hotel> hotels) {

		List<HotelDTO> hotelDTO = new ArrayList<>();
		hotels.forEach(hotel -> hotelDTO.add(convertEntityToDTO(hotel)));
		return hotelDTO;
	}

	/**
	 * Responsible to get the Hotel entity from repository by the hotelId provided
	 * 
	 * @param Long hotelId to fetch Hotel entity.
	 * @return the Hotel entity fetched.
	 */
	@Override
	public Hotel get(Long hotelId) {
		Optional<Hotel> hotel = hotelRepository.findById(hotelId);
		return hotel.orElse(new Hotel());
	}

}
