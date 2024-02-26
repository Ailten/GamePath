package be.gamepath.projectgamepath.utility;

import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.faces.context.FacesContext;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Utility {

    /**
     * function to debug a string in console.
     * @param message
     * @return
     */
    public static void debug (String message){
        Logger log = Logger.getAnonymousLogger();
        log.info("debug ----------------> "+message);
    }

    /**
     * function to hash a password.
     * @param password
     * @return
     */
    public static String hashPassword (String password){
        return BCrypt.withDefaults().hashToString(10, password.toCharArray());
    }

    /**
     * function to compare a password and a password hashed.
     * @param password
     * @param passwordHashed
     * @return
     */
    public static boolean passwordEqualsHash (String password, String passwordHashed){
        return BCrypt.verifyer().verify(password.toCharArray(), passwordHashed).verified;
    }


    /**
     * function to get a string from language file.
     * @param keyOfString key of string ask.
     * @return
     */
    public static String stringFromI18N (String keyOfString) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("language.messages", context.getViewRoot().getLocale());
        return text.getString(keyOfString);
    }

}
