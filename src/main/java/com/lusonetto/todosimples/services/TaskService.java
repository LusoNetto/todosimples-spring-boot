package com.lusonetto.todosimples.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lusonetto.todosimples.models.Task;
import com.lusonetto.todosimples.models.User;
import com.lusonetto.todosimples.repositories.TaskRepository;
import com.lusonetto.todosimples.services.exceptions.DataBindingViolationException;
import com.lusonetto.todosimples.services.exceptions.ObjectNotFoundException;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(()-> new ObjectNotFoundException(
            "Tarefa não encontrada, Id: " + id + ", Tipo: " + Task.class.getName()
        ));
    }

    public List<Task> findAllByUserId(Long userId){
        List<Task> tasks = this.taskRepository.findByUser_id(userId);
        return tasks;
    }

    @Transactional
    public Task create(Task obj){
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }
    
    @Transactional
    public Task update(Task obj){
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id){
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException(
                "Não é possivel deletar pois há entidades relacionadas"
            );
        }
    }

}
