package socialmedia;

import java.io.Serializable;
import java.util.ArrayList; // had to add this for getAuthorID

public class Post implements Serializable {
    private int postID;
    private String author;
    private String message;

    public int getID(){
        return postID;
    }

    public String getAuthor(){
        return author;
    }

    public String getMessage(){
        return message;
    }

    public void setID(int inputID){
        postID = inputID;
    }

    public void setAuthor(String inputAuthor){
        author = inputAuthor;
    }

    public void setMessage(String inputMessage){
        message = inputMessage;
    }
    public int getAuthorID(ArrayList<Account> accountList) {
        int id = -1;
        
        for (Account account : accountList) {
            if (account.getUserHandle() == author) {
                id = account.getUserID();
            }
        }
        return id;
    }
}   
