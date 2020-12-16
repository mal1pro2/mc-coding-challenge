
package com.asim.connected.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.asim.connected.bean.City;

@Service
public class LoadDataServices {

	private final Log LOG = LogFactory.getLog(getClass());

	private Map<String, City> cityMap = new HashMap<>();

	public Map<String, City> getCityMap() {
		return cityMap;
	}

	public City getCity(String name) {
		return cityMap.get(name);
	}

	@PostConstruct
	public void init() {
		Resource resource = new ClassPathResource("static/city.txt");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
			reader.lines().forEach(line -> {
				String[] fields = line.split(",");
				String orig = fields[0].trim().toUpperCase();
				String dest = fields[1].trim().toUpperCase();

				if (!orig.equals(dest)) {
					City city1 = cityMap.getOrDefault(orig, City.build(orig));
					City city2 = cityMap.getOrDefault(dest, City.build(dest));

					city1.addNearby(city2);
					city2.addNearby(city1);

					cityMap.put(city1.getName(), city1);
					cityMap.put(city2.getName(), city2);
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
		LOG.info("Map: " + cityMap);
	}

}
