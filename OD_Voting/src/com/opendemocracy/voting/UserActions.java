package com.opendemocracy.voting;

public interface UserActions {
/**

UI components and priorities
- Propositions add/merge/branch/comment
- Experts claim/find/trust
- Categories add/tag/find
- Option add/branch/merge/vote

 */
	
	void distributeRepresentation();

	void addProposition();

	void claimExpertise();

	void validateAccount();

	void addCategory();

	void addOption();

	void uploadFile();

	void vote();
}
