package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exceptions.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    @InjectMocks
    CompanyService companyService;

    @Mock
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
        Company expected = new Company("1", "alibaba", 100, new ArrayList<>());
        when(companyRepository.findById("1")).thenReturn(Optional.of(expected));

        //when
        Company actual = companyService.getOne("1");

        //then
        assertEquals(expected, actual);
    }


    @Test
    void should_throw_company_not_found_exception_when_called_get_one_by_id_given_company_id_not_exists() {
        //given
        String companyId = "1";
        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        //when
        assertThrows(
            CompanyNotFoundException.class,
            () -> companyService.getOne(companyId),
            "Company with id:1 not found"
        );
    }

    @Test
    void should_return_all_employees_under_company_of_given_id_when_get_company_employees_given_company_id() {
        //given
        String companyId = "1";
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        List<Employee> expected = Arrays.asList(employee1, employee2);
        Company company = new Company(companyId, "Test", 100, expected);
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

        //when
        List<Employee> actual = companyService.getCompanyEmployees(companyId);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_throw_company_not_found_exception_when_get_company_employees_given_company_id_not_exists() {
        //given
        String companyId = "1";
        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        //when
        assertThrows(
            CompanyNotFoundException.class,
            () -> companyService.getCompanyEmployees(companyId),
            "Company with id:1 not found"
        );
    }

    @Test
    void should_return_2_companies_when_get_all_paginated_give_repository_with_three_companies_page_1_page_size_2() {
        //given
        Integer page = 1;
        Integer pageSize = 2;
        Company company1 = new Company();
        Company company2 = new Company();
        Page<Company> expected = new PageImpl<>(Arrays.asList(company1, company2));
        when(companyRepository.findAll((Pageable)any())).thenReturn(expected);

        //when
        Page<Company> actual = companyService.getAllPaginated(page, pageSize);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_created_company_when_create_given_an_empty_repository_and_company_request() {
        //given
        Company expected = new Company("1", "alibaba", 100, new ArrayList<>());
        when(companyRepository.insert(expected)).thenReturn(expected);

        //when
        Company actual = companyService.create(expected);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_updated_company_when_update_given_company_id_and_company_update_request() {
        //given
        String companyId = "1";
        Company newCompany = new Company(companyId, "alibaba", 10, new ArrayList<>());
        when(companyRepository.existsById(companyId)).thenReturn(true);
        when(companyRepository.save(newCompany)).thenReturn(newCompany);

        //when
        Company actual = companyService.update(companyId, newCompany);

        //then
        verify(companyRepository, times(1)).save(newCompany);
        assertEquals(newCompany, actual);
    }

    @Test
    void should_throw_company_not_found_exception_when_update_given_company_id_not_exists_and_company_update_request() {
        //given
        String companyId = "1";
        Company company = new Company();
        when(companyRepository.existsById(companyId)).thenReturn(false);

        //then
        assertThrows(
            CompanyNotFoundException.class,
            //when
            () -> companyService.update(companyId, company),
            "Company with id:1 not found"
        );
    }

    @Test
    void should_call_repository_delete_once_with_company_id_1_when_delete_given_company_id_1() {
        //given
        String companyId = "1";

        //when
        companyService.delete(companyId);

        //then
        verify(companyRepository, times(1)).deleteById(companyId);
    }
}