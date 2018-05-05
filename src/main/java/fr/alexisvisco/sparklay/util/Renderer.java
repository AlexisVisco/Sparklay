package fr.alexisvisco.sparklay.util;

import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
public interface Renderer {

    String render(Request req, Response res, Map<String, Object> map, String view);
}
