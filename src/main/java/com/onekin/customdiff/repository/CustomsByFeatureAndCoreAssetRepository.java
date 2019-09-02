package com.onekin.customdiff.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onekin.customdiff.model.CustomsByFeatureAndCoreAsset;

@Repository
public interface CustomsByFeatureAndCoreAssetRepository  extends  CrudRepository<CustomsByFeatureAndCoreAsset, Long>{
	Iterable<CustomsByFeatureAndCoreAsset> findByIdfeature(String idFeature);
	Iterable<CustomsByFeatureAndCoreAsset> findByIdproductrelease(int idProductRelease);
	Iterable<CustomsByFeatureAndCoreAsset> findByIdcoreasset(int idCoreAsset);
	List<CustomsByFeatureAndCoreAsset> findByIdproductreleaseAndIdfeature(int idproductrelease, String idfeature);
	List<CustomsByFeatureAndCoreAsset> findByIdcoreassetAndIdfeature(int idCoreAsset, String idFeature);
	List<CustomsByFeatureAndCoreAsset> findByIdcoreassetAndIdproductrelease(int idCoreAsset, int idProductRelease);
	
}
