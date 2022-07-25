package org.example.dadas_paperstore;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primaryPartitionKey;

public class AddPaperItem {

        protected static final TableSchema<Paper> TABLE_SCHEMA =
                StaticTableSchema.builder(Paper.class)
                        .newItemSupplier(Paper::new)
                        .addAttribute(String.class, a -> a.name("Type")
                                .getter(Paper::getPaperType)
                                .setter(Paper::setPaperType)
                                .tags(primaryPartitionKey()))
                        .build();

        public static void main(String[] args) {

            Region region = Region.AP_SOUTH_1;
            DynamoDbClient ddb = DynamoDbClient.builder()
                    .region(region)
                    .build();

            DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                    .dynamoDbClient(ddb)
                    .build();

            putRecord(enhancedClient);
            ddb.close();
        }

        private static void putRecord(DynamoDbEnhancedClient enhancedClient){
            try {
                //Create a DynamoDbTable object
                DynamoDbTable<Paper> mappedTable = enhancedClient.table("Paper_Types", TABLE_SCHEMA);

                //Populate the Table
                Paper paper = new Paper();
                paper.setPrice(20);
                paper.setPaperType("Bond");

                mappedTable.putItem(paper);

            } catch (DynamoDbException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
            System.out.println("done");
        }

}
