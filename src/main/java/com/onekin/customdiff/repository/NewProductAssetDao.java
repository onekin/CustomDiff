package com.onekin.customdiff.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.onekin.customdiff.model.NewProductAsset;

@Transactional
public interface NewProductAssetDao extends CrudRepository<NewProductAsset, Long>{
	
	NewProductAsset getProductAssetByIdasset(int idasset);

	NewProductAsset getProductAssetByPath(String path);

	NewProductAsset getProductAssetByName(String name);

}
