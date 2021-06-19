package com.ritu.examples;

import com.ritu.examples.dynamodb.lsi.entity.Car;
import com.ritu.examples.dynamodb.lsi.repository.CarRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@MicronautTest
class MicronautDynamodbTest {

    @Inject
    CarRepository loadDataRepository;

    @Test
    void testInsertDataIntoDB() {
      //  loadDataRepository.insertDataIntoDB();
        List<Car> cars = loadDataRepository.fetchCarWithModelType("BMW", "SEDAN");
        Assertions.assertNotNull(cars);
        Assertions.assertEquals(1, cars.size());
        Assertions.assertEquals(cars.get(0).getModelNumber(), "5 Series Facelift");
    }

}
