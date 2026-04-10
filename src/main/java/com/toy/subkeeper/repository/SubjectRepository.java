package com.toy.subkeeper.repository;

import com.toy.subkeeper.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findBySemesterId(Long id);
}
