package com.onekin.customdiff.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.ChurnProductPortfolioAndFeatures;

@Transactional
public interface ChurnProductPortfolioAndFeaturesRepository extends CrudRepository<ChurnProductPortfolioAndFeatures, Long> {

	Iterable<ChurnProductPortfolioAndFeatures> findByIdFeatureIn(List<String> featureIds);
	
	
	

}
