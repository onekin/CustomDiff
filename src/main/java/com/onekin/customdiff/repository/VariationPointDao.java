package com.onekin.customdiff.repository;

import jakarta.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.VariationPoint;

@Transactional
public interface VariationPointDao extends CrudRepository <VariationPoint, Long > {
	
	VariationPoint getVariationPointByIdvariationpoint(int idvariationpoint);

}
