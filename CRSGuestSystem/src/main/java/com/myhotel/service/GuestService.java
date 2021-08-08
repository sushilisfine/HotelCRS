package com.myhotel.service;

import javax.validation.constraints.NotNull;

import com.myhotel.domain.Guest;
import com.myhotel.dto.GuestDTO;

public interface GuestService {

	GuestDTO create(GuestDTO guestDTO);

	GuestDTO update(GuestDTO guestRequestDTO);

	GuestDTO get(Long guestId);

	Guest getGuestById(Long guestId);

	GuestDTO getByName(@NotNull String guestUserName);

}
