package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @InjectMocks
    EmployeeService employeeService;

    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    public void should_return_all_employee_when_get_all_given_repository_with_all_employee() {
        //given
        List<Employee> expected = Arrays.asList(new Employee(), new Employee());
        when(employeeRepository.findAll()).thenReturn(expected);

        //when
        List<Employee> actual = employeeService.getAll();

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_targeted_employee_when_get_one_by_id_given_an_employee_id_repository_with_employee() {
        //given
        Employee expected = new Employee(1, "Tom", 18, "male", 10000);
        when(employeeRepository.findById(any())).thenReturn(expected);

        //when
        Employee actual = employeeService.getOneById(1);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_created_employee_when_create_given_an_empty_repository_and_employee_request() {
        //given
        Employee expected = new Employee(1, "Tom", 18, "male", 10000);
        when(employeeRepository.create(any())).thenReturn(expected);

        //when
        Employee actual = employeeService.create(expected);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_male_employee_when_get_all_by_gender_given_repository_with_one_male_and_female_employee_and_male_filter() {
        // given
        String gender = "male";
        Employee maleEmployee = new Employee(1, "Tom", 18, "male", 10000);
        List<Employee> expected = Collections.singletonList(maleEmployee);
        when(employeeRepository.findByGender(gender)).thenReturn(expected);

        //when
        List<Employee> actual = employeeService.getAllByGender(gender);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_2_employees_when_get_all_paginated_given_repository_with_3_employees_and_page_1_page_size_2() {
        //given
        int page = 1;
        int pageSize = 2;
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        Employee employee3 = new Employee();
        List<Employee> employees = Arrays.asList(employee1, employee2, employee3);
        when(employeeRepository.findAllPaginated(page, pageSize)).thenCallRealMethod();
        when(employeeRepository.findAll()).thenReturn(employees);

        //when
        List<Employee> actual = employeeService.getAllPaginated(page, pageSize);

        //then
        assertEquals(pageSize, actual.size());
        assertEquals(Arrays.asList(employee1, employee2), actual);
    }

    @Test
    void should_call_repository_once_with_new_employee_of_id_1_when_update_given_update_employee_details_of_id_1() {
        //given
        Integer employeeId = 1;
        Employee newEmployee = new Employee(employeeId, "Tom updated", 18, "male", 10000);

        //when
        employeeService.update(employeeId, newEmployee);

        //then
        verify(employeeRepository, times(1)).update(employeeId, newEmployee);
    }
}