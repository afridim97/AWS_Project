/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package org.example.dadas_paperstore;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import java.util.HashMap;

/**
 * Before running this Java V2 code example, set up your development environment, including your credentials.
 *
 * For more information, see the following documentation topic:
 *
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 *
 * To update an Amazon DynamoDB table using the AWS SDK for Java V2, its better practice to use the
 * Enhanced Client, See the EnhancedModifyItem example.
 */
public class UpdatePaperItem {

    public static void main(String[] args) {

        final String usage = "\n" +
                "Usage:\n" +
                "    <tableName> <key> <keyVal> <name> <updateVal>\n\n" +
                "Where:\n" +
                "    tableName - The Amazon DynamoDB table (for example, Music3).\n" +
                "    key - The name of the key in the table (for example, Artist).\n" +
                "    keyVal - The value of the key (for example, Famous Band).\n" +
                "    name - The name of the column where the value is updated (for example, Awards).\n" +
                "    updateVal - The value used to update an item (for example, 14).\n" +
                " Example:\n" +
                "    UpdateItem Music3 Artist Famous Band Awards 14\n";


        Region region = Region.AP_SOUTH_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        updateTableItem(ddb);
        ddb.close();
    }
    public static void updateTableItem(DynamoDbClient ddb){

        HashMap<String,AttributeValue> itemKey = new HashMap<>();
        itemKey.put("Type", AttributeValue.builder().s("Bond").build());

        HashMap<String, AttributeValue> attributeValues = new HashMap<>();
        attributeValues.put(":price", AttributeValue.builder().n("4").build());

        UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .tableName("Paper_Types")
                .key(itemKey)
                .updateExpression("set Price = :price")
                .expressionAttributeValues(attributeValues)
                .build();
        try {
            ddb.updateItem(updateItemRequest);
        }
        catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("The Amazon DynamoDB table was updated!");
    }
}
