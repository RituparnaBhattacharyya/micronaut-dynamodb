/*
 *
 * File Name: Car.java
 * Description:
 *
 * @author: BhattacR
 * @version: 1.0
 * @since: 6/19/21, 6:03 PM
 *
 */

package com.ritu.examples.dynamodb.lsi.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@Setter
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    public static final String TABLE_NAME = "CAR";
    public static final String LSI = "BRANDMODELTYPEINDEX";
    private String brand;
    private String modelNumber;
    private String modelType;

    @DynamoDbAttribute("BRAND")
    @DynamoDbPartitionKey
    @DynamoDbSecondaryPartitionKey(indexNames = {LSI})
    public String getBrand() {
        return brand;
    }

    @DynamoDbAttribute("MODELNO")
    public String getModelNumber() {
        return modelNumber;
    }

    @DynamoDbAttribute("MODELTYPE")
    @DynamoDbSecondarySortKey(indexNames = {LSI})
    public String getModelType() {
        return modelType;
    }
}
