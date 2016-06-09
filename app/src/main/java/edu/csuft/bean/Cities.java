package edu.csuft.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chalmers on 2016-06-03 17:47.
 * email:qxinhai@yeah.net
 */
public class Cities implements Serializable{

    private ArrayList<City> cities = null;

    public Cities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public Cities(){}

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }
}
