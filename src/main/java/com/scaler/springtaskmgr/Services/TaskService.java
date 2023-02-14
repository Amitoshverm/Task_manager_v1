package com.scaler.springtaskmgr.Services;

import com.scaler.springtaskmgr.Entities.Task;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskService {
    private final List<Task> taskList;
    private final AtomicInteger taskId = new AtomicInteger(0);


    public static class TaskNotFoundException extends IllegalStateException{
        public TaskNotFoundException(Integer id){
            super("Task with id "+id+" not found");
        }
    }
    public TaskService(){
        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(), "Task 1", "Description 1", "2023-02-13"));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 2", "Description 2", "2023-02-13"));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 3", "Description 3", "2023-02-13"));
    }

    public List<Task> getTask(){
        return taskList;
    }

    public Task createTask(String title, String description, String dueDate){
        var newTask = new Task(taskId.incrementAndGet(), title, description, dueDate);
        taskList.add(newTask);
        return newTask;
    }

    public Task updadeTask(Integer id, String title, String description, String dueDate){
        var task = getTaskById(id);
        if (id != null) task.setId(id);
        if (title != null) task.setTitle(title);
        if (description != null) task.setDescription(description);
        if (dueDate != null) task.setDueDate(dueDate);
        return task;
    }
    public Task getTaskById(Integer id){
        /**for(Task task: taskList){
            if (task.getId() == id) {
                return task;
            }
        }
        return null;*/
        return taskList.stream().filter(task->task.getId().equals(id)).findFirst().orElse(null);
    }
    public Task deleteTask(Integer id) {
        var task = getTaskById(id);
        taskList.remove(task);
        return task;
    }

}
