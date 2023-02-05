package com.example.demo.service;

import com.example.demo.dto.APIResponseDto;
import com.example.demo.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployeeById(Long employeeId);
}
