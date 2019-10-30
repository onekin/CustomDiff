package com.onekin.customdiff.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.Feature;



@Transactional
public interface FeatureRepository extends CrudRepository<Feature, Long> {

	Feature getFeatureByName(String name);

	Feature getFeatureByIdfeature(String idfeature);

	Iterable<Feature> getFeaturesByIdparent(int idparentfeature);

	@Query("Select DISTINCT(c.idfeature) from CustomsByFeatureAndCoreAsset c where c.idfeature!='No Feature'")
	List<String> getCustomizedFeatures();


}