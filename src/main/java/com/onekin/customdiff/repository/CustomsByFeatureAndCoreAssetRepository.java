package com.onekin.customdiff.repository;

import java.util.List;

import com.onekin.customdiff.model.ChurnCoreAssetsAndFeaturesByProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onekin.customdiff.model.CustomsByFeatureAndCoreAsset;

@Repository
public interface CustomsByFeatureAndCoreAssetRepository extends CrudRepository<CustomsByFeatureAndCoreAsset, Long> {
	List<CustomsByFeatureAndCoreAsset> findByIdfeature(String idFeature);

	List<CustomsByFeatureAndCoreAsset> findByIdproductrelease(int idProductRelease);

	List<CustomsByFeatureAndCoreAsset> findByIdcoreasset(int idCoreAsset);

	List<CustomsByFeatureAndCoreAsset> findByIdpackage(int idPackage);

	List<CustomsByFeatureAndCoreAsset> findByIdproductreleaseAndIdfeature(int idproductrelease, String idfeature);

	List<CustomsByFeatureAndCoreAsset> findByIdcoreassetAndIdfeature(int idCoreAsset, String idFeature);

	List<CustomsByFeatureAndCoreAsset> findByIdcoreassetAndIdproductrelease(int idCoreAsset, int idProductRelease);

	List<CustomsByFeatureAndCoreAsset> findByIdpackageAndIdproductrelease(int packageId, int productIdCleaned);

	List<CustomsByFeatureAndCoreAsset> findByIdpackageAndIdfeature(int packageIdCleaned, String featureId);


	@Query(value = "SELECT c.idfeature FROM CustomsByFeatureAndCoreAsset c where c.idcustomization=:idCustom")
	List<String> findFeatureByCustomId(@Param(value="idCustom")int idCustom);



}
