package com.myhotel.repository;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhotel.domain.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

	/**
	 * @param guestUserName
	 * @returnOptional<Guest>
	 */
	Optional<Guest> findByName(@NotNull String guestUserName);

}