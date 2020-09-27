package com.mo2christian.budget;

import com.mo2christian.line.Line;
import com.mo2christian.line.LineRepository;
import com.mo2christian.line.LineType;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTest
public class LineResourceTest {

    @InjectMock
    LineRepository lineRepository;

    private void before(){
        Line line = new Line();
        line.setAmount(BigDecimal.TEN);
        line.setLabel("Line");
        line.setWithdrawalDay(20);
        line.setBeginPeriod(new Date());
        line.setFrequency(1);
        line.setId(1L);
        line.setType(LineType.CREDIT);
        when(lineRepository.listAll()).thenReturn(Collections.singletonList(line));
    }

    @Test
    @Order(1)
    public void testLine(){
        given()
                .when().get("/line")
                .then()
                .statusCode(is(200));
    }

    @Test
    @Order(2)
    public void testBudget(){
        given()
                .when().get("/budget")
                .then()
                .statusCode(is(200));
    }

}
