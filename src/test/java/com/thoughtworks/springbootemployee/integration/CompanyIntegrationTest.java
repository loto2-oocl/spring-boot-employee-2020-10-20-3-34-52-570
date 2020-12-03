package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void tearEach() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_get_all_companies_when_called_get_all_given_companies() throws Exception {
        //given
        Company company = new Company("OOCL", 100, Collections.emptyList());
        companyRepository.insert(company);

        //when
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyId").isString())
                .andExpect(jsonPath("$[0].companyName").value("OOCL"))
                .andExpect(jsonPath("$[0].employeesNumber").value(100))
                .andExpect(jsonPath("$[0].employees").isEmpty());
    }

    @Test
    void should_return_specific_company_when_called_get_one_by_id_given_company_id_and_company() throws Exception {
        //given
        Company company = new Company("OOCL", 100, Collections.emptyList());
        companyRepository.insert(company);

        //when
        mockMvc.perform(get("/companies/" + company.getCompanyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").isString())
                .andExpect(jsonPath("$.companyName").value("OOCL"))
                .andExpect(jsonPath("$.employeesNumber").value(100))
                .andExpect(jsonPath("$.employees").isEmpty());
    }

    @Test
    void should_return_employees_list_when_called_get_company_employees_given_id_and_company() throws Exception {
        //given
        Employee employee1 = new Employee("Tom", 18, "male", 1000);
        Employee employee2 = new Employee("Tom1", 19, "female", 1001);
        employeeRepository.insert(Arrays.asList(employee1, employee2));
        Company company = new Company("OOCL", 100, Arrays.asList(employee1, employee2));
        companyRepository.insert(company);

        //when
        mockMvc.perform(get("/companies/" + company.getCompanyId() + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(1000))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].name").value("Tom1"))
                .andExpect(jsonPath("$[1].age").value(19))
                .andExpect(jsonPath("$[1].gender").value("female"))
                .andExpect(jsonPath("$[1].salary").value(1001));
    }

    @Test
    void should_return_2_companies_when_called_get_paginated_given_3_companies_page_1_page_size_2() throws Exception {
        //given
        Company company1 = new Company("OOCL", 100, Collections.emptyList());
        Company company2 = new Company("TEST", 100, Collections.emptyList());
        companyRepository.insert(Arrays.asList(company1, company2));

        //when
        mockMvc.perform(get("/companies?page=1&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].companyId").isString())
                .andExpect(jsonPath("$[0].companyName").value("OOCL"))
                .andExpect(jsonPath("$[0].employeesNumber").value(100))
                .andExpect(jsonPath("$[0].employees").isEmpty())
                .andExpect(jsonPath("$[1].companyId").isString())
                .andExpect(jsonPath("$[1].companyName").value("Test"))
                .andExpect(jsonPath("$[1].employeesNumber").value(100))
                .andExpect(jsonPath("$[1].employees").isEmpty());
    }

    @Test
    void should_return_created_company_when_called_create_given_company() throws Exception {
        //given
        String companyJson = "{\n" +
                "    \"companyName\": \"Test\",\n" +
                "    \"employeesNumber\": 100,\n" +
                "    \"employees\": [\n" +
                "    ]\n" +
                "}";

        //when
        mockMvc.perform(post("/companies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(companyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyId").isString())
                .andExpect(jsonPath("$.companyName").value("Test"))
                .andExpect(jsonPath("$.employeesNumber").value(100))
                .andExpect(jsonPath("$.employees").isEmpty());
    }
}
