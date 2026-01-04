package lyzo.karten.utility.logger;

public class Logger {

    public static final int DEBUG = 0;
    public static final int NORMAL = 1;
    public static final int ERROR = 2;

    private static int logLevel;

    private Logger() {}

    public static Logger getInstance() {
        return LoggerBuilder.INSTANCE;
    }

    private static class LoggerBuilder {
        private static final Logger INSTANCE = new Logger();
    }

    public static void setLogLevel(int logLevel) {
        Logger.logLevel = logLevel;
    }

    public void log(String message, int level) {
        if (level >= logLevel) {
            System.out.println(message);
        }
    }
}
