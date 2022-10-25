package com.GeekHub.TaskServer.repository;

import com.GeekHub.TaskServer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository  extends JpaRepository<Task,Long> {

}
