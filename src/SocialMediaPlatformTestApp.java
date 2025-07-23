import java.io.IOException;

import socialmedia.*;
/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMediaPlatformTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new SocialMedia();
		
		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		
		Integer id, postid;
		try {
			id = platform.createAccount("my_handle");
			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";

			platform.createAccount("myhandle");
		
			postid = platform.createPost("my_handle", "test");
			assert (platform.getTotalOriginalPosts() == 1) : "total original posts not incremented correctly";

			platform.endorsePost("myhandle", postid);
			assert (platform.getTotalEndorsmentPosts() == 1) : "total endorsement posts not incremented correctly";

			platform.createPost("myhandle", "test");
			assert (platform.getTotalOriginalPosts() == 2) : "total original posts not incremented correctly";

			platform.commentPost("myhandle", postid, "comment1");
			platform.commentPost("myhandle", postid, "comment2");
			platform.commentPost("myhandle", 4, "comment3");
			platform.commentPost("myhandle", 6, "comment4");
			platform.commentPost("myhandle", 4, "comment5");

			assert (platform.getTotalCommentPosts() == 5) : "total endorsement posts not incremented correctly";
			
			System.out.println("-----------------------------------------\nPlatform before saving:\n-----------------------------------------\n");

			System.out.println("Number of accounts: "+platform.getNumberOfAccounts());
			System.out.println("Number of original posts: "+platform.getTotalOriginalPosts());
			System.out.println("Number of comments: "+platform.getTotalCommentPosts());
			System.out.println("Number of endorsements: "+platform.getTotalEndorsmentPosts());
			System.out.println("Most endorsed post: "+platform.getMostEndorsedPost());
			System.out.println("Most endorsed account: "+platform.getMostEndorsedAccount()+"\n");

			System.out.println(platform.showPostChildrenDetails(postid)+"\n");

			platform.savePlatform("file.txt");

			platform.removeAccount(id);
			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";

			platform.removeAccount("myhandle");
			assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";

			System.out.println("-----------------------------------------\nPlatform after saving, accounts removed:\n-----------------------------------------\n");

			System.out.println("Number of accounts: "+platform.getNumberOfAccounts());
			System.out.println("Number of original posts: "+platform.getTotalOriginalPosts());
			System.out.println("Number of comments: "+platform.getTotalCommentPosts());
			System.out.println("Number of endorsements: "+platform.getTotalEndorsmentPosts());
			System.out.println("Most endorsed post: "+platform.getMostEndorsedPost());
			System.out.println("Most endorsed account: "+platform.getMostEndorsedAccount()+"\n");

			platform.loadPlatform("file.txt");
			
			System.out.println("-----------------------------------------\nPlatform after loading saved file:\n-----------------------------------------\n");

			System.out.println("Number of accounts: "+platform.getNumberOfAccounts());
			System.out.println("Number of original posts: "+platform.getTotalOriginalPosts());
			System.out.println("Number of comments: "+platform.getTotalCommentPosts());
			System.out.println("Number of endorsements: "+platform.getTotalEndorsmentPosts());
			System.out.println("Most endorsed post: "+platform.getMostEndorsedPost());
			System.out.println("Most endorsed account: "+platform.getMostEndorsedAccount()+"\n");

			System.out.println(platform.showPostChildrenDetails(postid)+"\n");

		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		} catch (AccountIDNotRecognisedException e) {
			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (false) : "NotActionablePostException thrown incorrectly";
		} catch (IOException e) {
			assert (false) : "IOException thrown incorrectly";
		} catch (ClassNotFoundException e) {
			assert (false) : "ClassNotFoundException thrown incorrectly";
		}
	}
}
