package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    @InjectMocks
    CompanyService companyService;

    @MockBean
    CompanyRepository companyRepository;
}