package com.example.gis.dao;

import com.example.gis.model.BritishGridPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class WardDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<String> findWardsForBuilding(BritishGridPoint point){

        return jdbcTemplate.queryForList("SELECT ward_name " +
                " FROM areas.city_of_edinburgh_council_area" +
                " WHERE ST_Contains(geom, ST_SetSRID(ST_MakePoint(?,?), 27700));",
                String.class, point.getEasting(), point.getNorthing());
    }
}
