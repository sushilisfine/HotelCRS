package com.myhotel.feignservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhotel.feign.dto.CategoryDTO;
import com.myhotel.feign.dto.HotelDTO;
import com.myhotel.feign.dto.OfferDTO;
import com.myhotel.feign.dto.RoomDTO;

@FeignClient(name = "hotel-service", path = "api/v1")
public interface HotelFeignService {

	@GetMapping("/hotels")
	HotelDTO getAllHotels();

	@PutMapping(value = "/hotels/{hotel_id}/rooms/{room_id}")
	public ResponseEntity<RoomDTO> updateBooking(@PathVariable("hotel_id") Long hotelId,
			@PathVariable("room_id") Long roomId, @RequestBody RoomDTO roomUpdateRequestDTO);

	@GetMapping(value = "/hotels/{hotel_id}/rooms")
	public ResponseEntity<List<RoomDTO>> getAllRooms(@PathVariable("hotel_id") Long hotelId);

	@GetMapping(value = "/hotels/{hotel_id}/rooms/availability")
	public ResponseEntity<List<RoomDTO>> getAvailableRooms(
			@PathVariable(required = true, name = "hotel_id") Long hotelId, @RequestParam String from,
			@RequestParam String to, @RequestParam Long categoryId);

	@GetMapping(value = "/hotels/{hotel_id}/offers/{offer_id}")
	public ResponseEntity<OfferDTO> getOffer(@PathVariable("hotel_id") Long hotelId,
			@PathVariable("offer_id") Long offerId, @RequestParam("category_id") Long categoryId);

	@GetMapping(value = "/hotels/{hotel_id}/categories/{category_id}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable("hotel_id") Long hotelId,
			@PathVariable("category_id") Long categoryId);
}
