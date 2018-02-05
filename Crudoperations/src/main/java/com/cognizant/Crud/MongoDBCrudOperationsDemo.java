package com.cognizant.Crud;

import java.util.ArrayList;
import java.util.List;
 
import org.bson.Document;
 
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
 
public class MongoDBCrudOperationsDemo {
 
	public static void main(String[] args) {
 
		String dbName = "mydb";
		String collectionName = "myCollection";
 
		MongoDBCrudOperationsDemo mongoDBCrudOperationsDemo = new MongoDBCrudOperationsDemo();
		MongoClient mongoClient = mongoDBCrudOperationsDemo
				.getMongoConnection();
 
		try {
			mongoDBCrudOperationsDemo.InsertOneDocument(mongoClient,dbName, collectionName);
			mongoDBCrudOperationsDemo.InsertMultipleDocuments(mongoClient,
					dbName, collectionName);
			Document singleDocument = mongoDBCrudOperationsDemo.findOne(
					mongoClient, dbName, collectionName);
 
			if (singleDocument != null) {
				System.out.println("Enter Finding single document data");
				System.out.println(singleDocument.get("name"));
				System.out.println("Exit Finding single document data");
			}
 
			mongoDBCrudOperationsDemo.findAll(mongoClient, dbName,
					collectionName);
			mongoDBCrudOperationsDemo.updateOne(mongoClient, dbName,
					collectionName);
			mongoDBCrudOperationsDemo.updateMultipleDocuments(mongoClient,
					dbName, collectionName);
			mongoDBCrudOperationsDemo.deleteOne(mongoClient, dbName,
					collectionName);
			mongoDBCrudOperationsDemo.deleteMany(mongoClient, dbName,
					collectionName);
			mongoDBCrudOperationsDemo.dropCollection(mongoClient, dbName,
					collectionName);
 
		} catch (MongoException mE) {
			System.err.println(mE.getMessage());
		}
 
	}
 
	private void dropCollection(MongoClient mongoClient, String db,
			String collection) {
 
		MongoCollection<Document> mongoCollection = getMongoCollection(
				mongoClient, db, collection);
		mongoCollection.drop();
		System.out.println("Collection dropped successfully");
	}
 
	private void deleteOne(MongoClient mongoClient, String db, String collection) {
 
		MongoCollection<Document> mongoCollection = getMongoCollection(
				mongoClient, db, collection);
		mongoCollection.deleteOne(new Document("name", "Mayuri"));
		System.out.println("Single Document Matching Query Deleted Successfully");
 
	}
 
	private void deleteMany(MongoClient mongoClient, String db,
			String collection) {
 
		MongoCollection<Document> mongoCollection = getMongoCollection(
				mongoClient, db, collection);
		mongoCollection.deleteMany(new Document("name", "Narmadha"));
		System.out.println("Multiple Documents Matching Query Deleted Successfully");
 
	}
 
	private void updateMultipleDocuments(MongoClient mongoClient, String db,
			String collection) {
 
		System.out.println("Enter updateMultipleDocuments ");
 
		MongoCollection<Document> mongoCollection = getMongoCollection(
				mongoClient, db, collection);
 
		mongoCollection.updateMany(new Document("name", "Narmadha"),
			 new Document("$set", new Document("name","Mayuri")));
		MongoCursor<Document> dbCursor = mongoCollection.find().iterator();
		System.out.println("Documents after updated multiple documents  \n");
		printDocuments(dbCursor);
 
		System.out.println("Exit updateMultipleDocuments ");
 
	}
 
	private void updateOne(MongoClient mongoClient, String db, String collection) {
 
		System.out.println("Enter updateOne ");
 
		MongoCollection<Document> mongoCollection = getMongoCollection(
				mongoClient, db, collection);
		mongoCollection.updateOne(new Document("name", "Mayuri"), new Document("$set", new Document("name","Narmadha")));
		MongoCursor<Document> dbCursor = mongoCollection.find().iterator();
		System.out.println("Documents after update of one document  \n");
		printDocuments(dbCursor);
 
		System.out.println("Exit updateOne ");
 
	}
 
	private void findAll(MongoClient mongoClient, String db, String collection) {
		System.out.println("Enter FindAll");
		MongoCollection<Document> mongoCollection = getMongoCollection(
				mongoClient, db, collection);
		MongoCursor<Document> dbCursor = mongoCollection.find().iterator();
		printDocuments(dbCursor);
		System.out.println("Exit FindAll");
 
	}
 
	private Document findOne(MongoClient mongoClient, String db,
			String collection) {
		MongoCollection<Document> mongoCollection = getMongoCollection(
				mongoClient, db, collection);
		return mongoCollection.find().first();
	}
 
	private void InsertMultipleDocuments(MongoClient mongoClient, String db,
			String collection) throws MongoException {
		System.out.println("Enter InsertMultipleDocuments");
 
		MongoCollection<Document> mongoCollection = getMongoCollection(
				mongoClient, db, collection);
		mongoCollection.drop();
		List<Document> documents = new ArrayList<Document>();
		Document document1 = new Document();
		Document document2 = new Document();
		Document document3 = new Document();
 
		document1.append("name", "Narmadha");
		document2.append("name", "Mayuri");
		document3.append("name", "Srilatha");
 
		documents.add(document1);
		documents.add(document2);
		documents.add(document3);
 
		mongoCollection.insertMany(documents);
		MongoCursor<Document> cursor = mongoCollection.find().iterator();
 
		printDocuments(cursor);
		System.out.println("Exit InsertMultipleDocuments \n");
 
	}
 
	private void printDocuments(MongoCursor<Document> cursor) {
		if (cursor != null) {
 
			while (cursor.hasNext()) {
				Document document = cursor.next();
				System.out.println(document.toJson());
			}
 
		}
	}
 
	private void InsertOneDocument(MongoClient mongoClient, String db,
			String collection) throws MongoException {
 
		MongoCollection<Document> mongoCollection = getMongoCollection(
				mongoClient, db, collection);
 
		// drops the existing collection(table) ----- use this if only required
		// otherwise whole data in collection is erased.
		mongoCollection.drop();
 
		// inserting only one document(row) into the collection
		mongoCollection.insertOne(new Document()
		    .append("flight", "C30")
		    .append("variant",new Document()
                          .append("short_name", "PSLV-XL")
			  .append("long_name", "Polar Satellite Launch Vehicle With XL Configuration"))
		    .append("launched_date", "28 September 2015")
		    .append("manufacturer", new Document()
		          .append("short_name", "ISRO")
		          .append("long_name", "Indian Space Research Organisation")));
 
		System.out.println("Document inserted is  "
				+ mongoCollection.find().first().toJson());
 
	}
 
	private MongoCollection<Document> getMongoCollection(
			MongoClient mongoClient, String db, String collection) {
 
		// Get the mongodb logical database, if or if not exists.
		MongoDatabase mongoDatabase = mongoClient.getDatabase(db);
 
		// Get the mongo collection, if or if not collection exists.
		MongoCollection<Document> mongoCollection = mongoDatabase
				.getCollection(collection);
		return mongoCollection;
	}
 
	private MongoClient getMongoConnection() {
		return new MongoClient();
	}
 
}