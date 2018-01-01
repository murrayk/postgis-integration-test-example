package com.example.gis.model;

import lombok.Getter;
import lombok.NonNull;


public class BritishGridPoint {

    @Getter
    private Integer easting;

    @Getter
    private Integer northing;


    public BritishGridPoint(@NonNull Integer easting, @NonNull Integer northing) {
        this.easting = easting;
        this.northing = northing;
    }
}
