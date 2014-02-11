import java.io.*;
import java.net.URLDecoder;
import java.text.*;
import java.util.*;

public final class Utils {
    public static String getExecutingPath() {
        try {
            String path = (new Utils()).getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            return URLDecoder.decode(path, "UTF-8").substring(1); // Removes leading '/'
        } catch (UnsupportedEncodingException e) {
            return "UnsupportedEncodingException";
        }
    }

    public static String getCurrentDateFull() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        return dateFormat.format(calendar.getTime());
    }

    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}