package com.example.demo.service.impl;

import com.example.demo.dto.APIResponseDto;
import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private ModelMapper mapper;
    private RestTemplate restTemplate;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        /*Employee employee = new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        ); */
        Employee employee = mapper.map(employeeDto,Employee.class);

        Employee savedEmployee = employeeRepository.save(employee);

        /*EmployeeDto savedDto = new EmployeeDto(
                savedEmployee.getId(),
                savedEmployee.getFirstName(),
                savedEmployee.getLastName(),
                savedEmployee.getEmail()
        );*/
        EmployeeDto savedDto = mapper.map(savedEmployee,EmployeeDto.class);

        return savedDto;
    }

    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();

        if (employee == null){
            throw new ResourceNotFoundException("Kaynak Yok");
        }

        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8082/api" +
                        "/departments/" + employee.getDepartmentCode(),
                DepartmentDto.class);

        DepartmentDto departmentDto = responseEntity.getBody();

        /*EmployeeDto employeeDto = new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );*/
        EmployeeDto employeeDto = mapper.map(employee,EmployeeDto.class);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);

        return apiResponseDto;
    }
}













