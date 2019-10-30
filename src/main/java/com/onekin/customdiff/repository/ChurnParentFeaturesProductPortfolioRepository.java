package com.onekin.customdiff.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.onekin.customdiff.model.ChurnParentFeaturesProductPortfolio;

public interface ChurnParentFeaturesProductPortfolioRepository
		extends CrudRepository<ChurnParentFeaturesProductPortfolio, String> {

	@Query("Select c from ChurnParentFeaturesProductPortfolio c where c.idParentFeature!='No Feature'")
	Iterable<ChurnParentFeaturesProductPortfolio> findAll();

	@Query("Select new ChurnParentFeaturesProductPortfolio(c.id, c.idParentFeature, c.parentFeatureName, c.productId, c.productName, sum(churn)) from ChurnParentFeaturesProductPortfolio c where c.idParentFeature!='No Feature' group by c.productId")
	Iterable<ChurnParentFeaturesProductPortfolio> findAllGroupByProduct();

	@Query("Select c from ChurnParentFeaturesProductPortfolio c where c.idParentFeature IN (:parentFeatureids)")
	Iterable<ChurnParentFeaturesProductPortfolio> findByParentFeatureIdIn(@Param("parentFeatureids") Set<Integer> parentFeatureids);

	@Query("Select c from ChurnParentFeaturesProductPortfolio c where c.idParentFeature=:parentFeatureId group by c.idParentFeature, c.productId")
	List<ChurnParentFeaturesProductPortfolio> findByParentFeatureId(@Param("parentFeatureId")int parentFeatureId);
}
