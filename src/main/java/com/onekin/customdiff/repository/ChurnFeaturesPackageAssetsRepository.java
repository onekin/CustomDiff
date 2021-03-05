package com.onekin.customdiff.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.onekin.customdiff.model.ChurnFeaturesPackageAssets;
import com.onekin.customdiff.model.ChurnPackageAndProduct;

@Transactional
public interface ChurnFeaturesPackageAssetsRepository extends CrudRepository<ChurnFeaturesPackageAssets, Long> {

	@Query(value = "SELECT new ChurnFeaturesPackageAssets(c.id, c.featureId, c.featurename, c.idcoreasset, "
			+ "c.caname, c.capath, c.packageId, SUM(c.churn)) FROM ChurnFeaturesPackageAssets c where c.packageId=:idPackage GROUP BY c.featureId ,c.idcoreasset")
	Iterable<ChurnFeaturesPackageAssets> findByPackageIdGroupedByFeaturesAndAsset(@Param("idPackage") int idPackage);

	Iterable<ChurnPackageAndProduct> findByFeatureIdIn(Collection<String> featureIds);

	List<ChurnFeaturesPackageAssets> findByFeatureIdInAndIdcoreassetIn(Set<String> featureIds,
			Set<Integer> rightAssetsIds);

	@Query(value = "SELECT new ChurnFeaturesPackageAssets(c.id, c.featureId, c.featurename, c.idcoreasset, "
			+ "c.caname, c.capath, c.packageId, SUM(c.churn)) FROM ChurnFeaturesPackageAssets c where c.packageId=:idPackage AND c.featureId in (:featureIds) GROUP BY c.featureId ,c.idcoreasset")
	List<ChurnFeaturesPackageAssets> findByPackageIdAndFeaturesInGroupedByFeaturesAndAsset(
			@Param("idPackage") int idPackage, @Param("featureIds") Set<String> featureIds);

	
	@Query(value = "SELECT new ChurnFeaturesPackageAssets(c.id, c.featureId, c.featurename, c.idcoreasset, "
			+ "c.caname, c.capath, c.packageId, SUM(c.churn)) FROM ChurnFeaturesPackageAssets c where c.idparentfeature=:parentFeatureId AND c.idcoreasset in (:assetIds) GROUP BY c.featureId ,c.idcoreasset")
	List<ChurnFeaturesPackageAssets> findByParentFeatureIdCoreAssetIdInAndGroupByFeatures(
			@Param("assetIds") Set<Integer> assetIds, @Param("parentFeatureId") int parentFeatureId);


}
