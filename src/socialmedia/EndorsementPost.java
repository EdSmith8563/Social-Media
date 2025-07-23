package socialmedia;

public class EndorsementPost extends Post {
    private int parentPostID;

    public EndorsementPost(int postID, String author, Post post){
        parentPostID = post.getID();
        super.setID(postID);
        super.setAuthor(author);
        super.setMessage( "EP@ " + post.getAuthor() + ": " + post.getMessage());
    }
    public int getParentPostID(){
        return parentPostID;
    }
}
