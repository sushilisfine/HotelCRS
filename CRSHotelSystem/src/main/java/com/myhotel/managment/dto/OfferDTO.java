package com.myhotel.managment.dto;

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
public class OfferDTO {

	private Long id;

	private Long hotelId;

	private Long categoryId;

	private Double value;

}
