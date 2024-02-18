package carecompass;

public class MongoQueryCreateMongoExample {

    public static void main(String[] args) {

        String connectionString = "mongodb+srv://limkaryna:GJ8FMBkVVQG7tPti@care.z7pjkp9.mongodb.net/?retryWrites=true&w=majority"; // MongoDB connection string
        String databaseName = "2024"; // Database name
        String collectionName = "highschools_in_toronto"; // Collection name
        String excelFilePath = "src/database/high_school_db.xlsx"; // Excel file path

        // Uploading to Mongo into data base, assuming we were given this from shelter and school registerans
        MongoQuerying myQuerySchools = new MongoQuerying(connectionString, databaseName, collectionName);
        myQuerySchools.importDataFromExcel(excelFilePath);

        myQuerySchools.updateMongoObject("School Number", 918997, "School Language", "English");

        MongoQuerying myQueryShelters = new MongoQuerying(connectionString, databaseName, "shelters_in_toronto");
        myQueryShelters.importDataFromExcel("src/database/Shelter_mock_database.xlsx");

    }
}

