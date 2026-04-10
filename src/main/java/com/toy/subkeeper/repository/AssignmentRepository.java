package com.toy.subkeeper.repository;

import com.toy.subkeeper.domain.Assignment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AssignmentRepository extends CrudRepository<Assignment, Long> {

    List<Assignment> findBySubjectId(Long id);
}
