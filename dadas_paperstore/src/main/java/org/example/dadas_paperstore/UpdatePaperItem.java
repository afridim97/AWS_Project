package org.example.dadas_paperstore;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class UpdatePaperItem {
    public static void main(String[] args){
        try{
            String tableName = args[0];
            String paperType = args[1];
            int price = Integer.parseInt(args[2]);

            Region region = Region.AP_SOUTH_1;
            DynamoDbClient ddb = DynamoDbClient.builder()
                    .region(region)
                    .build();

            updatePaperItem(tableName,ddb,paperType,price);
            ddb.close();
        }
        catch(DynamoDbException e) {
        System.err.println(e.getMessage());
        System.exit(1);
        }

}

    private static void updatePaperItem(String tableName,DynamoDbClient ddb,String paperType,int price){
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();

        DynamoDbTable<Paper> paperTable =
                enhancedClient.table(tableName, TableSchema.fromBean(Paper.class));

        //Get a Key object.
        Key key = Key.builder()
                .partitionValue(paperType)
                .sortValue(price)
                .build();

        // Get the item by using the key.
        Paper paper = paperTable.getItem(r->r.key(key));
        paper.setPrice(4);

        paperTable.updateItem(r->r.item(paper));


    }
}
