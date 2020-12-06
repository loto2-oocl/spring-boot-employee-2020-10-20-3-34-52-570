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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyIntegrationTest {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_get_all_companies_when_called_get_all_given_companies() throws Exception {
        //given
        Company company = new Company("OOCL", 100);
        companyRepository.insert(company);

        //when
        mockMvc.perform(get("/companies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].companyId").isString())
            .andExpect(jsonPath("$[0].companyName").value("OOCL"))
            .andExpect(jsonPath("$[0].employeesNumber").value(100));
    }

    @Test
    void should_return_specific_company_when_called_get_one_by_id_given_company_id_and_company() throws Exception {
        //given
        Company company = new Company("OOCL", 100);
        companyRepository.insert(company);

        //when
        mockMvc.perform(get("/companies/" + company.getCompanyId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.companyId").isString())
            .andExpect(jsonPath("$.companyName").value("OOCL"))
            .andExpect(jsonPath("$.employeesNumber").value(100));
    }

    @Test
    void should_return_employees_list_when_called_get_company_employees_given_id_and_company() throws Exception {
        //given
        Company company = new Company("OOCL", 100);
        companyRepository.insert(company);
        Employee employee1 = new Employee("Tom", 18, "male", 1000, company.getCompanyId());
        Employee employee2 = new Employee("Tom1", 19, "female", 1001, company.getCompanyId());
        employeeRepository.insert(Arrays.asList(employee1, employee2));

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
        Company company1 = new Company("OOCL", 100);
        Company company2 = new Company("TEST", 100);
        companyRepository.insert(Arrays.asList(company1, company2));

        //when
        mockMvc.perform(
            get("/companies?page=1&pageSize=2")
                .param("page", "1")
                .param("pageSize'", "2")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size").value(2))
            .andExpect(jsonPath("$.content[0].companyId").isString())
            .andExpect(jsonPath("$.content[0].companyName").value("OOCL"))
            .andExpect(jsonPath("$.content[0].employeesNumber").value(100))
            .andExpect(jsonPath("$.content[1].companyId").isString())
            .andExpect(jsonPath("$.content[1].companyName").value("TEST"))
            .andExpect(jsonPath("$.content[1].employeesNumber").value(100));
    }

    @Test
    void should_return_created_company_when_called_create_given_company() throws Exception {
        //given
        String companyJson = "{\n"
            + "    \"companyName\": \"Test\",\n"
            + "    \"employeesNumber\": 100\n"
            + "}";

        //when
        mockMvc.perform(post("/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(companyJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.companyId").isString())
            .andExpect(jsonPath("$.companyName").value("Test"))
            .andExpect(jsonPath("$.employeesNumber").value(100));
    }

    @Test
    void should_return_updated_company_when_called_with_update_given_company_id_and_update_company() throws Exception {
        //given
        Company company = new Company("Test", 100);
        companyRepository.insert(company);
        String updateCompanyJson = "{\n"
            + "    \"companyName\": \"Test1\",\n"
            + "    \"employeesNumber\": 1000\n"
            + "}";

        //when
        mockMvc.perform(put("/companies/" + company.getCompanyId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateCompanyJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.companyId").value(company.getCompanyId()))
            .andExpect(jsonPath("$.companyName").value("Test1"))
            .andExpect(jsonPath("$.employeesNumber").value(1000));
    }

    @Test
    void should_delete_company_when_called_delete_given_company_id() throws Exception {
        //given
        Company company = new Company("Test", 100);
        companyRepository.insert(company);

        //when
        mockMvc.perform(delete("/companies/" + company.getCompanyId()))
            .andExpect(status().isNoContent());
        assertEquals(0, companyRepository.findAll().size());
    }
}
