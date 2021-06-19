/*
 * File Name: LoadDataRepository.java
 * Description:
 *
 * @author: BhattacR
 * @version: 1.0
 * @since: 6/19/21, 6:08 PM
 *
 */

package com.ritu.examples.dynamodb.lsi.repository;

import com.ritu.examples.dynamodb.lsi.entity.Car;
import io.micronaut.data.annotation.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Repository
public class CarRepository {
    private final DynamoDbAsyncTable<Car> carAsyncTable;

    public CarRepository(DynamoDbEnhancedAsyncClient dynamoDbAsyncClient) {
        this.carAsyncTable = dynamoDbAsyncClient.table(Car.TABLE_NAME, TableSchema.fromBean(Car.class));
    }

    public void insertDataIntoDB() {
        List.of(new Car("BMW", "5 Series Facelift", "SEDAN"),
                new Car("BMW", "X1", "SUV")).stream().forEach(item -> {
            CompletableFuture<Void> future = carAsyncTable.putItem(item);
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Car> fetchCarWithModelType(String brand, String modelType) {
        List<Car> cars = new ArrayList<>();
        CompletableFuture<Void> event = carAsyncTable.index(Car.LSI)
                .query(QueryConditional.keyEqualTo(Key.builder().partitionValue(brand).sortValue(modelType).build()))
                .subscribe(k -> cars.addAll(k.items()));
        getFuture(event);
        return cars;
    }

    void getFuture(CompletableFuture<Void> future) {
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
