package ua.edu.sumdu.tss.mudbms;

import io.javalin.Javalin;
import io.javalin.core.util.JavalinLogger;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import ua.edu.sumdu.tss.mudbms.core.storage_engine.CachedStorage;
import ua.edu.sumdu.tss.mudbms.core.transaction_engine.TransactionFactory;
import ua.edu.sumdu.tss.mudbms.core.transaction_engine.WalDifferableEngine;
import ua.edu.sumdu.tss.mudbms.utils.Keys;

public class Server {

    private final static String LOGO = """
              
              _____
            < μDBMS >
              -----
                     \\   ^__^
                      \\  (oo)\\_______
                         (__)\\       )\\/\\
                             ||----w |
                             ||     ||

            """;

    private final Javalin app = Javalin.create(
                    config -> {
                        config.addStaticFiles("/public", Location.CLASSPATH);
                        config.registerPlugin(new OpenApiPlugin(getOpenApiOptions()));
                        config.showJavalinBanner = false;
                    })
            .before(context -> JavalinLogger.info("[" + context.method() + "] " + context.url()))
            .get("/", Api::start)
            .delete("/", Api::rollback)
            .patch("/", Api::commit)
            .get("/{key}", Api::read)
            .post("/{key}", Api::write);

    public static void main(String[] args) {
        //if (args.length < 1) {
        //    throw new RuntimeException("File with properties must be specified on startup");
        //}
        //var file = new File(args[0]);
        //Keys.loadParams(file);

        new Server().start(Keys.APP_PORT);
    }

    public void start(final int port) {
        JavalinLogger.info(LOGO);
        TransactionFactory.setup(new WalDifferableEngine(new CachedStorage("data.file")));
        this.app.start(port);
    }

    public void stop() {
        this.app.stop();
    }

    private OpenApiOptions getOpenApiOptions() {
        Info applicationInfo = new Info()
                .version("1.0")
                .description("Elephant");
        return new OpenApiOptions(applicationInfo)
                .path("/swagger-docs")
                .swagger(new SwaggerOptions("/swagger").title("My Swagger Documentation"))
                .reDoc(new ReDocOptions("/redoc").title("My ReDoc Documentation"))
                .activateAnnotationScanningFor("edu.sumdu.tss.mudbms.api");

    }
}
