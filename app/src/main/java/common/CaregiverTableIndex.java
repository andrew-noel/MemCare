package common;

/**
 * Created by Noel on 9/20/15.
 */
public enum CaregiverTableIndex {
        INDEX_ID(0),
        INDEX_FIRST_NAME(1),
        INDEX_LAST_NAME(2),
        INDEX_USER_NAME(3),
        INDEX_PASSWORD(4);

    private int value;

    CaregiverTableIndex(int value){
        this.value = value;
    }
    public int toValue(){
        return this.value;
    }

}
