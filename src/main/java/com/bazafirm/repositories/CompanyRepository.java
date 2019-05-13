package com.bazafirm.repositories;

import com.bazafirm.model.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {


    Optional<Company> findById(Long id);

    Iterable<Company> findByName(String title);

    Iterable<Company> findByBranch(String branch);

    Iterable<Company> findByRegion(String region);

}
