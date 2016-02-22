package com.kushaldave.unframework.gson;

import com.google.gson.*;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Convert an object to JSON using GSON with our custom modifications.
 *
 * Since we use this class a lot, we cut ourselves some slack and use a cute name.
 */
public class Jsonifier {

  public static String toJson(Object o) {
    return new GsonBuilder()
        .registerTypeAdapter(ObjectId.class, new ObjectIdSerializer())
        .registerTypeAdapter(Date.class, new DateSerializer())
        .addSerializationExclusionStrategy(new AnnotationExclusionStrategy())
        .create()
        .toJson(o);
  }

  private static class ObjectIdSerializer implements JsonSerializer<ObjectId> {
    public JsonElement serialize(ObjectId src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.toString());
    }
  }
  private static class DateSerializer implements JsonSerializer<Date> {
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.getTime() / 1000);
    }
  }

  private static class AnnotationExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
      return f.getAnnotation(Exclude.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
      return false;
    }
  }
}
