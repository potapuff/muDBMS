package ua.edu.sumdu.tss.mudbms.core.transaction_engine;

public class TransactionCoordinator {
    private static TransactionEngine instance;

    public static synchronized TransactionEngine getInstance() {
        if (instance == null) {
            throw new IllegalArgumentException("You need to run TransactionCoordinator.setup() before TransactionCoordinator.getInstance()");
        }
        return instance;
    }

    public static synchronized void setup(TransactionEngine engine) {
        instance = engine;
    }
}
