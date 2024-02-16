package com.onekin.customdiff.repository;

import jakarta.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.ComponentPackage;

@Transactional
public interface ComponentPackageRepository extends CrudRepository< ComponentPackage, Long>{

	ComponentPackage getComponentPackageByIdpackage(int idpackage);

}
