package be.gamepath.projectgamepath.managedBeans;

import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class HistoricalBean implements Serializable {

    private String[] historic;
    private int index;

    @PostConstruct
    public void init(){
        this.historic = new String[]{"","","","","","","",""};
        this.index = 20;
    }

    public void saveNewPageHistoric(String url){
        PrimeFaces.current().executeScript("eventLoadPage(\'"+url+"\')");
        if(url.equals(historic[index%historic.length]))
            return;
        historic[(++index)%historic.length]=url;
    }

    public String backLastPageHistoric(){
        return historic[(--index)%historic.length];
    }

    public String currentPage() { return historic[(index)%historic.length]; }


}
