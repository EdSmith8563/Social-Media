package socialmedia;

public class CommentPost extends OriginalPost {
    private int parentPostID;

    public CommentPost(int postID, String author, String message, int inputParentPostID){
        super(postID, author, message);
        parentPostID = inputParentPostID;
    }

    public int getParentPostID(){
        return parentPostID;
    }

    public void setParentPostID(int inputParentPostID){
        parentPostID = inputParentPostID;
    }
}