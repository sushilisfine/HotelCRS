package com.myhotel.feignservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhotel.feign.dto.response.GuestDTO;

@FeignClient(name = "guest-service")
public interface GuestFeignService {

	@GetMapping("api/v1/guests/{guest_id}")
	GuestDTO getGuestById(@PathVariable("guest_id") Long guestId);

	@GetMapping("api/v1/guests")
	GuestDTO getGuestByName(@RequestParam("guest_name") String guestUserName);

}
