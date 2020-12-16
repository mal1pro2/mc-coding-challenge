
package com.asim.connected;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.asim.connected.bean.City;
import com.asim.connected.service.ConnectionServices;
import com.asim.connected.service.LoadDataServices;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ConnectionApplicationTest {

    @Autowired
    LoadDataServices dataServices;
    
    @Autowired
    ConnectionServices connectionServices;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void fileLoad() {
        Assert.assertFalse("File load failed", dataServices.getCityMap().isEmpty());
    }

    @Test
    public void sameCity() {
        City city = City.build("A");
        Assert.assertTrue(connectionServices.isCitiesConnected(city, city));
    }

    @Test
    public void neighbours() {
        City cityA = dataServices.getCity("A");
        City cityB = dataServices.getCity("B");

        Assert.assertNotNull("Invalid test data. City not found: A", cityA);
        Assert.assertNotNull("Invalid test data. City not found: B", cityB);

        Assert.assertTrue(connectionServices.isCitiesConnected(cityA, cityB));
    }

    @Test
    public void distantConnected() {
        City cityA = dataServices.getCity("F");
        City cityB = dataServices.getCity("A");

        Assert.assertNotNull("Invalid test data. City not found: F", cityA);
        Assert.assertNotNull("Invalid test data. City not found: A", cityB);

        Assert.assertTrue(connectionServices.isCitiesConnected(cityA, cityB));
    }

    @Test
    public void restConnectedIT() {

        Map<String, String> params = new HashMap<>();
        params.put("origin", "A");
        params.put("destination", "F");

        String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        Assert.assertEquals("YES", body);
    }

    @Test
    public void restNotConnectedIT() {

        Map<String, String> params = new HashMap<>();
        params.put("origin", "a");
        params.put("destination", "l");

        String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        Assert.assertEquals("NO", body);
    }

    @Test
    public void badRequestIT() {
        restTemplate.getForObject("/connected", String.class);
        ResponseEntity<String> response = restTemplate.exchange("/connected?origin=none&destination=none", HttpMethod.GET, HttpEntity.EMPTY, String.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}
