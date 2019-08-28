package com.onekin.customdiff.repository;


import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.CoreassetsAndFeatures;

@Transactional
public interface CoreassetsAndFeaturesDao extends CrudRepository <CoreassetsAndFeatures, Long>{

	

	Iterable<CoreassetsAndFeatures> getFeatureCoreAssetsByIdfeature(String idfeature);
	
	
}