package com.onekin.customdiff.repository;

import jakarta.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.CustomsForCaPaFeature;

@Transactional
 public interface CustomsForCaPaFeatureDao extends CrudRepository<CustomsForCaPaFeature, Long>{

	public Iterable<CustomsForCaPaFeature> findCustomsByFeaturemodified(String featurenamemodified);
	
}

