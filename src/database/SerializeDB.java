package database;

import base.BaseModel;
import database.exceptions.FailReadException;
import database.exceptions.FailWriteException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods to create, read and write .dat files.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class SerializeDB {

    /**
     * Path to the directory that stores all .dat files.
     */
    private static final Path ROOT =
        Paths.get(System.getProperty("user.dir"), "src", "database", "db");

    /**
     * Reads the data saved in a .dat file into a List of Objects.
     *
     * @param filename Name of the file that contains the data to be read. The file must be of .dat type in database/db.
     * @return List of objects saved in the file.
     * @throws FailReadException If the user tries to read data from a file that does not exist.
     */
    public static List readSerializedObject(String filename) throws FailReadException {
        List data = new ArrayList<>();
        FileInputStream fin = null;
        ObjectInputStream in = null;

        String filepath = getFilePath(filename);

        try {
            createFileIfNotExists(filepath);

            fin = new FileInputStream(filepath);
            if (fin.available() > 0) {
                in = new ObjectInputStream(fin);
                data = (ArrayList) in.readObject();
                fin.close();
            }
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new FailReadException();
        }

        return data;
    }

    /**
     * Writes a List of Objects into a .dat file.
     *
     * @param filename Name of the file to write the data. The file must be of .dat type in database/db.
     * @param list List of Objects to be saved into the file.
     * @throws FailWriteException If the user tries to save an invalid list of objects into a file.
     */
    public static void writeSerializeObject(String filename, List list) throws FailWriteException {
        FileOutputStream fout = null;
        ObjectOutputStream out = null;

        String filepath = getFilePath(filename);

        try {
            createFileIfNotExists(filepath);

            fout = new FileOutputStream(filepath);
            out = new ObjectOutputStream(fout);
            out.writeObject(list);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FailWriteException();
        }
    }

    /**
     * Searches for a BaseModel object in an ArrayList by ID.
     *
     * @param items ArrayList of BaseModel objects.
     * @param id ID of Object of type BaseModel to be searched for.
     * @param <T> A generic type that extends BaseModel.
     * @return Object if it is found. Else, null.
     */
    public static <T extends BaseModel> T findById(ArrayList<T> items, int id) {
        for (BaseModel item: items) {
            if (item.getId() == id) {
                return (T)item;
            }
        }
        return null;
    }

    /**
     * Generates an ID for a new BaseModel object.
     *
     * @param items ArrayList of BaseModel objects.
     * @param <T> A generic type that extends BaseModel.
     * @return ID of type int.
     */
    public static <T extends BaseModel> int generateId(ArrayList<T> items) {
        if (items.size() == 0) {
            return 1;
        }
        T latestItem = items.get(items.size() - 1);
        return latestItem.getId() + 1;
    }

    /**
     * Creates a new file at filepath if it does not exist.
     *
     * @param filepath Path to file (including the file name)
     * @throws IOException If there is an input or output exception.
     */
    private static void createFileIfNotExists(String filepath) throws IOException {
        File file = new File(filepath);
        file.createNewFile();
    }

    /**
     * Checks if a database file exists in the database folder db.
     *
     * @param filename Name of the files to be checked.
     * @return True if the file exists. Else, false.
     */
    public static boolean checkIfFileExists(String filename) {
        String filepath = getFilePath(filename);
        File file = new File(filepath);
        return file.exists();
    }

    /**
     * Returns the absolute path to a file inside database/db.
     *
     * @param filename Name of the file to be opened. It must be of .dat type in database/db.
     * @return Absolute path to the file.
     */
    private static String getFilePath(String filename) {
        return Paths.get(ROOT.toString(), filename).toString();
    }
}
