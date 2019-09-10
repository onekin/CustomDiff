package com.onekin.customdiff.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.onekin.customdiff.model.ChurnProductPortfolioAndFeatures;

@Transactional
public interface ChurnProductPortfolioAndFeaturesRepository
		extends CrudRepository<ChurnProductPortfolioAndFeatures, Long> {

	Iterable<ChurnProductPortfolioAndFeatures> findByIdFeatureIn(Set<String> featureIds);

	@Query("Select SUM(c.churn) from ChurnProductPortfolioAndFeatures c where c.idFeature!='No Feature'")
	Integer getAgregatedChurn();
	
	
	@Query("Select new ChurnProductPortfolioAndFeatures( c.id_pr, c.pr_name, SUM(c.churn)) from ChurnProductPortfolioAndFeatures c where c.idFeature!='No Feature' group by c.id_pr")
	List<ChurnProductPortfolioAndFeatures> findAllAggregatedInFeature();

	List<ChurnProductPortfolioAndFeatures> findByParentFeatureId(int parentId);

	@Query("Select new ChurnProductPortfolioAndFeatures(c.id, c.idFeature, c.featuremodified, SUM(c.churn), c.parentFeatureId) from ChurnProductPortfolioAndFeatures c where c.idFeature!='No Feature' AND c.parentFeatureId=:parentFeatureId group by c.idFeature")
	List<ChurnProductPortfolioAndFeatures> findByParentFeatureIdAllProducts(@Param("parentFeatureId") int parentFeatureId); 

}
