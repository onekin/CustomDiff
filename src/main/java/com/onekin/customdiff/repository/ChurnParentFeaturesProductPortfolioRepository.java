package com.onekin.customdiff.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.ChurnParentFeaturesProductPortfolio;

public interface ChurnParentFeaturesProductPortfolioRepository extends CrudRepository<ChurnParentFeaturesProductPortfolio, String> {


	@Query("Select c from ChurnParentFeaturesProductPortfolio c where c.idParentFeature!='No Feature'")
	Iterable<ChurnParentFeaturesProductPortfolio> findAll();
	
	@Query("Select new ChurnParentFeaturesProductPortfolio(c.id, c.idParentFeature, c.parentFeatureName, c.productId, c.productName, sum(churn)) from ChurnParentFeaturesProductPortfolio c where c.idParentFeature!='No Feature' group by c.productId")
	Iterable<ChurnParentFeaturesProductPortfolio> findAllGroupByProduct();
}
