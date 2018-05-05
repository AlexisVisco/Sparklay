package fr.alexisvisco.sparklay;

import fr.alexisvisco.sparklay.util.MapBuilder;
import fr.alexisvisco.sparklay.util.Renderer;
import spark.Request;
import spark.Response;
import spark.Spark;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Controller {

    public Request req;
    public Response res;

    protected MapBuilder<String, Object> model = new MapBuilder<>();
    private Renderer renderer;
    public String currentView = "index.vm";

    public Controller(Request req, Response res, Renderer renderer) {
        this.req = req;
        this.res = res;
        this.renderer = renderer;
    }

    void setCurrentView(String currentView) {
        this.currentView = currentView;
    }

    void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public Controller setTitle(String title)
    {
        model.put("title", title);
        return this;
    }

    public Controller setSuccess(String message)
    {
        model.put("success", message);
        return this;
    }

    public Controller setErrors(Map<String, String> errors)
    {
        model.put("error", errors);
        backForm();
        return this;
    }

    public Controller backForm()
    {
        HashMap<String, String> fillForm = new HashMap<>();
        req.queryParams().forEach(e -> fillForm.put(e, req.queryParams(e)));
        model.put("fillForm", fillForm);
        return this;
    }

    public Controller singleError(String error)
    {
        Map<String, String> errors = new HashMap<>();
        errors.put("Erreur", error);
        model.put("error", errors);
        backForm();
        return this;
    }

    public String render()
    {
        return renderer.render(req, res, model, currentView);
    }


    //------------------------------------------------------------------------------
    //
    // REQUEST
    //
    //------------------------------------------------------------------------------

    public Map<String, String> getUrlParams() {
        return req.params();
    }

    public String getUrlParam(String param) {
        return req.params(param);
    }

    public boolean paramUrlNullOEmpty(String param) {
        String s = getUrlParam(param);
        return (s == null || s.isEmpty());
    }

    public String url() {
        return req.url();
    }

    public String reqBody() {
        return req.body();
    }

    public String getPostParam(String queryParam) {
        return req.queryParams(queryParam);
    }

    public boolean paramPostNullOEmpty(String queryParam) {
        String s = getPostParam(queryParam);
        return (s == null || s.isEmpty());
    }

    public String[] getPostParamsValues(String queryParam) {
        return req.queryParamsValues(queryParam);
    }

    public Set<String> getPostParams() {
        return req.queryParams();
    }

    public String getHeader(String header) {
        return req.headers(header);
    }

    public Set<String> headers() {
        return req.headers();
    }

    public void session(String attribute, String value) {
        req.session().attribute(attribute, value);
    }

    public String session(String attribute) {
        return req.session().attribute(attribute);
    }

    public Set<String> sessions() {
        return req.attributes();
    }

    //------------------------------------------------------------------------------
    //
    // RESPONSE
    //
    //------------------------------------------------------------------------------

    public void status(int status)
    {
        res.status(status);
    }

    public void status(HttpStatus s)
    {
        res.status(s.value());
    }

    public int status()
    {
        return res.status();
    }

    public void body(String body)
    {
        res.body(body);
    }

    public String body()
    {
        return res.body();
    }

    public HttpServletResponse raw()
    {
        return res.raw();
    }

    public void redirect(String location)
    {
        res.redirect(location);
        Spark.halt();
    }

    public void redirect(String location, int httpStatusCode)
    {
        res.redirect(location, httpStatusCode);
        Spark.halt();
    }

    public void header(String header, String value)
    {
        res.header(header, value);
    }

    public void cookie(String name, String value)
    {
        res.cookie(name, value);
    }

    public void cookie(String name, String value, int maxAge)
    {
        res.cookie(name, value, maxAge);
    }

    public void cookie(String name, String value, int maxAge, boolean secured) {
        res.cookie(name, value, maxAge, secured);
    }

    public void cookie(String path, String name, String value, int maxAge, boolean secured, boolean httpOnly) {
        res.cookie(path, name, value, maxAge, secured, httpOnly);
    }

    public void removeCookie(String name) {
        res.removeCookie(name);
    }
}
