package carecompass;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

public class App {
    public static void main(String[] args) {
        // Connect to MongoDB
        MongoClient client = MongoClients.create("mongodb+srv://limkaryna:GJ8FMBkVVQG7tPti@care.z7pjkp9.mongodb.net/?retryWrites=true&w=majority");
        MongoDatabase database = client.getDatabase("sample_guides");
        MongoCollection<Document> collection = database.getCollection("planets");

        // Query documents and print the _id attribute
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                String id = document.getString("_id");
                System.out.println("_id: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the MongoDB client
            client.close();
        }
    }
}
