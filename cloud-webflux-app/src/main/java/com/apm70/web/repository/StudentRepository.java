package com.apm70.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apm70.web.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
