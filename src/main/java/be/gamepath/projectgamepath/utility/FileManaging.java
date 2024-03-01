package be.gamepath.projectgamepath.utility;

import org.primefaces.model.file.UploadedFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManaging {

    //location of folder for file.
    private static final String FOLDER_PATH_DOWNLOAD = "C:/wamp/www/imageFolderLocalHost/";
    private static final String FOLDER_PATH_APPLY = "http://localhost/imageFolderLocalHost/";
    private static final String DEFAULT_FILE = "default.png";


    public static boolean saveNewFile(UploadedFile uploadingFile){
        return FileManaging.saveNewFile(uploadingFile, null);
    }

    //save a file (from input file).
    public static boolean saveNewFile(UploadedFile uploadingFile, String pathFolder){
        boolean out = false;

        //instance file and path.
        File file = new File(
                FOLDER_PATH_DOWNLOAD +
                (pathFolder != null? pathFolder + "/": "") +
                uploadingFile.getFileName()
        );
        Path filePath = file.toPath();

        //delete previous file if existing.
        if(Files.exists(filePath)){
            file.deleteOnExit();
        }

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(uploadingFile.getContent());
            out = true;
        } catch (IOException e) {
            Utility.debug("error into saveNewFile : " + e.getMessage());
        }

        return out;
    }



    //get seed of file in folder download.
    public static String getUrlForApply(String nameFile, String pathFolder){
        return getUrlForApply(pathFolder + "/" + nameFile);
    }
    public static String getUrlForApply(String nameFile){
        return FOLDER_PATH_APPLY+nameFile;
    }
    public static String getUrlForApply(UploadedFile uploadingFile){
        return getUrlForApply(uploadingFile.getFileName());
    }
    public static String getUrlForApply(UploadedFile uploadingFile, String pathFolder){
        return getUrlForApply(pathFolder + "/" + uploadingFile.getFileName());
    }
    public static String getDefaultUrlForApply(){
        return getUrlForApply(DEFAULT_FILE);
    }

}
