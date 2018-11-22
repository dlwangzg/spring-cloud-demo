package com.apm70.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apm70.web.model.Student;
import com.apm70.web.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	@PostMapping
	public Mono<Student> save(@RequestBody Student student) {
		return Mono.create(monoSink -> {
			this.studentRepository.save(student);
			monoSink.success(student);
		});
	}
	
	@GetMapping(value="/paging")
	public Flux<Student> page(@RequestParam int page, @RequestParam int size) {
		return Flux.create(fluxSink -> {
			Page<Student> students = this.studentRepository.findAll(PageRequest.of(page, size));
			log.info("students get paging data");
			students.stream().forEach(student -> fluxSink.next(student));
			fluxSink.complete();
		});
	}
}
