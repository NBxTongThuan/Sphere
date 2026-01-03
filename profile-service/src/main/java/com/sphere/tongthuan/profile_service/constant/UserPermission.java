package com.sphere.tongthuan.profile_service.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserPermission {
	// ================= USER / ACCOUNT =================
	USER_READ("USER_READ", "View user profile information"),
	USER_UPDATE_SELF("USER_UPDATE_SELF", "Update own user profile"),
	USER_UPDATE_ANY("USER_UPDATE_ANY", "Update any user's profile"),
	USER_BAN("USER_BAN", "Ban a user account"),
	USER_UNBAN("USER_UNBAN", "Unban a user account"),

	// ================= POST =================
	POST_CREATE("POST_CREATE", "Create a new post"),
	POST_READ("POST_READ", "View posts"),
	POST_UPDATE_SELF("POST_UPDATE_SELF", "Edit own post"),
	POST_DELETE_SELF("POST_DELETE_SELF", "Delete own post"),
	POST_DELETE_ANY("POST_DELETE_ANY", "Delete any user's post"),
	POST_HIDE("POST_HIDE", "Hide a post due to policy violations"),

	// ================= COMMENT =================
	COMMENT_CREATE("COMMENT_CREATE", "Create a comment"),
	COMMENT_DELETE_SELF("COMMENT_DELETE_SELF", "Delete own comment"),
	COMMENT_DELETE_ANY("COMMENT_DELETE_ANY", "Delete any user's comment"),
	COMMENT_DISABLE("COMMENT_DISABLE", "Disable comments on a post"),

	// ================= REACTION =================
	REACTION_CREATE("REACTION_CREATE", "Add a reaction (like, emoji) to a post"),
	REACTION_DELETE("REACTION_DELETE", "Remove a reaction from a post"),

	// ================= CHAT / MESSAGE =================
	CHAT_SEND("CHAT_SEND", "Send a chat message"),
	CHAT_READ("CHAT_READ", "Read chat messages"),
	CHAT_DELETE_SELF("CHAT_DELETE_SELF", "Delete own chat message"),
	CHAT_DELETE_ANY("CHAT_DELETE_ANY", "Delete any user's chat message"),

	// ================= REPORT =================
	REPORT_CREATE("REPORT_CREATE", "Submit a report for inappropriate content"),
	REPORT_READ("REPORT_READ", "View reported content"),
	REPORT_HANDLE("REPORT_HANDLE", "Review and resolve reports"),

	// ================= SYSTEM =================
	SYSTEM_VIEW_LOG("SYSTEM_VIEW_LOG", "View system logs"),
	SYSTEM_MANAGE_ROLE("SYSTEM_MANAGE_ROLE", "Manage roles"),
	SYSTEM_MANAGE_PERMISSION("SYSTEM_MANAGE_PERMISSION", "Manage permissions");
	;
	private String permissionName;
	private String description;

}
