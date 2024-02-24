package be.gamepath.projectgamepath.utility;

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

}
