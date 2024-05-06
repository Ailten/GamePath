package be.gamepath.projectgamepath.utility;

public class EntityGenerique {

    //getter of primary key element (need override).
    public int getId() throws Exception { throw new Exception("EntityGeneric was not override"); }


    //generic override of equals function.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityGenerique that = (EntityGenerique) o;
        try{
            return getId() == that.getId();
        }catch(Exception e){
            Utility.debug(e.getMessage());
            return false;
        }
    }

}
