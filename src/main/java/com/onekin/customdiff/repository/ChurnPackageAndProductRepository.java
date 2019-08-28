package com.onekin.customdiff.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.onekin.customdiff.model.ChurnPackageAndProduct;

@Transactional
public interface ChurnPackageAndProductRepository  extends CrudRepository<ChurnPackageAndProduct, Long>{

	
	

	
}
