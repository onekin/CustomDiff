package com.onekin.customdiff.repository;


import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.ChurnFeaturesPackageAssets;
import com.onekin.customdiff.model.ChurnPackageAndProduct;

@Transactional
public interface ChurnFeaturesPackageAssetsRepository extends CrudRepository<ChurnFeaturesPackageAssets, Long>{


	Iterable<ChurnFeaturesPackageAssets> findByPackageId(int idPackage);
	
	Iterable<ChurnPackageAndProduct> findByFeatureIdIn(Collection<String> featureIds);


}
