package be.gamepath.projectgamepath.enumeration;

import java.util.Arrays;
import java.util.List;

public enum MultyPlayer {
    A_SINGLE ("single"),
    B_MULTY ("multy"),
    C_SINGLEANDMULTY ("single and multy");

    //params.
    private String txtValue;

    //constructor.
    MultyPlayer(String txtValue) {
        this.txtValue=txtValue;
    }

    //getter.
    public String getTxtValue(){
        return this.txtValue;
    }

    //getter all enum.
    public static List<MultyPlayer> getAll(){
        return Arrays.asList(MultyPlayer.values());
    }

    //cast string to enum.
    public static MultyPlayer stringToEnum(String txtValue){
        return Arrays.stream(MultyPlayer.values())
                .filter(s->s.getTxtValue().equals(txtValue))
                .findFirst()
                .orElse(null);
    }
}
