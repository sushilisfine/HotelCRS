package com.myhotel.service;

import java.util.List;

import com.myhotel.domain.Guest;
import com.myhotel.dto.GuestDTO;

public interface GuestService {

	GuestDTO create(GuestDTO guestDTO);

	List<GuestDTO> getAll();

	GuestDTO update(GuestDTO guestRequestDTO);

	GuestDTO get(Long guestId);

	Guest getGuestById(Long guestId);

}
