
//snippet-sourcedescription:[CreateTable.java demonstrates how to create an Amazon DynamoDB table by using a waiter.]
//snippet-keyword:[SDK for Java v2]
//snippet-keyword:[Code Sample]
//snippet-service:[Amazon DynamoDB]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[05/16/2022]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/
package org.example.dadas_paperstore;
// snippet-start:[dynamodb.java2.create_table.import
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;

// snippet-end:[dynamodb.java2.create_table.import]

/**
 * Before running this Java V2 code example, set up your development environment, including your credentials.
 *
 * For more information, see the following documentation topic:
 *
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class CreatePaperTable {

    public static void main(String[] args) {

        String tableName = args[0];
        System.out.println("Creating an Amazon DynamoDB table "+tableName);

        try {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
            CreateTableResult response = createTable(client,tableName);
            System.out.println(response.getTableDescription().getTableName());
        }
        catch (AmazonServiceException e) {
            System.err.println("exception " + e.getErrorMessage());
        }

    }

    // snippet-start:[dynamodb.java2.create_table.main]
    public static CreateTableResult createTable(AmazonDynamoDB client, String tableName) {
        CreateTableResult result = new CreateTableResult();

        try {
            CreateTableRequest request = new CreateTableRequest().withAttributeDefinitions(new AttributeDefinition().withAttributeName("Name").withAttributeType("S"),
                    new AttributeDefinition().withAttributeName("Price").withAttributeType("N")).withKeySchema(
                            new KeySchemaElement().withAttributeName("Name").withKeyType("HASH"),
                    new KeySchemaElement().withAttributeName("Price").withKeyType("RANGE")).withTableName(tableName).withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));

            result = client.createTable(request);
        } catch (AmazonServiceException e) {
            System.err.println("exception " + e.getErrorMessage());
        }

        return result;
    }
    // snippet-end:[dynamodb.java2.create_table.main]
}
