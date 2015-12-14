package com.dualion.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.dualion.domain.Trip;

@Repository
public interface TripSearchRepository extends ElasticsearchRepository<Trip, Long>{

}
