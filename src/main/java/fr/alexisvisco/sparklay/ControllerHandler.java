package fr.alexisvisco.sparklay;

import fr.alexisvisco.sparklay.route.Before;
import fr.alexisvisco.sparklay.route.Pass;
import fr.alexisvisco.sparklay.route.Route;
import fr.alexisvisco.sparklay.util.Renderer;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.reflections.ReflectionUtils.*;

public class ControllerHandler {


    private Set<Class<? extends Controller>> controllers = new HashSet<>();
    private Renderer renderer;

    public ControllerHandler(Renderer renderer) {
        this.renderer = renderer;
    }

    public void handle(Class<? extends Controller> controller) {
        controllers.add(controller);
    }

    public void register() {
        for (Class<? extends Controller> controller : controllers) {
            Set<Method> methods = getAllMethods(controller, withReturnType(Object.class), withAnnotation(Route.class));
            System.out.printf("%d routes for controller %s:\n", methods.size(), controller.getSimpleName());
            for (Method method : methods) {
                Route annotation = method.getAnnotation(Route.class);

                System.out.printf(" + %-10s %s\n", annotation.type().name(), annotation.value());
                registerMiddlewares(method, annotation.value());
                registerIntoSpark(controller, method, annotation);
            }
        }
    }

    private void registerMiddlewares(Method method, String path) {
        Before b = method.getAnnotation(Before.class);
        if (b != null) {
            Spark.before(path, (req, res) -> {
                for (Class<? extends Pass> aClass : b.value()) {
                    Set<Constructor> constructors = getConstructors(aClass, withParameters());
                    if (!constructors.isEmpty()) {
                        if (!((Pass)constructors.iterator().next().newInstance()).pass(req, res)) {
                            res.redirect(b.redirect());
                            Spark.halt();
                            return;
                        }
                    }
                }
            });

        }
    }

    private void registerIntoSpark(Class<? extends Controller> controller, Method method, Route route) {
        Constructor ct = getConstructors(controller, withParameters(Request.class, Response.class, Renderer.class)).iterator().next();
        route.type().dispatch(route.value(), (req, res) -> exec(route, ct, method, req, res));
    }

    private Object exec(Route route, Constructor ct, Method method, Request request, Response response) {
        try {
            Controller ctrl = (Controller) ct.newInstance(request, response, this.renderer);
            ctrl.setCurrentView(route.view());
            ctrl.model.set("title", route.title());
            return method.invoke(ctrl);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
