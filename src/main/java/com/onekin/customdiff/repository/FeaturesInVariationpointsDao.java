package com.onekin.customdiff.repository;

import jakarta.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.FeaturesInVariationpoints;

@Transactional
public interface FeaturesInVariationpointsDao extends CrudRepository<FeaturesInVariationpoints, Long> {

	Iterable<FeaturesInVariationpoints> getCoreAssetsByIdfeature(String idfeature);
}
