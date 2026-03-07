package com.rodaxsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date completed;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date created;

	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date due;

	private String id;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date modified;

	private TaskStatus status;

	private String title;

	private String userId;

	public Task() {}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	public Date getDue() {
		return due;
	}
	public void setDue(Date due) {
		this.due = due;
	}
	public void setCompleted(Date completed) {
		this.completed = completed;
	}
}