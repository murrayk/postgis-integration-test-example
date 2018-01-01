package com.example.gis.dao;


import com.example.gis.model.BritishGridPoint;
import org.flywaydb.core.Flyway;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
public class TestWardDao {


    @Rule
    public PostgreSQLContainer postgres = new PostgreSQLContainer("mdillon/postgis:9.5");


    @Value(value = "classpath:/db/sql/test-fixture.sql")
    private Resource testFixtureResource;

    private WardDao wardDao;

    @Before
    public void setup() throws SQLException {

        flywayCreateFreshSchema();

        areasTestFixture();

        wardDao = new WardDao();
        wardDao.setDataSource(postGisDocker());
    }



    @Test
    public void testFindWardOfBuilding(){
        BritishGridPoint pointInPentlandsWard = new BritishGridPoint(319426,670158);

        List<String> result = wardDao.findWardsForBuilding(pointInPentlandsWard);
        Assert.assertEquals("point in pentlands ward", "Pentland Hills", result.get(0));

        BritishGridPoint pointOutsideUrbanArea = new BritishGridPoint(0,0);

        List<String> emptyResult = wardDao.findWardsForBuilding(pointOutsideUrbanArea);
        Assert.assertTrue("point not in ward",  emptyResult.isEmpty());

    }




    DataSource postGisDocker() {
        return DataSourceBuilder
                .create()
                .username(postgres.getUsername())
                .password(postgres.getPassword())
                .url(postgres.getJdbcUrl())
                .driverClassName(postgres.getDriverClassName())
                .build();

    }



    private void areasTestFixture() throws SQLException {
        ScriptUtils.executeSqlScript(postGisDocker().getConnection(), testFixtureResource);
    }

    private void flywayCreateFreshSchema() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(postGisDocker());
        flyway.setSchemas("areas");
        flyway.migrate();
    }

}
