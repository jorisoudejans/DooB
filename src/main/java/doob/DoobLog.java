package doob;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
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
public final class DooBLog {

    private static Writer writer;

    /**
     * Disable instantiation of the class.
     */
    private DooBLog() { }

    /**
     * Specify where the log file should be written to. Overwrites the file of it already exists.
     * @param path The path the file should be written.
     * @throws FileNotFoundException when the specified path can't be written to.
     */
    public static void setFile(String path) throws FileNotFoundException {
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(path),
                            "utf-8"
                    )
            );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Empties the current file.
     * @throws IOException when an I/O error occurs.
     */
    public static void emptyFile() throws IOException {
        writer.write("");
        writer.flush();
    }

    /**
     * Prints information log to console and to file.
     * @param text string to log.
     * @throws IOException when an I/O error occurs.
     */
    public static void i(String text) throws IOException {
        Date now = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        text = dateFormat.format(now) + ": " + text;
        writer.append(text).append('\n');
        System.out.print(text);
        writer.flush();
    }

}
