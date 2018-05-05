package fr.alexisvisco.sparklay.util;

import spark.Request;
import spark.Response;

import java.util.HashMap;

@FunctionalInterface
public interface Renderer {

    String render(Request req, Response res, HashMap<Object, Object> map, String view);
}
