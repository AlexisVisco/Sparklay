import fr.alexisvisco.sparklay.route.Pass;
import spark.Request;
import spark.Response;

public class Midleware implements Pass {
    @Override
    public boolean pass(Request req, Response res) {
        System.out.println("Yeah");
        return false;
    }
}
