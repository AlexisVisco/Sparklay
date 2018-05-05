package fr.alexisvisco.sparklay.route;

import spark.Filter;
import spark.Route;
import spark.Spark;

public enum Method {

    GET,
    POST,
    PUT,
    PATCH,
    DELETE,
    TRACE,
    OPTIONS,

    BEFORE,
    AFTER;

    public void dispatch(String path, Route r) {
        if (this == BEFORE || this == AFTER)
            intoSparkMidleWare(path, (Filter) r);
        else
            intoSpark(path, r);
    }

    public void intoSpark(String path, Route r) {
        switch (this) {
            case GET:
                Spark.get(path, r);
                break;
            case POST:
                Spark.post(path, r);
                break;
            case PUT:
                Spark.put(path, r);
                break;
            case PATCH:
                Spark.patch(path, r);
                break;
            case DELETE:
                Spark.delete(path, r);
                break;
            case TRACE:
                Spark.trace(path, r);
                break;
            case OPTIONS:
                Spark.options(path, r);
                break;
            default:
                break;
        }
    }

    public void intoSparkMidleWare(String path, Filter filter) {
        switch (this) {
            case BEFORE:
                Spark.before(path, filter);
                break;
            case AFTER:
                Spark.after(path, filter);
                break;
        }
    }

}
