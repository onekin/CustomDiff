package com.onekin.customdiff.repository;


import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.CustomsByFeatureAndCoreAsset;

@Transactional
public interface CustomsByFeatureAndCoreAssetDao  extends  CrudRepository<CustomsByFeatureAndCoreAsset, Long>{
	Iterable<CustomsByFeatureAndCoreAsset> getCustomsByIdfeature(String featureid);
	Iterable<CustomsByFeatureAndCoreAsset> getCustomsByIdproductrelease(int idproductrelease);
	Iterable<CustomsByFeatureAndCoreAsset> getCustomsByIdcoreasset(int idcoreasset);
	Iterable<CustomsByFeatureAndCoreAsset> getCustomsByIdproductreleaseAndIdfeature(int idproductrelease, String idfeature);
	
}
