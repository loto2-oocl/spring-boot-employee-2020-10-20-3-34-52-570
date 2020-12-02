package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    @InjectMocks
    CompanyService companyService;

    @MockBean
    CompanyRepository companyRepository;

    @Test
    void should_return_all_companies_when_get_all_given_repository_with_all_companies() {
        //given
        List<Company> expected = Arrays.asList(new Company(), new Company(), new Company());
        when(companyRepository.findAll()).thenReturn(expected);

        //when
        List<Company> actual = companyService.getAll();

        //then
        assertEquals(expected, actual);
    }
}