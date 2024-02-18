package carecompass;

import com.mongodb.client.result.UpdateResult;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MongoQuerying {

    private String connectionString;
    private String databaseName;
    private String collectionName;

    public MongoQuerying(String connectionString, String databaseName, String collectionName) {
        this.connectionString = connectionString;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    public void importDataFromExcel(String excelFilePath) {
        extractFromExcel(excelFilePath, connectionString, databaseName, collectionName);
    }

    public void updateMongoObject(String identifierAttribute, Integer identifierValue, String attributeToUpdate, Object newValue) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Define the filter based on the identifier attribute and its value
            Document filter = new Document(identifierAttribute, identifierValue);

            // Define the update operation to set the new value for the specified attribute
            Document update = new Document("$set", new Document(attributeToUpdate, newValue));

            // Update one document that matches the filter
            UpdateResult result = collection.updateOne(filter, update);

            if (result.getModifiedCount() > 0) {
                System.out.println(attributeToUpdate + " updated successfully.");
            } else {
                System.out.println("No document found or updated.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static void extractFromExcel(String excelFilePath, String connectionString, String databaseName, String collectionName) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);

            // Check if the database already exists
            if (!database.listCollectionNames().into(new ArrayList<>()).contains(collectionName)) {
                // Create the collection if it doesn't exist
                database.createCollection(collectionName);
                System.out.println("Collection '" + collectionName + "' created successfully.");
            }

            // Get the collection
            MongoCollection<Document> collection = database.getCollection(collectionName);

            FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

            // Get the header row (first row) to use as attribute names
            Row headerRow = sheet.getRow(0);

            // Iterate through each row starting from the second row
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);
                if (currentRow != null) {
                    Document document = new Document();
                    for (Cell cell : headerRow) {
                        int columnIndex = cell.getColumnIndex();
                        String attributeName = cell.getStringCellValue();
                        Cell dataCell = currentRow.getCell(columnIndex);
                        if (dataCell != null) {
                            switch (dataCell.getCellType()) {
                                case STRING:
                                    document.append(attributeName, dataCell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    document.append(attributeName, dataCell.getNumericCellValue());
                                    break;
                                case BOOLEAN:
                                    document.append(attributeName, dataCell.getBooleanCellValue());
                                    break;
                                default:
                                    document.append(attributeName, "");
                            }
                        } else {
                            document.append(attributeName, ""); // Empty value if cell is null
                        }
                    }
                    collection.insertOne(document); // Insert the document into the collection
                    System.out.println("Document inserted into collection '" + collectionName + "'.");
                }
            }

            System.out.println("Data imported successfully to MongoDB!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
