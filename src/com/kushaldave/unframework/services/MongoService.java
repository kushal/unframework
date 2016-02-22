package com.kushaldave.unframework.services;

import com.kushaldave.unframework.models.Hello;
import com.kushaldave.unframework.models.Request;
import com.kushaldave.unframework.models.User;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.logging.MorphiaLoggerFactory;
import org.mongodb.morphia.logging.slf4j.SLF4JLoggerImplFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Mediates interaction with the database and contains connection state.
 */
public class MongoService {

  private final Datastore datastore;
  public final ExecutorService executor;

  public MongoService() {
    MorphiaLoggerFactory.registerLogger(SLF4JLoggerImplFactory.class);

    String uriString = System.getenv("MONGOLAB_URI");
    if (uriString == null || uriString.equals("")) {
      uriString = "mongodb://localhost/answerbox";
    }
    MongoClientURI uri = new MongoClientURI(uriString);
    MongoClient mongoClient = new MongoClient(uri);
    Morphia m = new Morphia();
    //m.map(Recipe.class, Activity.class, User.class, UserRecipe.class);
    this.datastore = m.createDatastore(mongoClient, uri.getDatabase());
    this.executor = Executors.newSingleThreadExecutor();
  }

  public User userFromToken(String token) {
    return datastore.find(User.class, "token", token).get();
  }

  public void saveRequest(Request r) {
    datastore.save(r);
  }


  public User getUser() {
    return datastore.find(User.class).get();
  }

  public Hello getHello(ObjectId objectId) {
    return datastore.find(Hello.class, "id", objectId).get();
  }

  public List<Hello> getHellos() {
    return datastore.find(Hello.class).asList();
  }
}
