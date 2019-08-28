package com.onekin.customdiff.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.Feature;



@Transactional
public interface FeatureRepository extends CrudRepository<Feature, Long> {

	Feature getFeatureByName(String name);

	Feature getFeatureByIdfeature(String idfeature);

	Iterable<Feature> getFeaturesByIdparent(int idparentfeature);


}