package com.example.gis.service;


import com.example.gis.dao.WardDao;
import com.example.gis.model.BritishGridPoint;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WardLookupService {

    @Autowired
    @Setter
    private WardDao wardDao;

    public String findWardsForBuilding(BritishGridPoint point){
        return wardDao.findWardsForBuilding(point).stream().findFirst().orElse("No ward found");
    }
}
