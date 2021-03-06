package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exceptions.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @InjectMocks
    EmployeeService employeeService;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void should_return_all_employee_when_get_all_given_repository_with_all_employee() {
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
        Employee expected = new Employee("1", "Tom", 18, "male", 10000);
        Optional<Employee> optionalEmployee = Optional.of(expected);
        when(employeeRepository.findById("1")).thenReturn(optionalEmployee);

        //when
        Employee actual = employeeService.getOneById("1");

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_throw_employee_not_found_exception_when_get_one_by_id_given_employee_id_not_exists() {
        //given
        String employeeId = "1";
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //then
        assertThrows(
            EmployeeNotFoundException.class,
            // when
            () -> employeeService.getOneById(employeeId),
            "Employee with id:1 not found"
        );
    }

    @Test
    void should_return_created_employee_when_create_given_an_empty_repository_and_employee_request() {
        //given
        Employee expected = new Employee("1", "Tom", 18, "male", 10000);
        when(employeeRepository.insert(expected)).thenReturn(expected);

        //when
        Employee actual = employeeService.create(expected);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_male_employee_when_get_all_by_gender_given_repository_with_one_male_and_female_employee_and_male_filter() {
        // given
        String gender = "male";
        Employee maleEmployee = new Employee("1", "Tom", 18, "male", 10000);
        List<Employee> expected = Collections.singletonList(maleEmployee);
        when(employeeRepository.findAllByGender(gender)).thenReturn(expected);

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
        Page<Employee> expected = new PageImpl<>(Arrays.asList(employee1, employee2));
        when(employeeRepository.findAll((Pageable)any())).thenReturn(expected);

        //when
        Page<Employee> actual = employeeService.getAllPaginated(page, pageSize);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_call_repository_update_once_with_new_employee_of_id_1_when_update_given_update_employee_details_of_id_1() {
        //given
        String employeeId = "1";
        Employee newEmployee = new Employee(employeeId, "Tom updated", 18, "male", 10000);
        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        //when
        employeeService.update(employeeId, newEmployee);

        //then
        verify(employeeRepository, times(1)).save(newEmployee);
    }

    @Test
    void should_throw_employee_not_found_exception_when_update_given_employee_id_not_exists() {
        //given
        String employeeId = "1";
        Employee employee = new Employee();
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        //then
        assertThrows(
            EmployeeNotFoundException.class,
            // when
            () -> employeeService.update(employeeId, employee),
            "Employee with id:1 not found"
        );
    }

    @Test
    void should_call_repository_delete_once_with_employee_id_when_update_given_employee_id_to_delete() {
        //given
        String employeeId = "1";

        //when
        employeeService.delete(employeeId);

        //then
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}