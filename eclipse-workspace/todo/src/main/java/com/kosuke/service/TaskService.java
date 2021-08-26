package com.kosuke.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import com.kosuke.model.Task;

import java.util.List;

/**
 * The TaskService interface
 *
 * @author kosuke takeuchi
 * @version 1.0
 * Date 2021/8/15.
 */
public interface TaskService {

    Task save(Task task);

    Boolean delete(int id);

    Task update(Task task);

    Task findById(int id);

    List<Task> findAll();

    List<Task> findByStatus(String status);

    List<Task> findByUserIdStatus(int userId, String status);

    List<Task> findBetween(int start, int end);

    int findMaxTaskId(int userId);
    
	void uploadTaskImage(Task reqTask);

}
