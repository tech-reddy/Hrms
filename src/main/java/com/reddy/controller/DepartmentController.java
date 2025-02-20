package com.reddy.controller;

import com.reddy.dto.employee.DepartmentRequestDTO;
import com.reddy.dto.employee.DepartmentResponseDTO;
import com.reddy.service.employee.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponseDTO createDepartment(
            @Valid @RequestBody DepartmentRequestDTO dto) {
        return departmentService.createDepartment(dto);
    }

    @PutMapping("/{id}")
    public DepartmentResponseDTO updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequestDTO dto) {
        return departmentService.updateDepartment(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentResponseDTO>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        return ResponseEntity.ok(departmentService.getAllDepartments(pageable));
    }

    @GetMapping("/{id}")
    public DepartmentResponseDTO getDepartmentById(@PathVariable Long id) {
        return modelMapper.map(departmentService.getDepartmentById(id), DepartmentResponseDTO.class);
    }

    @GetMapping("/search")
    public Page<DepartmentResponseDTO> searchDepartments(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return departmentService.searchDepartments(query, PageRequest.of(page, size));
    }

    private List<Sort.Order> parseSort(String sort) {
        String[] parts = sort.split(",");
        String property = parts[0].trim();
        Sort.Direction direction = Sort.Direction.ASC; // default
        if (parts.length > 1) {
            String dir = parts[1].trim().toLowerCase();
            if ("desc".equals(dir)) {
                direction = Sort.Direction.DESC;
            }
        }
        return Collections.singletonList(new Sort.Order(direction, property));
    }

}
