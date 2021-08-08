package com.myhotel.unit.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import com.myhotel.repository.ReservationRepository;
import com.myhotel.service.impl.ReservationServiceImpl;

@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public class ReservationServiceTest {

	@InjectMocks
	private ReservationServiceImpl reservationService;

	@Mock
	private ReservationRepository reservationRepository;

}
