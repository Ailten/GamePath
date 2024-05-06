package be.gamepath.projectgamepath.managedBeans;

import be.gamepath.projectgamepath.utility.Utility;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class HistoricalBean implements Serializable {

    //array of string of page previously visited.
    private String[] historic;
    private int index;

    @PostConstruct
    public void init(){
        this.historic = new String[]{"","","","","","","",""};
        this.index = 20;
    }

    /**
     * save a page visited in array historic.
     * @param url string of page visited.
     */
    public void saveNewPageHistoric(String url){
        PrimeFaces.current().executeScript("eventLoadPage(\'"+url+"\')");
        if(url.equals(historic[index%historic.length]))
            return;
        historic[(++index)%historic.length]=url;
    }

    /**
     * return the string of previous page saved (and decrement the index).
     * @return string of previous page.
     */
    public String backLastPageHistoric(){
        return historic[(--index)%historic.length];
    }

    /**
     * return string of the current page load.
     * @return string of current page.
     */
    public String currentPage() { return historic[(index)%historic.length]; }

}
