package com.onekin.customdiff.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.onekin.customdiff.model.ChurnParentFeaturesAndCoreAssets;

public interface ChurnParentFeaturesAndCoreAssetsRepository
		extends CrudRepository<ChurnParentFeaturesAndCoreAssets, String> {

	@Query("Select new ChurnParentFeaturesAndCoreAssets(c.id, c.parentFeatureId, c.parentFeatureName, c.packageId, c.packageName, c.coreAssetId, c.coreAssetName,  c.coreAssetPath, SUM(churn) as churn)"
			+ " from ChurnParentFeaturesAndCoreAssets c where c.coreAssetId in (:assetIds) GROUP BY c.parentFeatureId, c.coreAssetId")
	List<ChurnParentFeaturesAndCoreAssets> findByCoreAssetIdInAndGroupByFeatures(
			@Param("assetIds") Set<Integer> assetIds);

	@Query("Select new ChurnParentFeaturesAndCoreAssets(c.id, c.parentFeatureId, c.parentFeatureName, c.packageId, c.packageName, c.coreAssetId, c.coreAssetName,  c.coreAssetPath, SUM(churn) as churn)"
			+ " from ChurnParentFeaturesAndCoreAssets c where c.parentFeatureId IN (:parentFeatureIds) AND c.packageId = :packageId GROUP BY c.parentFeatureId, c.coreAssetId")
	List<ChurnParentFeaturesAndCoreAssets> findByPackageIdAndParentFeatureIdIn(
			@Param("parentFeatureIds") Set<Integer> parentFeatureIds, @Param("packageId") int packageId);

	@Query("Select new ChurnParentFeaturesAndCoreAssets(c.id, c.parentFeatureId, c.parentFeatureName, c.packageId, c.packageName, c.coreAssetId, c.coreAssetName,  c.coreAssetPath, SUM(churn) as churn)"
			+ " from ChurnParentFeaturesAndCoreAssets c where c.coreAssetId in (:assetIds) AND c.parentFeatureId= :parentFeatureId GROUP BY c.parentFeatureId, c.coreAssetId")
	List<ChurnParentFeaturesAndCoreAssets> findByParentFeatureIdCoreAssetIdInAndGroupByFeatures(
			@Param("assetIds")Set<Integer> assetIds, @Param("parentFeatureId") int parentFeatureId);

}
