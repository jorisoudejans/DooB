package doob;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Date;

/**
 * Class responsible for writing a log to a specified file.
 */
public final class DLog {

    private static Writer writer;
    private static String path;

    public static final String LOG_CREATED_MESSAGE = "Log file created. Path is ";
    public static final String ENCODING = "utf-8";

    /**
     * Disable instantiation of the class.
     */
    private DLog() { }

    /**
     * Specify where the log file should be written to. Overwrites the file of it already exists.
     * @param path The path the file should be written.
     * @throws IOException when an I/O error occurs.
     */
    public static void setFile(String path) throws IOException {
        DLog.path = path;
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(path),
                            ENCODING
                    )
            );
            i(LOG_CREATED_MESSAGE + path);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Empties the assigned file.
     * @throws IOException when an I/O error occurs.
     */
    public static void emptyFile() throws IOException {
        setFile(path);
    }

    /**
     * Prints information log to console and to file.
     * @param text string to log.
     */
    public static void i(String text) {
        Date now = new Date();
        DateFormat dateFormat = DateFormat.getTimeInstance();
        text = dateFormat.format(now) + ": " + text;
        try {
            writer.append(text).append('\n');
            System.out.println(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
