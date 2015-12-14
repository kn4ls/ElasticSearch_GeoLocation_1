package com.dualion;

import static org.junit.Assert.*;

import org.elasticsearch.common.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dualion.service.TripService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@IntegrationTest
public class TripImplTests {

	@Inject
	private TripService tripService;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
