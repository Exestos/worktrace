package com.exestos.worktrace.repository;

import com.exestos.worktrace.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
