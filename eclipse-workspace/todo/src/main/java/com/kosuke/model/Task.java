package com.kosuke.model;

import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.kosuke.utils.TaskCategories;

/**
 * The Task Entity class
 *
 * @author kosuke takeuchi
 * @version 1.0
 * Date 2021/8/15.
 */
@Entity
@Table(name = "task", schema = "sampledb")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "task_name")
	private String taskName;

	@Column(name = "description")
	private String description;

	@Column(name = "content")
	private String content;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "task_date", nullable = false)
	private LocalDate taskDate;

	@Column(name = "create_date")
	private LocalDateTime createDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private TaskCategories category;

	@Column(name = "status")
	private String status;

	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "image_dir")
	private String imageDir;
	
	public String getImageDir() {
		return imageDir;
	}

	public void setImageDir(String imageDir) {
		this.imageDir = imageDir;
	}

	@javax.persistence.Transient
	@Column(name = "task_image")
	private MultipartFile taskImage;

	public MultipartFile getTaskImage() {
		return taskImage;
	}

	public void setTaskImage(MultipartFile taskImage) {
		this.taskImage = taskImage;
	}

	public Task() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(LocalDate taskDate) {
		this.taskDate = taskDate;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public TaskCategories getCategory() {
		return category;
	}

	public void setCategory(TaskCategories category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Task task = (Task) o;
		return id == task.id && userId == task.userId && Objects.equals(taskName, task.taskName)
				&& Objects.equals(description, task.description) && Objects.equals(content, task.content)
				&& Objects.equals(taskDate, task.taskDate) && Objects.equals(createDate, task.createDate)
				&& category == task.category && Objects.equals(status, task.status);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, taskName, description, content, taskDate, createDate, category, status, userId);
	}
}
