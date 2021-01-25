package com.myhotel.feign.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomUpdateRequestDTO {

	private Long categoryId;

	private List<LocalDate> bookedDates;
}
