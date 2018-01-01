package com.example.gis.rest;

import com.example.gis.model.BritishGridPoint;
import com.example.gis.service.WardLookupService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Validated
public class WardLookupController {

    /**
     * British National Grid
     */
    private static final int MAX_EASTING_METERS = 700000;
    private static final int MAX_NORTHING_METERS = 1300000;

    @Setter
    @Autowired
    private WardLookupService lookupService;

    @RequestMapping(value = "wardForBuilding", method = RequestMethod.GET)
    public String administrativeWardForBuilding(
            @Min(0) @Max(MAX_EASTING_METERS) Integer easting,
            @Min(0) @Max(MAX_NORTHING_METERS) Integer northing) {
        BritishGridPoint point = new BritishGridPoint(easting, northing);
        return lookupService.findWardsForBuilding(point);
    }



    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Set<String>> handleConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        Set<String> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                .map(constraintViolation -> String.format("%s value '%s' %s", constraintViolation.getPropertyPath(),
                        constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
                .collect(Collectors.toList()));

        return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);

    }
}
