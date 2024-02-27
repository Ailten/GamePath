package be.gamepath.projectgamepath.enumeration;

import java.util.Arrays;
import java.util.List;

public enum Crud {
    CREATE("create"),
    READ_LIST("readList"),
    READ_DETAILS("read"),
    UPDATE("update"),
    DELETE("delete");

    //params.
    private String txtValue;

    //constructor.
    Crud(String txtValue) {
        this.txtValue=txtValue;
    }

    //getter.
    public String getTxtValue(){
        return this.txtValue;
    }

    //get enum crud from string.
    public static Crud getFromStr(String txtValue){
        return Arrays.stream(Crud.values())
                .filter(c->c.getTxtValue().equals(txtValue))
                .findFirst()
                .orElse(Crud.READ_DETAILS);
    }
}
