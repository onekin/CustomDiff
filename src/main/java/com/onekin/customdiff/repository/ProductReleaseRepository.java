package com.onekin.customdiff.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.onekin.customdiff.model.ProductRelease;

@Transactional
public interface ProductReleaseRepository extends CrudRepository<ProductRelease, Long>{

	Iterable<ProductRelease> getProductReleaseByName(String name);

	ProductRelease getProductReleaseByIdproductrelease(int idproductrelease);

}
