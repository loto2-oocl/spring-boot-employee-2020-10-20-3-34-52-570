package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void tearEach() {
        companyRepository.deleteAll();
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
        mockMvc.perform(get("/companies/"+company.getCompanyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").isString())
                .andExpect(jsonPath("$.companyName").value("OOCL"))
                .andExpect(jsonPath("$.employeesNumber").value(100))
                .andExpect(jsonPath("$.employees").isEmpty());
    }
}
