package com.onekin.customdiff.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onekin.customdiff.model.ChurnPackageAndProduct;

@Repository
public interface ChurnPackageAndProductRepository  extends CrudRepository<ChurnPackageAndProduct, Long>{

	List<ChurnPackageAndProduct> findByIdPackage(int idPackage);

}
