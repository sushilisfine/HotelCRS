package com.myhotel.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.myhotel.domain.Reservation;
import com.myhotel.dto.ReservationDTO;
import com.myhotel.exception.CustomException;
import com.myhotel.feign.dto.request.RoomUpdateRequestDTO;
import com.myhotel.feign.dto.response.CategoryDTO;
import com.myhotel.feign.dto.response.GuestDTO;
import com.myhotel.feign.dto.response.OfferDTO;
import com.myhotel.feign.dto.response.RoomDTO;
import com.myhotel.feignservice.GuestFeignService;
import com.myhotel.feignservice.HotelFeignService;
import com.myhotel.repository.ReservationRepository;
import com.myhotel.service.ReservationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation Class for performing operations on Reservation entity.
 *
 * @author Sushil Yadav
 */
@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

	private HotelFeignService hotelFeignService;
	private GuestFeignService guestFeignService;
	private ReservationRepository reservationRepository;

	public ReservationServiceImpl(HotelFeignService hotelFeignService, ReservationRepository reservationRepository,
			GuestFeignService guestFeignService) {
		this.hotelFeignService = hotelFeignService;
		this.reservationRepository = reservationRepository;
		this.guestFeignService = guestFeignService;
	}

	@Override
	public ReservationDTO create(ReservationDTO reservationReqObj) {

		LocalDate startDate = reservationReqObj.getStartDate();
		LocalDate endDate = reservationReqObj.getEndDate();
		List<LocalDate> requestedDates = getListOfDates(startDate, endDate);

		RoomDTO room = getAvailableRooms(reservationReqObj, startDate, endDate);
		OfferDTO offerDTO = getOffer(reservationReqObj);
		CategoryDTO category = getCategory(reservationReqObj);

		return converteEntityToDTO(createReservationEntry(category, requestedDates, offerDTO, reservationReqObj, room));
	}

	@Override
	public ReservationDTO update(ReservationDTO reservation) {
		return null;
	}

	/**
	 * @returnGuestDTO
	 */
	@HystrixCommand(fallbackMethod = "getPrincipalFallback")
	private GuestDTO getPrincipalUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		GuestDTO guest = guestFeignService.getGuestByName(principal.toString());
		return guest != null ? guest : new GuestDTO();
	}

	public GuestDTO getPrincipalFallback() {
		log.error("Guest Service is down ");
		return GuestDTO.builder().id(0L).name("Fallback").build();
	}

	private Reservation createReservationEntry(CategoryDTO category, List<LocalDate> requestedDates, OfferDTO offerDTO,
			ReservationDTO reservationReqObj, RoomDTO room) {

		updateBookingDates(requestedDates, reservationReqObj, room.getId());
		Double charges = category != null ? category.getCharges() : 0;

		Double offerValue = offerDTO != null ? offerDTO.getValue() : 0;
		Double totalCharges = (charges - offerValue) * requestedDates.size();

		return createEntry(reservationReqObj, totalCharges, offerDTO, room);
	}

	@HystrixCommand(fallbackMethod = "getOfferFallback")
	private OfferDTO getOffer(ReservationDTO reservationReqObj) {
		OfferDTO offer = hotelFeignService.getOffer(reservationReqObj.getHotelId(), reservationReqObj.getOfferId(),
				reservationReqObj.getCategoryId()).getBody();
		return offer != null ? offer : OfferDTO.builder().id(0L).build();
	}

	public OfferDTO getOfferFallback() {
		log.error("Offer Service is down ");
		return OfferDTO.builder().id(0L).build();
	}

	@HystrixCommand(fallbackMethod = "getCategoryFallback")
	private CategoryDTO getCategory(ReservationDTO reservationReqObj) {
		CategoryDTO category = hotelFeignService
				.getCategory(reservationReqObj.getHotelId(), reservationReqObj.getCategoryId()).getBody();
		return category != null ? category : new CategoryDTO();
	}

	public CategoryDTO getCategoryFallback() {
		log.error("Category Service is down ");
		return CategoryDTO.builder().id(0L).build();
	}

	private RoomDTO updateBookingDates(List<LocalDate> requestedDates, ReservationDTO reservationReqObj, Long roomId) {
		RoomUpdateRequestDTO roomRequestDTO = new RoomUpdateRequestDTO();
		roomRequestDTO.setCategoryId(reservationReqObj.getCategoryId());
		roomRequestDTO.setBookedDates(requestedDates);

		RoomDTO room = hotelFeignService.updateBooking(reservationReqObj.getHotelId(), roomId, roomRequestDTO)
				.getBody();
		return room != null ? room : new RoomDTO();
	}

	private List<LocalDate> getListOfDates(LocalDate startDate, LocalDate endDate) {

		if (startDate.isAfter(endDate)) {
			log.error("Start Date: {} is greater than: {}", startDate, endDate);
			throw new CustomException("Start Date should be less than End Date, Please check");
		}
		Stream<LocalDate> dates = startDate.datesUntil(endDate.plusDays(1));
		return dates.collect(Collectors.toList());
	}

	@HystrixCommand(fallbackMethod = "getAvailableRoomsFallback")
	private RoomDTO getAvailableRooms(ReservationDTO reservationReqObj, LocalDate startDate, LocalDate endDate) {

		List<RoomDTO> rooms = hotelFeignService.getAvailableRooms(reservationReqObj.getHotelId(), startDate.toString(),
				endDate.toString(), reservationReqObj.getCategoryId()).getBody();

		RoomDTO room = new RoomDTO();

		if (rooms != null && !rooms.isEmpty())
			room = rooms.get(0);

		return room;
	}

	@SuppressWarnings("unused")
	private RoomDTO getAvailableRoomsFallback(Long hotelId, LocalDate startDate, LocalDate endDate, Long categoryId) {
		log.error("Room Service is down ");
		return new RoomDTO();
	}

	private Reservation createEntry(ReservationDTO reservationReqObj, Double totalCharges, OfferDTO offerDTO,
			RoomDTO roomDTO) {

		Reservation reservation = Reservation.builder().categoryId(reservationReqObj.getCategoryId())
				.charges(totalCharges).startDate(reservationReqObj.getStartDate())
				.endDate(reservationReqObj.getEndDate()).guestId(getPrincipalUser().getId())
				.hotelId(reservationReqObj.getHotelId()).offerId(offerDTO.getId()).roomId(roomDTO.getId()).build();
		reservationRepository.save(reservation);

		return reservation != null ? reservation : new Reservation();
	}

	private ReservationDTO converteEntityToDTO(Reservation reservation) {
		return ReservationDTO.builder().id(reservation.getId()).categoryId(reservation.getCategoryId())
				.charges(reservation.getCharges()).endDate(reservation.getEndDate()).guestId(reservation.getGuestId())
				.hotelId(reservation.getHotelId()).offerId(reservation.getOfferId()).roomId(reservation.getRoomId())
				.startDate(reservation.getStartDate()).build();
	}

	@Override
	public Reservation get(Long reservationId) {
		Optional<Reservation> reservation = reservationRepository.findById(reservationId);
		return reservation.orElse(new Reservation());
	}

}
