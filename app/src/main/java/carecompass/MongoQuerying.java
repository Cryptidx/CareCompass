package carecompass;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
public class MongoQuerying {

    public static void main(String[] args) {
        String connectionString = "mongodb+srv://limkaryna:GJ8FMBkVVQG7tPti@care.z7pjkp9.mongodb.net/?retryWrites=true&w=majority"; // MongoDB connection string
        String databaseName = "2024"; // Database name
        String collectionName = "my_collection"; // Collection name
        String excelFilePath = "src/database/high_school_db.xlsx"; // Excel file path

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



//
//public class MongoQuerying {
//
//    public static void main(String[] args) {
//        String connectionString = "mongodb+srv://limkaryna:GJ8FMBkVVQG7tPti@care.z7pjkp9.mongodb.net/?retryWrites=true&w=majority"; // MongoDB connection string
//        String databaseName = "2024"; // Database name
//        String collectionName = "my_collection"; // Collection name
//        String excelFilePath = "src/database/Shelter_mock_database.xlsx"; // Excel file path
//
//        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
//            MongoDatabase database = mongoClient.getDatabase(databaseName);
//
//            // Check if the database already exists
//            if (!database.listCollectionNames().into(new ArrayList<>()).contains(collectionName)) {
//                // Create the collection if it doesn't exist
//                database.createCollection(collectionName);
//                System.out.println("Collection '" + collectionName + "' created successfully.");
//            }
//
//            // Get the collection
//            MongoCollection<Document> collection = database.getCollection(collectionName);
//
//            FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
//            Workbook workbook = new XSSFWorkbook(fileInputStream);
//            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
//
//            // Create a document for the second row and insert it into the collection
//            Row row = sheet.getRow(1); // Second row
//            if (row != null) {
//                Document document = new Document();
//                for (Cell cell : row) {
//                    switch (cell.getCellType()) {
//                        case STRING:
//                            document.append(String.valueOf(cell.getColumnIndex()), cell.getStringCellValue());
//                            break;
//                        case NUMERIC:
//                            document.append(String.valueOf(cell.getColumnIndex()), cell.getNumericCellValue());
//                            break;
//                        case BOOLEAN:
//                            document.append(String.valueOf(cell.getColumnIndex()), cell.getBooleanCellValue());
//                            break;
//                        // Handle other cell types as needed
//                        default:
//                            document.append(String.valueOf(cell.getColumnIndex()), ""); // Set empty string for other types
//                    }
//                }
//                collection.insertOne(document); // Insert the document into the collection
//                System.out.println("Document inserted into collection '" + collectionName + "'.");
//            }
//
//            System.out.println("Data imported successfully to MongoDB!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
