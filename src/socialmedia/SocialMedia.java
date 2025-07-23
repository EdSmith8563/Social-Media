package socialmedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.io.*;

public class SocialMedia implements SocialMediaPlatform {
	
	
	int universalAccountID = 0;																				// variable used to store the next available account ID number
	int universalPostID = 0;																				// variable used to store the next available account ID number
	private ArrayList<Account> accountList = new ArrayList<Account>();										// list used to store all of the currently exisitng accounts
	private ArrayList<Post> postList = new ArrayList<Post>(); 												// list used to store all of the currently exisitng posts


	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		return createAccount(handle, "");														// calls the other create account function, setting the description field as empty
	}


	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		for (Account account : accountList) {																// iterates through all the accounts in the account list
			if (account.getUserHandle() == handle) {														// if the account handle matches the handle requested then
				throw new IllegalHandleException("Handle already in use!");							// throw an error saying the handle is already in use
			}
		}

		if (handle.length() > 30) {																			// if the inputted handles length is more than 30 characters
			throw new InvalidHandleException("Handle can't be longer than 30 characters!");			// throw an error explaining so
		} else if (handle.contains(" ")) {																// if the inputted handle contains white spaces
			throw new InvalidHandleException("Handle must not contain whitespaces!");				// throw an error explaining so
		} else if (handle == "") {																			// if the handle is empty
			throw new InvalidHandleException("Handle must have one or more characters!");			// throw an error explaining so
		} else {																							// if no errors are thrown
			accountList.add(new Account(++universalAccountID, handle, description));						// create an account with the specified handle and description and add it to the list
		}
		return universalAccountID;																			// finally, return the ID of the account created
	}


	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		boolean idFound = false;																			// boolean used to store if the account has been found
		Iterator<Account> accountIterator = accountList.iterator();											// iterator used to remove accounts without error
		Iterator<Post> postIterator = postList.iterator();													// iterator used to remove accounts without error


		while (accountIterator.hasNext()) {																	// iterates through all exisitng accounts
			Account account = (Account)accountIterator.next();												// assigns the account as the next account in the list
			if (account.getUserID() == id) {																// if the ID of the account matches the the inputted ID
				while (postIterator.hasNext()) {															// iterates through all exisitng posts
					Post post = (Post)postIterator.next();													// assigns the post as the next post in the list
					if (post.getAuthorID(accountList) == id) {												// if the posts author ID matches the current ID
						postIterator.remove();																// remove the post
					} else if (post instanceof CommentPost) {												// if the post is an comment
						CommentPost comment = (CommentPost) post;											// create new variable for commeny
						if (comment.getParentPostID() == id) {												// if the comment's parent id is that of given id
							comment.setParentPostID(0);									// set the comments parent to the empty post
						}
					} else if (post instanceof EndorsementPost) {											// if the post is an endorsement
						EndorsementPost endorsement = (EndorsementPost) post;								// create new variable for endorsement
						if (endorsement.getParentPostID() == id) {											// if the endorsement's parent id is that of given id
							postIterator.remove();				 											// remove the endorsement from the list
						}
					}
				}
				accountIterator.remove();																	// remove that account from the list
				idFound = true;																				// set boolean to true as account was found
			}
		}

		if (!idFound) {																						// if the inputted ID does not match an existing account
			throw new AccountIDNotRecognisedException("Account ID not recognised!");				// throw an error saying so
		}
	}


	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		boolean handleFound = false;																		// boolean used to store if the account has been found
		Iterator<Account> accountIterator = accountList.iterator();											// iterator used to remove accounts without error
		Iterator<Post> postIterator = postList.iterator();													// iterator used to remove accounts without error

		while (accountIterator.hasNext()) {																	// iterates through all exisitng accounts
			Account account = (Account)accountIterator.next();												// assigns the account as the next account in the list
			if (account.getUserHandle() == handle) {														// if the handle of the account matches the the inputted handle
				while (postIterator.hasNext()) {															// iterates through all exisitng posts
					Post post = (Post)postIterator.next();													// assigns the post as the next post in the list
					if (post.getAuthor() == handle) {														// if the posts author ID matches the current ID
						postIterator.remove();																// remove the post
					} else if (post instanceof CommentPost) {												// if the post is an comment
						CommentPost comment = (CommentPost) post;											// create new variable for commeny
						if (comment.getParentPostID() == account.getUserID()) {								// if the comment's parent id is that of given id
							comment.setParentPostID(0);									// set the comments parent to the empty post
						}
					} else if (post instanceof EndorsementPost) {											// if the post is an endorsement
						EndorsementPost endorsement = (EndorsementPost) post;								// create new variable for endorsement
						if (endorsement.getParentPostID() == account.getUserID()) {							// if the endorsement's parent id is that of given id
							postIterator.remove();				 											// remove the endorsement from the list
						}
					}
				}
				accountIterator.remove();																	// remove that account from the list
				handleFound = true;																			// set boolean to true as account was found
			}
		}

		if (!handleFound) {																					// if the inputted handle does not match an existing account
			throw new HandleNotRecognisedException("Account handle not recognised!");				// throw an error saying so
		}
	}


	@Override
	public void changeAccountHandle(String oldHandle, String newHandle) throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		boolean handleFound = false;																		// boolean to track whether the account has been found
		
		for (Account account : accountList) {																// iterates through all the accounts in the account list
			if (account.getUserHandle() == newHandle) {														// if the account handle matches the handle requested then
				throw new IllegalHandleException("Handle already in use!");							// throw an error saying the handle is already in use
			}
		}
		
		for (Account account : accountList) {																// iterates through all exisitng accounts
			if (account.getUserHandle() == oldHandle) {														// if the account handle matches the given handle
				handleFound = true;																			// set boolean to true as account has been found
				if (newHandle.length() > 30) {																// if the inputted handles length is more than 30 characters
					throw new InvalidHandleException("Handle can't be longer than 30 characters!");	// throw an error explaining so
				} else if (newHandle.contains(" ")) {														// if the inputted handle contains white spaces
					throw new InvalidHandleException("Handle must not contain whitespaces!");		// throw an error explaining so
				} else if (newHandle == "") {																// if the handle is empty
					throw new InvalidHandleException("Handle must have one or more characters!");	// throw an error explaining so
				} else {																					// if no errors are thrown
					account.setUserHandle(newHandle);														// set the handle of the account to the new given one
				}
			}
		}
	
		if (!handleFound) {																					// if the account with the given handle was not found	
			throw new HandleNotRecognisedException("Account handle not recognised!");				// throw an error saying so
		}
	}


	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		boolean handleFound = false;																		// boolean to track if the account is found
		for (Account account : accountList) {																// iterates through all existing accounts
			if (account.getUserHandle() == handle) {														// if the account handle matches the given one
				handleFound = true;																			// account has been found so set boolean to true
				account.setUserDesc(description);															// set the description of the matching account to the description given
			}
		}

		if (!handleFound) {																					// if the account with the given handle was not found
			throw new HandleNotRecognisedException("Account handle not recognised!");				// throw an error explaining so
		}
	}


	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		boolean handleFound = false;																		// boolean to track if the account is found
		String returnMessage = null;																		// string to return
		int postCount = 0;																					// variable for tracking how many posts an account has made
		int endorsementCount = 0; 																			// variable for tracking how many endorsments an account has

		for (Account account : accountList) {																// searches through all accounts
            if (account.getUserHandle() == handle){															// if account handle matches given handle
				handleFound = true;																			// set handle found to true
                
				for (Post post : postList) {																// iterate through all exisitng posts
					if (post.getAuthorID(accountList) == account.getUserID()) { 							// if the author ID of the post matches the account ID
						postCount++;																		// increment post count
						if (post instanceof OriginalPost) { 												// if the post is an original post or comment
							endorsementCount += ((OriginalPost) post).getEndorsementCount(postList);		// add the endorsement count to the total endorsements
						}
					}
				}
				
				returnMessage = 																			// set return message
				"ID: " + account.getUserID() + 																// show account ID
				"\nHandle: " + account.getUserHandle() + 													// show account handle
				"\nDescription: " + account.getUserDesc() +													// show account description
				"\nPost count: " + postCount +																// show amount of posts
				"\nEndorse count: " + endorsementCount;														// show amount of endorsements
            }
        }
		
		if (!handleFound) {																					// if the account with the given handle was not found
			throw new HandleNotRecognisedException("Account handle not recognised!");				// throw an error explaining so
		}
		return returnMessage;																				// return the account information if found, else return null
    }


	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		boolean handleFound = false;																		// boolean to track if the account is found

		if (message.length() > 100) {																		// if the message is longer than 100 characters
            throw new InvalidPostException("Message must not be longer than 100 characters!");		// throw an error explaining so
        } else if (message == "") {																			// if the message is empty
			throw new InvalidPostException("Message must not be empty");							// throw an error explaining so
		}

		for (Account account : accountList) {																// searches through all accounts
            if (account.getUserHandle() == handle) {														// if the account handle matches given handke
				handleFound = true;																			// set handle found to true
				postList.add(new OriginalPost(++universalPostID, handle, message));							// create post and add it to the list of all posts
       		}
		}

		if (!handleFound) {																					// if the account with the given handle was not found
			throw new HandleNotRecognisedException("Account handle not recognised!");				// throw an error explaining so
		}
		return universalPostID;																				// returns post id
	}


	@Override
	public int endorsePost(String handle, int id) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		boolean handleFound = false;																		// boolean to track if the account is found
		boolean postFound = false;																			// boolean to track is the post is found
		OriginalPost postToEndorse = null;																	// variable to pass the endorsed post
		
		for (Account account : accountList) {																// search all exisitng accounts
			if (account.getUserHandle() == handle) {														// if the account handle matches given handle
				handleFound = true;																			// set handle found to true
			}
		}

		if (!handleFound) {																					// if the account with the given handle was not found
			throw new HandleNotRecognisedException("Account handle not recognised!");				// throw an error explaining so
		}

		for (Post post : postList) {																		// searches through all posts
			if (post.getID() == id) {																		// if the post id matches the given id
				if (post instanceof OriginalPost) {															// if post is original or comment
					postFound = true;																		// set post found to true
					postToEndorse = (OriginalPost) post;													// set the post to the post to endorse variable
				} else if (post instanceof EndorsementPost) {												// throw an error if post is an endorsement
					throw new NotActionablePostException("Cannot endorse an endorsement post!");
				}
			}
		}

		if (postFound) {																					// if the post was found
			postList.add(new EndorsementPost(++universalPostID, handle, postToEndorse));					// create endorsement and add it to the list of all posts
		} else {																							// else post was not found
			throw new PostIDNotRecognisedException("Post ID not recognised!");						// throw an error explaining so
		}
		return universalPostID;
	}


	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		boolean handleFound = false;																		// boolean to track if the account is found
		boolean postFound = false;																			// boolean to track is the post is found
		
		if (message.length() > 100) {																		// if the message is longer than 100 characters
            throw new InvalidPostException("Message must not be longer than 100 characters!");		// throw an error explaining so
        } else if (message == ""){																			// if the message is empty
			throw new InvalidPostException("Message must not be empty");							// throw an error explaining so
		}
		
		for (Account account : accountList) {																// search all exisitng accounts
			if (account.getUserHandle() == handle) {														// if the account handle matches given handle
				handleFound = true;																			// set handle found to true
			}
		}

		if (!handleFound) {																					// if the account with the given handle was not found
			throw new HandleNotRecognisedException("Account handle not Recognised!");				// throw an error explaining so
		}
		
		for (Post post : postList) {																		// searches through all posts
			if (post.getID() == id) {																		// if the post id matches the given id
				if (post instanceof OriginalPost) {															// if post is original or comment
					postFound = true;																		// set post found to true
				} else if (post instanceof EndorsementPost) {												// throw an error if post is an endorsement
					throw new NotActionablePostException("Cannot endorse an endorsement post!");
				}
			}
		}

		if (postFound) {																					// if the post was found
			postList.add(new CommentPost(++universalPostID, handle, message, id));							// create comment add it to the list of all posts
		} else {																							// else post isnt found
			throw new PostIDNotRecognisedException("Post ID not recognised!");						// throw an error explaining so
		}
		return universalPostID;
	}


	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		boolean postFound = false;																			// boolean used to store if the account has been found
		Iterator<Post> postIterator = postList.iterator();													// iterator used to remove accounts without error

		while (postIterator.hasNext()) {																	// iterates through all exisitng accounts
			Post post = (Post)postIterator.next();															// assigns the account as the next account in the list
			if (post.getID() == id) {																		// if the ID of the account matches the the inputted ID
				postIterator.remove();																		// remove that post from the list
				postFound = true;																			// set boolean to true as account was found
			} else if (post instanceof CommentPost) {														// if the post is an comment
				CommentPost comment = (CommentPost) post;													// create new variable for commeny
				if (comment.getParentPostID() == id) {														// if the comment's parent id is that of given id
					comment.setParentPostID(0);											// set the comments parent to the empty post
				}
			} else if (post instanceof EndorsementPost) {													// if the post is an endorsement
				EndorsementPost endorsement = (EndorsementPost) post;										// create new variable for endorsement
				if (endorsement.getParentPostID() == id) {													// if the endorsement's parent id is that of given id
					postIterator.remove();				 													// remove the endorsement from the list
				}
			}
		}

		if (!postFound) {																					// if the inputted ID does not match an existing post
			throw new PostIDNotRecognisedException("Post ID not Recognised!");						// throw an error saying so
		}
	}


	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		boolean postFound = false;																			// boolean used to store if the account has been found
		StringBuilder message = new StringBuilder();														// string used to store message
		
		for (Post post : postList) {																		// searches all posts
			if (post.getID() == id) {																		// if the post id matches the given one
				message.append("ID: " + post.getID() + "\n");												// add the ID to the message
				message.append("Account: " + post.getAuthor() + "\n");										// add the handle to the message
				if (post instanceof OriginalPost) {															// if the post is an original post or a comment
					OriginalPost original = (OriginalPost) post;											// change to OriginalPost type
					message.append("No. endorsements: " + original.getEndorsementCount(postList) + " | ");	// add endorsements to message
					message.append("No. comments: " + original.getCommentCount(postList) + "\n");			// add comments to message
				}
				message.append(post.getMessage());															// add the actual message
				postFound = true;																			// set boolean to true as account was found
			}
		}

		if (!postFound) {																					// if the inputted ID does not match an existing post
			throw new PostIDNotRecognisedException("Post ID not recognised!");						// throw an error saying so
		}
		return message.toString();
	}


	@Override
	public StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException {
		boolean postFound = false;																			// boolean used to store if the account has been found
		boolean postCanHaveChildren = false;																// boolean used to store if the account can have children
		boolean firstChild = true;																			// boolean used to format child posts correctly
		StringBuilder message = new StringBuilder();														// string used to store message

		
		for (Post post : postList) {																		// searches all posts
			if (post.getID() == id) {																		// if the post id matches the given one
				postFound = true;																			// set boolean to true as account was found
				if (post instanceof OriginalPost) {															// if the post is an original post or a comment
					postCanHaveChildren = true;																// set can have children to true
				}
			}
		}

		if (!postFound) {																					// if the inputted ID does not match an existing post
			throw new PostIDNotRecognisedException("Post ID not recognised!");						// throw an error saying so
		} else if (!postCanHaveChildren) {																	// if the post is an endorsement
			throw new NotActionablePostException("Post cannot be an endorsement!");					// throw an error saying so
		} else {																							// else if the post exists and can have children
			message.append(showIndividualPost(id));															// display the post with the given id
			for (Post post : postList) {																	// search through all exisiting posts
				if (post instanceof CommentPost) {															// if the post is a comment
					CommentPost childPost = (CommentPost) post;												// create variable for comment
					if (childPost.getParentPostID() == id) {												// if the comments parent id is that of the given id
						if (firstChild) {																	// check if this is the first child of the given post
							message.append("\n|");														// if so, append a | to the message for formatting
							firstChild = false;																// then set first child to false
						}
						message.append("\n| > " + (showPostChildrenDetails(childPost.getID()).toString())	// call this function to display children of child post
						.replace("\n", "\n    "));										// used to keep indentation correctly
					}
				}
			}
		}
		return message;
	}


	@Override
	public int getNumberOfAccounts() {
		return accountList.size();																			// returns the size off account list, showing number off accounts
	}


	@Override
	public int getTotalOriginalPosts() {
		int originalPostCount = 0;																			// variable for keeping count of all original posts
		for (Post post : postList) {																		// searches all exisitng posts
			if (post instanceof OriginalPost && !(post instanceof CommentPost)) {							// if the post is an instance of a original post but not a comment
				originalPostCount++;																		// increment the counter
			}
		}
		return originalPostCount;																			// return the counter value
	}


	@Override
	public int getTotalEndorsmentPosts() {
		int endorsementPostCount = 0;																		// variable for keeping count of all endorsements
		for (Post post : postList) {																		// searches all exisitng posts
			if (post instanceof EndorsementPost) {															// if the post is an instance of an endorsement
				endorsementPostCount++;																		// increment the counter
			}
		}
		return endorsementPostCount;																		// return the counter value
	}


	@Override
	public int getTotalCommentPosts() {
		int commentPostCount = 0;																			// variable for keeping count of all comments
		for (Post post : postList) {																		// searches all exisitng posts
			if (post instanceof CommentPost) {																// if the post is an instance of a comment 
				commentPostCount++;																			// increment the counter
			}
		}
		return commentPostCount;																			// return the counter value
	}


	@Override
	public int getMostEndorsedPost() {
		int maxEndorsements = 0; 
    	int mostEndorsedPostId = 0;

    	for (Post post : postList) {																		// iterate through all exisitng posts
       		if (post instanceof OriginalPost) {																// if the post is an original post or a comment
           		OriginalPost originalPost = (OriginalPost) post;											// create variable for getting endorsements
            	int endorsements = originalPost.getEndorsementCount(postList);								// get the endorsements of the post

            if (endorsements > maxEndorsements) {															// if the posts endorsements are higher than the max current endorsements
                maxEndorsements = endorsements;																// set the max endorsements to the posts endorsement number
				mostEndorsedPostId = originalPost.getID();													// set the max endorsed post id to this posts id
            	}
       		}
    	}
    	return mostEndorsedPostId;
	}


	@Override
	public int getMostEndorsedAccount() {
    	int maxEndorsements = 0;
    	int mostEndorsedAccountId = 0;

    	for (Account account : accountList) { 																// iterate through all accounts
        	int endorsements = 0; 																			// set endorsements to 0

        	for (Post post : postList) {																	// iterate through all exisitng posts
            	if (post.getAuthorID(accountList) == account.getUserID()) { 								// if the author ID of the post matches the account ID
                	if (post instanceof OriginalPost) { 													// if the post is an original post or comment
                    	endorsements += ((OriginalPost) post).getEndorsementCount(postList);				// add the endorsement count to the total endorsements
                	}
            	}
        	}

        	if (endorsements > maxEndorsements) { 															// if the total endorsements is greater than the current max endorsements
            	maxEndorsements = endorsements; 															// set the max endorsements to the total endorsements
            	mostEndorsedAccountId = account.getUserID(); 												// set the most endorsed account ID to the account ID
        	}
    	}
    	return mostEndorsedAccountId;
	}


	@Override
	public void erasePlatform() {
		accountList.clear();																				// clear account list
		postList.clear();																					// clear post list
    	universalAccountID = 0;																				// set the universal account ID to 0
    	universalPostID = 0; 																				// set the universal post ID to 0
	}


	@Override
	public void savePlatform(String filename) throws IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {				// create the output variable
			out.writeInt(universalAccountID);																// save the account id number
			out.writeInt(universalPostID);																	// save the post id number
			out.writeObject(accountList);																	// save all the accounts
			out.writeObject(postList);																		// save all the posts
			out.close();																					// close the file
		}
	}


	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {					// create the input variable
			erasePlatform();																				// clear platform before loading

			universalAccountID = in.readInt(); 																// read in the universal account ID
			universalPostID = in.readInt(); 																// read in the universal post ID

			List<?> rawLoadedAccounts = (List<?>) in.readObject(); 											// read in the list of accounts
			List<Account> loadedAccounts = new ArrayList<>(); 												// create a new list of accounts
			for (Object obj : rawLoadedAccounts) { 															// iterate through the list of accounts
				loadedAccounts.add((Account) obj); 															// add the accounts to the new list
			}
			accountList.addAll(loadedAccounts); 															// add the new list of accounts to the current list
			
			List<?> rawLoadedPosts = (List<?>) in.readObject(); 											// read in the list of posts
			List<Post> loadedPosts = new ArrayList<>(); 													// create a new list of posts
			for (Object obj : rawLoadedPosts) { 															// iterate through the list of posts
				loadedPosts.add((Post) obj); 																// add the posts to the new list
			}
			postList.addAll(loadedPosts); 																	// add the new list of posts to the current list
		}
	}
}

