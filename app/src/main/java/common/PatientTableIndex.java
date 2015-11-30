package common;

/**
 * Created by Noel on 10/14/15.
 */
public enum PatientTableIndex {
    INDEX_ID(0),
    INDEX_FIRST_NAME(1),
    INDEX_LAST_NAME(2),
    INDEX_AGE(3),
    INDEX_GENDER(4),
    INDEX_CREATOR(5);

    private int value;

    PatientTableIndex(int value){
        this.value = value;
    }
    public int toValue(){
        return this.value;
    }
}
