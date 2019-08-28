package com.onekin.customdiff.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.ChurnCoreAssetsAndFeaturesByProduct;

@Transactional
public interface  ChurnCoreAssetsAndFeaturesByProductRepository extends CrudRepository<ChurnCoreAssetsAndFeaturesByProduct, Long>{

	Iterable<ChurnCoreAssetsAndFeaturesByProduct> getCustomsByIdproductrelease(int idproductrelease);

	Iterable<ChurnCoreAssetsAndFeaturesByProduct> getCustomsByFeatureId(String idfeature);

	Iterable<ChurnCoreAssetsAndFeaturesByProduct> findByPackageId(int i);

}
