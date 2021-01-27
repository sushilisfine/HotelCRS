package com.myhotel.feign.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDTO {

	private Long id;

	private Long categoryId;

	private List<LocalDate> bookedDates;

	private Long hotelId;
}
