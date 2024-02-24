package be.gamepath.projectgamepath.enumeration;

import java.util.Arrays;
import java.util.List;

public enum PayementType {
    MAESTRO ("maestro"),
    MASTERCARD ("mastercard"),
    PAYPAL ("paypal");

    //params.
    private String txtValue;

    //constructor.
    PayementType(String txtValue) {
        this.txtValue=txtValue;
    }

    //getter.
    public String getTxtValue(){
        return this.txtValue;
    }

    //getter all enum.
    public static List<PayementType> getAll(){
        return Arrays.asList(PayementType.values());
    }

    //cast string to enum.
    public static PayementType stringToEnum(String txtValue){
        return Arrays.stream(PayementType.values())
                .filter(s->s.getTxtValue().equals(txtValue))
                .findFirst()
                .orElse(null);
    }
}
