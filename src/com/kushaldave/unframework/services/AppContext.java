package com.kushaldave.unframework.services;

public class AppContext {
  public final MongoService mongoService;

  public AppContext() {
    this.mongoService = new MongoService();
  }
}
