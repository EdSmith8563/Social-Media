package socialmedia;

import java.io.Serializable;

public class Account implements Serializable {
    // Variable Definitions
    private int userID;
    private String userHandle;
    private String userDesc;

    public Account(int inputID,String inputHandle, String inputDesc){
        userID = inputID;
        userHandle = inputHandle;
        userDesc = inputDesc;
    }

    public int getUserID(){
        return userID; 
    }

    public String getUserHandle(){
        return userHandle; 
    }

    public String getUserDesc(){
        return userDesc; 
    }

    public void setUserHandle(String inputHandle){
        userHandle = inputHandle; 
    }

    public void setUserDesc(String inputDesc){
        userDesc = inputDesc; 
    }
}