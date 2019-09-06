package com.onekin.customdiff.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.ChurnProductPortfolioAndFeatures;

@Transactional
public interface ChurnProductPortfolioAndFeaturesRepository
		extends CrudRepository<ChurnProductPortfolioAndFeatures, Long> {

	Iterable<ChurnProductPortfolioAndFeatures> findByIdFeatureIn(List<String> featureIds);

	@Query("Select SUM(c.churn) from ChurnProductPortfolioAndFeatures c ")
	Integer getAgregatedChurn();
	
	
	@Query("Select new ChurnProductPortfolioAndFeatures( c.id_pr, c.pr_name, SUM(c.churn)) from ChurnProductPortfolioAndFeatures c where c.idFeature!='No Feature' group by c.id_pr")
	List<ChurnProductPortfolioAndFeatures> findAllAggregatedInFeature();

}
