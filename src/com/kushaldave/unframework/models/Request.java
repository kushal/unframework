package com.kushaldave.unframework.models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.CappedAt;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Map;

@Entity(value="requests", noClassnameStored=true, cap=@CappedAt(value=1024*1024*10, count=5000))
public class Request {
  @Id
  public ObjectId id;
  public String email;
  public String url;
  public String ip;
  public String referrer;
  public String userAgent;
  public Map<String, String> params;
  public boolean success;
}
