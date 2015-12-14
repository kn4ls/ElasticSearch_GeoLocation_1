package com.dualion.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import static org.elasticsearch.index.query.FilterBuilders.boolFilter;
import static org.elasticsearch.index.query.FilterBuilders.geoDistanceFilter;
import static org.elasticsearch.index.query.FilterBuilders.nestedFilter;
import static org.elasticsearch.index.query.FilterBuilders.queryFilter;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.fuzzyLikeThisFieldQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

import javax.inject.Inject;

import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.GeoDistanceFilterBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dualion.domain.Trip;
import com.dualion.repository.TripRepository;
import com.dualion.repository.search.TripSearchRepository;

@Service("tripService")
public class TripService {

	private final Logger log = LoggerFactory.getLogger(TripService.class);
	private static final Double defaultRadius = 1.0;

	@Inject
	private TripSearchRepository tripSearchRepository;

	@Inject
	private TripRepository tripRepository;

	@Inject
	public ElasticsearchOperations elasticsearchTemplate;

	@Transactional
	public Trip createTrip(Trip trip) {
		Trip reult = tripRepository.save(trip);
		return tripSearchRepository.save(reult);
	}

	@Transactional(readOnly = true)
	public Trip findTrip(Long id) {
		return tripSearchRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	public Iterable<Trip> findAllTrips() {
		return tripSearchRepository.findAll();
	}

	public List<Trip> findByGeoLocationSort(Double latitude, Double longitude, Integer distance, DistanceUnit unit, SortOrder order) {
		GeoDistanceFilterBuilder filter = FilterBuilders.geoDistanceFilter("location").distance(distance, unit).lat(latitude).lon(longitude);
		 
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withFilter(filter)
				.withSort(SortBuilders.geoDistanceSort("location").point(latitude, longitude).order(order))
				.build();
		 
		return StreamSupport
				.stream(tripSearchRepository.search(searchQuery).spliterator(), false)
				.collect(Collectors.toList());
	}
	
	public List<Trip> findByGeoLocation(Double latitude, Double longitude, Integer distance,  DistanceUnit unit) {	
		String startLocation = latitude.toString() +"," + longitude.toString();
		String range = unit.toString(distance);
		
		CriteriaQuery query = new CriteriaQuery(new Criteria("location").within(startLocation, range));
		return elasticsearchTemplate.queryForList(query, Trip.class);
	}
	
public List<Trip> findByGeoLocation2(Double latitude, Double longitude, Integer distance,  DistanceUnit unit) {	
		BoolFilterBuilder boolFilter = boolFilter()
				.must(geoDistanceFilter("location").distance(distance, unit).point(latitude,longitude));
		
		return StreamSupport
				.stream(tripSearchRepository.search(new NativeSearchQuery(matchAllQuery(), boolFilter)).spliterator(), false)
				.collect(Collectors.toList());
	}
	
}
