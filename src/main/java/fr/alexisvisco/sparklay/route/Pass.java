package fr.alexisvisco.sparklay.route;

import spark.Request;
import spark.Response;

@FunctionalInterface
public interface Pass {

    boolean pass(Request req, Response res);

}
