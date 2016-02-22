package com.kushaldave.unframework.models;

import com.kushaldave.unframework.gson.Exclude;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * A user of unframework.
 *
 * For simplicity, the user's oauth token is stored on the user, but to support multiple oauth consumers
 * the token should be normalized into its own collection with one per (user, consumer) pair.
 */
@Entity(value="users", noClassnameStored=true)

public class User {
  @Id
  public ObjectId id;
  public String name;

  @Exclude
  public String token;
}
