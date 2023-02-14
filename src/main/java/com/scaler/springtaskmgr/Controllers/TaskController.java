package com.scaler.springtaskmgr.Controllers;

import com.scaler.springtaskmgr.Dto.ErrorResponse;
import com.scaler.springtaskmgr.Entities.Task;
import com.scaler.springtaskmgr.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    public TaskController(@Autowired TaskService taskService) {
        this.taskService = taskService;
    }

    /*show all existing tasks*/
    @GetMapping("/tasks")

    ResponseEntity<List<Task>> getTasks(){
        return ResponseEntity.ok(taskService.getTask());
    }



    /**create new task*/
    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody Task task){
        var newtask = taskService.createTask(task.getTitle(), task.getDescription(), task.getDueDate());
        return ResponseEntity.created(URI.create("/tasks/"+ newtask.getId())).body(newtask);
    }


    /**Get task by id*/
    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> getTask(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }



    /** Delete a task by id */
    @DeleteMapping("/tasks/{id}")
    ResponseEntity<Task> deleteTask(@PathVariable Integer id) {
        return ResponseEntity.accepted().body(taskService.deleteTask(id));
    }




    /*update task by id*/
    @PatchMapping("/tasks/{id}")
    ResponseEntity<Task> updateTask(@PathVariable("id") Integer id, @RequestBody Task task){
        var updatedTask = taskService.updadeTask(
                id,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate()
        );
        return ResponseEntity.accepted().body(updatedTask);
    }

    @ExceptionHandler(TaskService.TaskNotFoundException.class)
    ResponseEntity<ErrorResponse> handleErrors(TaskService.TaskNotFoundException e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),
                HttpStatus.NOT_FOUND
        );

    }

}