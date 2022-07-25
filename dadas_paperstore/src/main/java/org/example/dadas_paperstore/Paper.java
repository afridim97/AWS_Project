package org.example.dadas_paperstore;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;

/**
 * This class is used by the Enhanced Client examples.
 */

public class Paper {

    private String type;
    private int price;

    public String getPaperType() {
        return this.type;
    };

    public void setPaperType(String type) {

        this.type = type;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(int price) {

        this.price = price;
    }

}