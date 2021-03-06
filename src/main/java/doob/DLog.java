package doob;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Class responsible for writing a log to a specified file.
 */
public final class DLog {

    private static volatile DLog uniqueInstance;

    private Writer writer;
    private String path;

    private static final String DEFAULT_LOG_FILE = "DooB.log";

    public static final String LOG_CREATED_MESSAGE = "Log file created. Path is ";
    public static final String ENCODING = "utf-8";

    private static final String TAG_PLAYER_INTERACTION = "-Player interaction- ";
    private static final String TAG_COLLISION = "-Collision- ";
    private static final String TAG_STATE = "-Event- ";
    private static final String TAG_APPLICATION = "-App- ";
    private static final String TAG_ERROR = "-ERROR- ";

    private static final String PROPERTIES_FILE = "DooB.properties";
    private static final String PROPERTY_LOG_ENABLED = "logEnabled";
    private static final String PROPERTY_LOG_TYPES_ENABLED = "logTypesEnabled";

    private boolean isLogOn;
    private Type[] typesEnabled;

    /**
     * Disable instantiation of the class.
     */
    private DLog() {
        invalidateProperties();
        this.path = DEFAULT_LOG_FILE;
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(path),
                            ENCODING
                    )
            );
            info(LOG_CREATED_MESSAGE + path, Type.APPLICATION);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get unique instance of DLog.
     * @return instance
     */
    public static DLog getInstance() {
        if (uniqueInstance == null) {
            synchronized (DLog.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new DLog();
                }
            }
        }
        return uniqueInstance;
    }

    /**
     * Specify where the log file should be written to. Overwrites the file of it already exists.
     * @param path The path the file should be written.
     */
    public void setFile(String path) {
        invalidateProperties();
        this.path = path;
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(path),
                            ENCODING
                    )
            );
            info(LOG_CREATED_MESSAGE + path, Type.APPLICATION);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update DLog state from properties file.
     */
    public void invalidateProperties() {
        Properties properties = new Properties();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(PROPERTIES_FILE);
            properties.load(fileInputStream);
        	fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        // Check whether log is enabled.
        isLogOn = properties.getProperty(PROPERTY_LOG_ENABLED).equals("yes");
        // Check which types are enabled.
        String typesProperty = properties.getProperty(PROPERTY_LOG_TYPES_ENABLED).toUpperCase();
        String[] typesString = typesProperty.split(",");
        typesEnabled = new Type[
                typesString.length
        ];
        for (int i = 0; i < typesString.length; i++) {
            for (Type type : Type.values()) {
            	typesString[i] = typesString[i].replace(" ", "");
                if (type.toString().equals(typesString[i])) {
                    typesEnabled[i] = type;
                }
            }
        }
    }

    /**
     * Empties the assigned file.
     * @throws IOException when an I/O error occurs.
     */
    public void emptyFile() throws IOException {
        setFile(path);
    }

    /**
     * Prints information log to console and to file.
     * @param text string to log.
     * @param type Type of log message to be written.
     */
    public void info(String text, Type type) {
        if (!isEnabled(type)) {
            return;
        }
        // Type tag
        text = getTag(type) + text;

        // Timestamp
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

    /**
     * Prints information log to console and to file.
     * @param text string to log.
     */
    public void info(String text) {
        info(text, Type.NONE);
    }

    /**
     * Prints error information to console and to file.
     * @param text message with the error.
     */
    public void e(String text) {
        info(text, Type.ERROR);
    }

    /**
     * Checks if the logging option is enabled for this type of log.
     * @param type the type that is checked
     * @return isEnabled boolean
     */
    private boolean isEnabled(Type type) {
        if (!isLogOn) {
            return false;
        }

        if (type == Type.NONE || type == Type.APPLICATION) {
            return true;
        }

        for (Type curType : typesEnabled) {
            if (curType == type) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get String representation of Tag.
     * @param type Type of tag.
     * @return The String associated with the tag.
     */
    private static String getTag(Type type) {
        switch (type) {
            case PLAYER_INTERACTION:
                return TAG_PLAYER_INTERACTION;
            case COLLISION:
                return TAG_COLLISION;
            case STATE:
                return TAG_STATE;
            case APPLICATION:
                return TAG_APPLICATION;
            case ERROR:
                return TAG_ERROR;
            default:
                return "";
        }
    }

    /**
     * Types of log messages.
     */
    public enum Type {
        PLAYER_INTERACTION,
        COLLISION,
        STATE,
        APPLICATION,
        ERROR,
        NONE;

        /**
         * Get String representation of the type.
         * @return the String.
         */
        public String toString() {
            return this.name().toUpperCase();
        }
    }

}
