package be.gamepath.projectgamepath.utility;

import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.faces.context.FacesContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    /**
     * function to convert a localdatetime to a date.
     * @param localDateTime
     * @return
     */
    public static Date castLocalDateTimeToDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

    /**
     * function to convert a date to a localdatetime
     */
    public static LocalDateTime castDateToLocalDateTime(Date date)
    {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }


    /**
     * function to convert a date to a string
     * @param date object date.
     * @param pattern pattern of date you need (example: "yyyy-MM-dd hh:mm:ss").
     * @return date in string.
     */
    public static String castDateToString(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * cast a float to a string, and normalize the decimals.
     * @param floatValue base value.
     * @param decimalAsk quantity of decimal.
     * @return string of value.
     */
    public static String castFloatToString(float floatValue, int decimalAsk) {
        String out = Float.toString(floatValue);

        Pattern patternDecimal = Pattern.compile("[.][0-9]{1,}$");
        Matcher matcher;
        String decimalGet;
        while(true) {
            matcher = patternDecimal.matcher(out);
            if(!matcher.find())
                break;
            decimalGet = matcher.group(0);
            if(decimalGet.length()-1 > decimalAsk){ //to much decimals.
                if(decimalGet.length()-2 == 0){
                    out = out.substring(0, out.length()-2);
                    break; //stop if 0 decimal.
                }
                out = out.substring(0, out.length()-1);
                continue;
            }
            if(decimalGet.length()-1 < decimalAsk){ //not enough decimals.
                out += "0";
                continue;
            }
            break;
        }

        return out;
    }


    /**
     * make a date by sending year, month and day.
     * @param year year of date.
     * @param month month of date.
     * @param day day of date.
     * @return a Date.
     */
    public static Date makeDate(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * return year of an object date.
     */
    public static int dateGetYear(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    /**
     * return month of an object date.
     */
    public static int dateGetMonth(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }
    /**
     * return day of an object date.
     */
    public static int dateGetDay(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * add many days to an object date.
     * @param date object date.
     * @param days amount of days to add.
     * @return object date incremented.
     */
    public static Date dateAddDay(Date date, int days){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
    public static Date dateAddDay(Date date){
        return dateAddDay(date, 1);
    }

    /**
     * add many days to an object date.
     * @param date object date.
     * @param month amount of months to add.
     * @return object date incremented.
     */
    public static Date dateAddMonth(Date date, int month){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }
    public static Date dateAddMonth(Date date){
        return dateAddMonth(date, 1);
    }

}
