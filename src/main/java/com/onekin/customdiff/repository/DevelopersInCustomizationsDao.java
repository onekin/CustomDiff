package com.onekin.customdiff.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.onekin.customdiff.model.DevelopersInCustomizations;

@Transactional
public interface DevelopersInCustomizationsDao extends CrudRepository<DevelopersInCustomizations, Long>{

}
