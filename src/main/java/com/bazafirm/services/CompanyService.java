package com.bazafirm.services;

import com.bazafirm.model.Company;
import com.bazafirm.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Optional<Company> findById(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        return company;
    }

    public Iterable<Company> findByName(String name) {
        Iterable<Company> companies = companyRepository.findByName(name);
        return companies;
    }

    public Iterable<Company> findByBranch(String branch) {
        Iterable<Company> companies = companyRepository.findByBranch(branch);
        return companies;
    }

    public Iterable<Company> findByRegion(String region) {
        Iterable<Company> companies = companyRepository.findByRegion(region);
        return companies;
    }


}
