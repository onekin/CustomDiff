package com.onekin.customdiff.repository;

import jakarta.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.ParentFeature;

@Transactional
public interface ParentFeatureDao extends CrudRepository <ParentFeature, Long>{

	ParentFeature getFeaturePArentByName(String name);

	ParentFeature getParentFeatureByIdparentfeature(int idparentfeature);

}
