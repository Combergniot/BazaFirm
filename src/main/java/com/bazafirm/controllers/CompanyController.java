package com.bazafirm.controllers;

import com.bazafirm.model.Company;
import com.bazafirm.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/companies")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/all")
    public Iterable<Company> getAll() {
        Iterable<Company> companies = companyService.findAll();
        return companies;
    }

    @GetMapping("/{id}")
    public Optional<Company> getCompanyById(@PathVariable Long id) {
        Optional<Company> company = companyService.findById(id);
        return company;
    }

    @GetMapping("/name={name}")
    public Iterable<Company> getCompaniesByName(@PathVariable String name) {
        Iterable<Company> companies = companyService.findByName(name);
        return companies;
    }

    @GetMapping("/branch={branch}")
    public Iterable<Company> getCompaniesByBranch(@PathVariable String branch) {
        Iterable<Company> companies = companyService.findByBranch(branch);
        return companies;
    }

    @GetMapping("/region={region}")
    public Iterable<Company> getCompaniesByRegion(@PathVariable String region) {
        Iterable<Company> companies = companyService.findByBranch(region);
        return companies;
    }

}
