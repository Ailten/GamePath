package be.gamepath.projectgamepath.enumeration;

import java.util.Arrays;
import java.util.List;

public enum Tva {
    A_ZERO ("0", 0),
    B_SIX ("6", 6),
    C_DOUZE ("12", 12),
    D_VINGHTETUN ("21", 21);

    //params.
    private String txtValue;
    private int numValue;

    //constructor.
    Tva(String txtValue, int numValue) {
        this.txtValue=txtValue;
        this.numValue=numValue;
    }

    //getter.
    public String getTxtValue(){
        return this.txtValue;
    }
    public int getNumValue(){
        return this.numValue;
    }

    //getter all enum.
    public static List<Tva> getAll(){
        return Arrays.asList(Tva.values());
    }

    //cast string to enum.
    public static Tva stringToEnum(String txtValue){
        return Arrays.stream(Tva.values())
                .filter(t->t.getTxtValue().equals(txtValue))
                .findFirst()
                .orElse(null);
    }
}
