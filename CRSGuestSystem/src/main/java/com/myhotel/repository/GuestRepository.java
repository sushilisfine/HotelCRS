package com.myhotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhotel.domain.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

}