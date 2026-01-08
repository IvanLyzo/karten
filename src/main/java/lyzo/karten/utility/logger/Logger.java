package lyzo.karten.utility.logger;

public class Logger {

    public static final int INFO = 0;
    public static final int DEBUG = 1;
    public static final int NORMAL = 2;
    public static final int ERROR = 3;

    private static int logLevel;

    private Logger() {}

    public static void setLogLevel(int logLevel) {
        Logger.logLevel = logLevel;
    }

    public static void log(String message, int level) {
        if (level >= logLevel) {
            System.out.println(message);
        }
    }
}
