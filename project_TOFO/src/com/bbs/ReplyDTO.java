package com.bbs;

public class ReplyDTO {
	private int replyNum, num;
	private String userId, userName, content, created;
	private int answer;
	private int replyLike;
	
	private int answerCount, likeCount, disLikeCount;
	
	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public int getReplyLike() {
		return replyLike;
	}
	public void setReplyLike(int replyLike) {
		this.replyLike = replyLike;
	}
	public int getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getDisLikeCount() {
		return disLikeCount;
	}
	public void setDisLikeCount(int disLikeCount) {
		this.disLikeCount = disLikeCount;
	}
}