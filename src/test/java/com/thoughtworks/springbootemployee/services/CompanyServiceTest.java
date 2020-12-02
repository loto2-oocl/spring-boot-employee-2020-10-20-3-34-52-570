package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    @Test
    void should_return_targeted_company_when_get_one_given_a_company_id_in_repository() {
        //given
        Company expected = new Company(1, "alibaba", 100, new ArrayList<>());
        when(companyRepository.findById(1)).thenCallRealMethod();
        when(companyRepository.findAll()).thenReturn(Collections.singletonList(expected));

        //when
        Company actual = companyService.getOne(1);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_all_employees_under_company_of_given_id_when_get_company_employees_given_company_id() {
        //given
        Integer companyId = 1;
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        List<Employee> expected = Arrays.asList(employee1, employee2);
        Company company = new Company(companyId, "alibaba", 100, expected);
        when(companyRepository.findById(companyId)).thenReturn(company);

        //when
        List<Employee> actual = companyService.getCompanyEmployees(companyId);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_2_companies_when_get_all_paginated_give_repository_with_three_companies_page_1_page_size_2() {
        //given
        Integer page = 1;
        Integer pageSize = 2;
        Company company1 = new Company();
        Company company2 = new Company();
        Company company3 = new Company();
        when(companyRepository.findAllPaginated(page, pageSize)).thenCallRealMethod();
        when(companyRepository.findAll()).thenReturn(Arrays.asList(company1, company2, company3));

        //when
        List<Company> actual = companyService.getAllPaginated(page, pageSize);

        //then
        assertEquals(pageSize, actual.size());
        assertEquals(Arrays.asList(company1, company2), actual);
    }
}