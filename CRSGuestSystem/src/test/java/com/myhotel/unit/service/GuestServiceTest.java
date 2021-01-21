package com.myhotel.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import com.myhotel.domain.Guest;
import com.myhotel.dto.GuestDTO;
import com.myhotel.repository.GuestRepository;
import com.myhotel.service.impl.GuestServiceImpl;

@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class GuestServiceTest {

	@InjectMocks
	private GuestServiceImpl guestService;

	@Mock
	private GuestRepository guestRepository;

	private GuestDTO guestReqObj() {
		GuestDTO guest = new GuestDTO();
		guest.setEmail("test@test.com");
		guest.setName("test");
		guest.setContact(9999999999L);
		guest.setId(1L);
		return guest;
	}

	private Guest guestObj() {
		Guest guest = new Guest();
		guest.setId(1L);
		guest.setEmail("test@test.com");
		guest.setName("test");
		guest.setContact(9999999999L);
		return guest;
	}

	@Test
	void test1AddGuest() {
		GuestDTO guestRequestDTO = guestReqObj();
		Guest guest = guestObj();

		doReturn(guest).when(guestRepository).save(Mockito.any(Guest.class));

		GuestDTO createdGuest = guestService.create(guestRequestDTO);

		assertEquals(guestRequestDTO.getContact(), createdGuest.getContact());
		assertEquals(guestRequestDTO.getEmail(), createdGuest.getEmail());
		assertEquals(guestRequestDTO.getName(), createdGuest.getName());

		assertEquals(1L, createdGuest.getId());
	}

	@Test
	void test2AddGuest() {
		GuestDTO guestRequestDTO = guestReqObj();
		Guest guest = guestObj();

		lenient().doReturn(guest).when(guestRepository).save(Mockito.any(Guest.class));

		GuestDTO createdGuest = guestService.create(guestRequestDTO);

		assertNotNull(createdGuest);
	}

	@Test
	void test3UpdateGuest() {
		GuestDTO guestRequestDTO = guestReqObj();
		Guest guest = guestObj();

		lenient().doReturn(guest).when(guestRepository).save(Mockito.any(Guest.class));

		GuestDTO updatedGuest = guestService.update(guestRequestDTO);

		assertEquals(guestRequestDTO.getContact(), updatedGuest.getContact());
		assertEquals(guestRequestDTO.getEmail(), updatedGuest.getEmail());
		assertEquals(guestRequestDTO.getName(), updatedGuest.getName());

		assertEquals(1L, updatedGuest.getId());
	}

	@Test
	void test3GetGuest() {

		Guest guest = guestObj();

		doReturn(Optional.of(guest)).when(guestRepository).findById(1L);

		GuestDTO guestResponse = guestService.get(1L);

		assertNotNull(guestResponse);

	}

}
