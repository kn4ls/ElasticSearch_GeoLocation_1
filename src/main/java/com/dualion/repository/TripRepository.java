package com.dualion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dualion.domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>{

}
