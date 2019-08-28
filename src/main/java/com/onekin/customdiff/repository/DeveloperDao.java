package com.onekin.customdiff.repository;



import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.onekin.customdiff.model.Developer;

@Transactional
public interface DeveloperDao extends CrudRepository<Developer, Long> {

}
