package com.lusonetto.todosimples.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lusonetto.todosimples.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    
    List<Task> findByUser_id(Long id);

}
