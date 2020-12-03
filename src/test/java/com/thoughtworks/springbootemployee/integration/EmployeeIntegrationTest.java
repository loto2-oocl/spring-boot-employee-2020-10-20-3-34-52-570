package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_employees_when_called_get_all_give_employees() throws Exception {
        //given
        Employee employee = new Employee("Tom", 18, "Male", 10000);
        employeeRepository.insert(employee);

        //when
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(10000));
    }

    @Test
    void should_return_specific_employee_when_called_get_one_by_id_given_employee() throws Exception {
        //given
        Employee employee = new Employee("Tom", 18, "Male", 10000);
        employeeRepository.insert(employee);

        //when
        mockMvc.perform(get("/employees/" + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(10000));
    }

    @Test
    void should_return_2_employee_when_called_get_all_paginated_given_3_employees_page_1_page_size_2() throws Exception {
        //given
        Employee employee1 = new Employee("Tom", 18, "Male", 10000);
        Employee employee2 = new Employee("Tom1", 19, "Male", 10001);
        Employee employee3 = new Employee("Tom2", 20, "Male", 10002);
        employeeRepository.insert(Arrays.asList(employee1, employee2, employee3));

        //when
        mockMvc.perform(get("/employees")
                    .param("page", "1")
                    .param("pageSize", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(10000))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].name").value("Tom1"))
                .andExpect(jsonPath("$[1].age").value(19))
                .andExpect(jsonPath("$[1].gender").value("Male"))
                .andExpect(jsonPath("$[1].salary").value(10001));
    }

    @Test
    void should_return_all_male_employees_when_called_get_all_given_employees_param_gender_male() throws Exception {
        //given
        Employee employee1 = new Employee("Tom", 18, "Male", 10000);
        Employee employee2 = new Employee("Tom1", 19, "Female", 10001);
        employeeRepository.insert(Arrays.asList(employee1, employee2));

        //when
        mockMvc.perform(get("/employees")
                .param("gender", "Male"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(10000));
    }
}
