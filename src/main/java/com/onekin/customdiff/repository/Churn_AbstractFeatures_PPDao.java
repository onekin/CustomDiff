package com.onekin.customdiff.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.Churn_AbstractFeatures_PP;

@Transactional
public interface Churn_AbstractFeatures_PPDao extends CrudRepository<Churn_AbstractFeatures_PP, Long> {

}
