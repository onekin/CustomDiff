package com.onekin.customdiff.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onekin.customdiff.model.ChurnParentFeaturesProductComponents;

@Repository
public interface ChurnParentFeaturesProductComponentsRepository
		extends CrudRepository<ChurnParentFeaturesProductComponents, Long> {

	Iterable<ChurnParentFeaturesProductComponents> getCustomsByIdproductrelease(int idproductrelease);

	@Query("SELECT new ChurnParentFeaturesProductComponents(c.id, c.id_parentfeature, c.parentfeaturename, c.package_name, c.idpackage, SUM(c.churn) as churn)"
			+ " FROM ChurnParentFeaturesProductComponents c where c.id_parentfeature in (:parentFeatures) group by c.id_parentfeature, c.idpackage")
	Iterable<ChurnParentFeaturesProductComponents> findByParentFeatureIdsInGroupedByPackage(
			@Param("parentFeatures") Set<Integer> parentFeatures);

	@Query("SELECT new ChurnParentFeaturesProductComponents(c.id, c.id_parentfeature, c.parentfeaturename, c.package_name, c.idpackage, SUM(c.churn) as churn)"
			+ " FROM ChurnParentFeaturesProductComponents c where c.id_parentfeature!='No Feature' AND c.idpackage in (:packageIds) group by c.id_parentfeature, idpackage")
	List<ChurnParentFeaturesProductComponents> findByIdPackageIn(@Param("packageIds") Set<Integer> packageIds);

	@Query("SELECT new ChurnParentFeaturesProductComponents(c.id, c.id_parentfeature, c.parentfeaturename, c.package_name, c.idpackage, SUM(c.churn) as churn)"
			+ " FROM ChurnParentFeaturesProductComponents c where c.id_parentfeature= :parentFeatureId AND c.idpackage in (:packageIds) group by c.id_parentfeature, idpackage")
	List<ChurnParentFeaturesProductComponents> findByParentFeatureIdAndPackageIdIn(
			@Param("packageIds") Set<Integer> packageIds, @Param("parentFeatureId") int parentFeatureId);

	@Query("SELECT new ChurnParentFeaturesProductComponents(c.id, c.id_parentfeature, c.parentfeaturename, c.package_name, c.idpackage, SUM(c.churn) as churn)"
			+ " FROM ChurnParentFeaturesProductComponents c where c.id_parentfeature IN (:parentFeatureIds) AND c.idpackage= :packageId group by c.id_parentfeature, idpackage")
	List<ChurnParentFeaturesProductComponents> findByParentFeatureIdInAndPackageId( @Param("parentFeatureIds") Set<Integer> parentFeatureIds, @Param("packageId") int packageId);

	
	@Query("SELECT new ChurnParentFeaturesProductComponents(c.id, c.id_parentfeature, c.parentfeaturename, c.package_name, c.idpackage, SUM(c.churn) as churn)"
			+ " FROM ChurnParentFeaturesProductComponents c where  c.idpackage= :packageId group by c.idpackage")
	List<ChurnParentFeaturesProductComponents> findByPackageId(@Param("packageId") int packageId);

}
