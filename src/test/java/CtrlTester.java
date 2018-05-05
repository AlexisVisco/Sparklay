import fr.alexisvisco.sparklay.Controller;
import fr.alexisvisco.sparklay.ControllerHandler;
import fr.alexisvisco.sparklay.HttpStatus;
import fr.alexisvisco.sparklay.route.Before;
import fr.alexisvisco.sparklay.route.Method;
import fr.alexisvisco.sparklay.route.Route;
import fr.alexisvisco.sparklay.util.Renderer;
import spark.Request;
import spark.Response;
import spark.Spark;

public class CtrlTester extends Controller {

    public CtrlTester(Request req, Response res, Renderer renderer) {
        super(req, res, renderer);
    }

    @Route(value = "/")
    public Object route0() {
        setSuccess("Yeah !");
        return "c le hom";
    }

    @Before(Midleware.class)
    @Route(value = "/home/test")
    public Object route1() {
        setSuccess("Yeah !");
        return "yeah";
    }

    @Route(value = "/home/test/1",
           type = Method.PUT,
           view = "test/admin.vm",
           title = "Super value 2"
    )
    public Object route2() {
        status(HttpStatus.SERVICE_UNAVAILABLE);
        return "value 1";
    }

    @Route(value = "/home/test/2",
            type = Method.GET)
    public Object route3() {
        cookie("", "");
        removeCookie("");
        return "2";
    }

    @Route(value = "/home/test/3", type = Method.OPTIONS)
    public Object route4() {
        return "3";
    }

    @Route(value = "/home/test/4", type = Method.POST)
    public Object route5() {
        return "4";
    }

    public static void main(String[] args) {
        Spark.port(2000);
        ControllerHandler controllerHandler = new ControllerHandler(null);
        controllerHandler.handle(CtrlTester.class);

        controllerHandler.register();
    }
}
