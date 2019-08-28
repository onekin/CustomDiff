package com.onekin.customdiff.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.CoreAsset;


@Transactional
public interface CoreAssetDao extends CrudRepository <CoreAsset, Long>{

	CoreAsset getCoreAssetByIdcoreasset(int idcoreasset);
}
