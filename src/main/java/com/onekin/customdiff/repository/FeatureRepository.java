package com.onekin.customdiff.repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.Feature;
import org.springframework.data.repository.query.Param;


@Transactional
public interface FeatureRepository extends CrudRepository<Feature, Long> {

	Feature getFeatureByName(String name);

	Feature getFeatureByIdfeature(String idfeature);

	Iterable<Feature> getFeaturesByIdparent(int idparentfeature);

	@Query("Select DISTINCT(c.idfeature) from CustomsByFeatureAndCoreAsset c where c.idfeature!='No Feature'")
	List<String> getCustomizedFeatures();


	@Query("Select c.idparent from Feature c where c.idfeature=:featureId")
    String getFeatureParentId(@Param("featureId") String featureId);
}