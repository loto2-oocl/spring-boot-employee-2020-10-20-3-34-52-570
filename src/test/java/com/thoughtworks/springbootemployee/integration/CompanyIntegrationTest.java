package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
}
