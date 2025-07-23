package socialmedia;

import java.util.ArrayList;

public class OriginalPost extends Post {
    
    private int commentCount, endorsementCount;

    public OriginalPost(int postID, String author, String message){
        super.setID(postID);
        super.setAuthor(author);
        super.setMessage(message);
    }
    
    public int getCommentCount(ArrayList<Post> postList){
        commentCount = 0;
        for(Post post : postList){
            if(post instanceof CommentPost){
                CommentPost comment = (CommentPost) post;
                if(comment.getParentPostID() == super.getID()){
                    commentCount += 1;
                }
            }
        }
        return commentCount;
    }

    public int getEndorsementCount(ArrayList<Post> postList){
        endorsementCount = 0;
        for(Post post : postList){
            if(post instanceof EndorsementPost){
                EndorsementPost endorsement = (EndorsementPost) post;
                if(endorsement.getParentPostID() == super.getID()){
                    endorsementCount += 1;
                }
            }
        }
        return endorsementCount;
    }
}
