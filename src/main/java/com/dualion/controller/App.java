package com.dualion.controller;

import javax.inject.Inject;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.dualion.domain.Car;
import com.dualion.domain.Trip;
import com.dualion.service.TripService;

@Component
public class App implements ApplicationRunner {

	private final Logger log = LoggerFactory.getLogger(ApplicationRunner.class);
	
	@Inject
	private TripService tripService; 
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		log.info("Create Trips:");
		log.info("-------------------------------");
		tripService.createTrip(new Trip("Primer viatge", "40.715, -74.011", new Car(1)));
		tripService.createTrip(new Trip("Segon viatge", "41.315, -75.011", new Car(2)));
		tripService.createTrip(new Trip("Tercer viatge", "43.501, -73.011", new Car(3)));
		tripService.createTrip(new Trip("Cuart viatge", "42.567, -71.011", new Car(4)));
		log.info("");
		
		log.info("Trips found with findAllTrips():");
		log.info("-------------------------------");
		for (Trip trips : tripService.findAllTrips()) {
			log.info(trips.toString());
		}
        log.info("");
        
        log.info("Trips found with findByGeoLocation():");
		log.info("-------------------------------");
		for (Trip trips : tripService.findByGeoLocation(40.0, -74.0, 500, DistanceUnit.KILOMETERS)) {
			log.info(trips.toString());
		}
        log.info("");
        
        log.info("Trips found with findByGeoLocation():");
		log.info("-------------------------------");
		for (Trip trips : tripService.findByGeoLocation2(40.0, -74.0, 500, DistanceUnit.KILOMETERS)) {
			log.info(trips.toString());
		}
        log.info("");
		
        log.info("Trips found with findByGeoLocationSort():");
		log.info("-------------------------------");
		for (Trip trips : tripService.findByGeoLocationSort(40.0, -74.0, 500, DistanceUnit.KILOMETERS, SortOrder.ASC)) {
			log.info(trips.toString());
		}
        log.info("");
        
	}
	
}