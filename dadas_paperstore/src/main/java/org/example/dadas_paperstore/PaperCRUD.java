package org.example.dadas_paperstore;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;

public class PaperCRUD {
    public static void main(String[] args) {

        final String USAGE = "\n" +
                "To run this example, supply the following values: \n" +
                "artist name \n" +
                "song title \n" +
                "album title \n" +
                "number of awards \n";

        if (args.length < 2) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String paperType = args[0];
        int price = Integer.parseInt(args[1]);

        // snippet-start:[dynamodb.java.dynamoDB_mapping.main]
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        PaperItems items = new PaperItems();

        try{
            // Add new content to the Music table
            items.setPaperType(paperType);
            items.setPrice(price); //convert to an int

            // Save the item
            DynamoDBMapper mapper = new DynamoDBMapper(client);
            mapper.save(items);

            // Load an item based on the Partition Key and Sort Key
            // Both values need to be passed to the mapper.load method
            // Retrieve the item
            PaperItems itemRetrieved = mapper.load(PaperItems.class, paperType, price);
            System.out.println("Item retrieved:");
            System.out.println(itemRetrieved);

            // Modify the Award value
            itemRetrieved.setPrice(4);
            mapper.save(itemRetrieved);
            System.out.println("Item updated:");
            System.out.println(itemRetrieved);

            System.out.print("Done");
        } catch (AmazonDynamoDBException e) {
            e.getStackTrace();
        }
        System.exit(0);
    }

    @DynamoDBTable(tableName="PaperTypes")
    public static class PaperItems {

        //Set up Data Members that correspond to columns in the Music table
        private String paperType;
        private int price;

        @DynamoDBHashKey(attributeName="Name")
        public String getPaperType() {
            return this.paperType;
        }

        public void setPaperType(String type) {
            this.paperType = type;
        }

        @DynamoDBRangeKey(attributeName="Price")
        public int getPrice() {
            return this.price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
