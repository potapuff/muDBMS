package ua.edu.sumdu.tss.mudbms.core.engine;

public class Engine {
    private static Storage instance;

    public static synchronized Storage getInstance() {
        if (instance == null) {
            throw new IllegalArgumentException("You need to run Engine.setup() before Emgine.getInstance()");
        }
        return instance;
    }

    public static synchronized void setup(Storage storage) {
        instance = storage;
    }
}
