/*
package com.asim.connected;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.asim.connected.bean.City;

public class CityTest {

    @Test
    public void build() {
        City city = City.build("Montreal");
        Assert.assertEquals("MONTREAL", city.getName());
    }

    @Test
    public void buildWithNeighbours() {
        City city = City.build("Montreal");
        city.addNearby(City.build("Laval"))
                .addNearby(City.build("Lachine"));

        Set<City> nearby = city.getNearby();
        Assert.assertEquals(2, nearby.size());
        Assert.assertTrue(nearby.contains(City.build("Laval")));
    }


    @Test
    public void addNearby() {
        City city = City.build("Montreal");
        city.addNearby(City.build("Laval"))
                .addNearby(City.build("Lachine"));

        Assert.assertEquals(2, city.getNearby().size());
    }
 

}
*/