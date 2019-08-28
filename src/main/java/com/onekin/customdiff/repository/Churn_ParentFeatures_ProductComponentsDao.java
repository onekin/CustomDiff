package com.onekin.customdiff.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.Churn_ParentFeatures_ProductComponents;

@Transactional
public interface Churn_ParentFeatures_ProductComponentsDao extends CrudRepository<Churn_ParentFeatures_ProductComponents, Long>{

	Iterable<Churn_ParentFeatures_ProductComponents> getCustomsByIdproductrelease(int idproductrelease);

}
