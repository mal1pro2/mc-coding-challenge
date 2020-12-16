
package com.asim.connected.service;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asim.connected.api.ConnectionController;
import com.asim.connected.bean.City;

@Service
public class ConnectionServices {

	Logger logger = LoggerFactory.getLogger(ConnectionController.class);

	@Autowired
	LoadDataServices loadDataServices;

	public String checkCitiesConnection(String origin, String destination) {

		City originCity = loadDataServices.getCity(origin.toUpperCase());
		City destCity = loadDataServices.getCity(destination.toUpperCase());

		Objects.requireNonNull(originCity);
		Objects.requireNonNull(destCity);

		boolean isConnected = isCitiesConnected(originCity, destCity);

		return isConnected == true ? "YES" : "NO";
	}

	public boolean isCitiesConnected(City origin, City destination) {

		logger.info("Origin: " + origin.getName() + ", destination: " + destination.getName());

		if (origin.equals(destination)) return true;

        if (origin.getNearby().contains(destination)) return true;

        /*
         * The origin city was already visited since we have started from it
         */
        Set<City> visited = new HashSet<>(Collections.singleton(origin));

        /*
         * Put all the neighboring cities into a bucket list
         */
        Deque<City> bucketlist = new ArrayDeque<>(origin.getNearby());


        while (!bucketlist.isEmpty()) {


            City city = bucketlist.getLast();

            if (city.equals(destination)) return true;

            // remove the city from the bucket list

            // first time visit?
            if (!visited.contains(city)) {

                visited.add(city);

                // add neighbours to the bucket list and
                // remove already visited cities from the list
                bucketlist.addAll(city.getNearby());
                bucketlist.removeAll(visited);

                logger.info("Visiting: ["
                        + city.getName()
                        + "] , neighbours: ["
                        + (city.prettyPrint())
                        + "], bucketlist: ["
                        + bucketlist.toString()
                        + "]");
            } else {
                // the city has been visited, so remove it from the bucket list
                bucketlist.removeAll(Collections.singleton(city));
            }
        }

		return false;
	}
}
