package com.myhotel.managment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhotel.managment.domain.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

}