package com.onekin.customdiff.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.onekin.customdiff.model.ChurnFeaturesAndPackagesGrouped;
import com.onekin.customdiff.model.ChurnFeaturesComponentPackages;

@Transactional
public interface ChurnFeaturesComponentPackagesRepository extends CrudRepository<ChurnFeaturesComponentPackages, Long> {

	Iterable<ChurnFeaturesComponentPackages> getCustomsByIdproductrelease(int id_productrelease);

	Iterable<ChurnFeaturesComponentPackages> getCustomsByIdfeature(String idfeature);

	@Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c GROUP BY c.idfeature, c.idpackage")
	Iterable<ChurnFeaturesAndPackagesGrouped> getCustomsGroupByFeatures();

	List<ChurnFeaturesComponentPackages> findByIdpackage(int packageId);

	@Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idfeature in (:featureIds)GROUP BY c.idfeature, c.idpackage")
	Iterable<ChurnFeaturesAndPackagesGrouped> findByIdfeatureIn(@Param("featureIds") List<String> featureIds);

	@Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idfeature in (:featureIds) AND c.idpackage in (:packageIds) GROUP BY c.idfeature, c.idpackage")
	List<ChurnFeaturesAndPackagesGrouped> findByIdfeatureInAndIdpackageIn(@Param("featureIds") List<String> featureIds,
			@Param("packageIds") List<Integer> packageIds);

	@Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idfeature!='No Feature' and c.idpackage= :idPackage GROUP BY c.idfeature")
	List<ChurnFeaturesAndPackagesGrouped> findByIdPackageGroupByFeatures(@Param("idPackage") Integer idPackage);

	@Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idfeature IN (:featureIds) and c.idpackage= :idPackage GROUP BY c.idfeature")
	List<ChurnFeaturesAndPackagesGrouped> findByIdPackageAndFeaturesInGroupByFeatures(@Param("idPackage") Integer idPackage,
			@Param("featureIds") List<String> featureIds);

	@Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idfeature in (:featureIds) AND c.idpackage not in (:packageIds) GROUP BY c.idfeature, c.idpackage")
	List<ChurnFeaturesAndPackagesGrouped> findByIdfeatureInAndNotIdpackageIn(@Param("featureIds") List<String> featureIds,
			@Param("packageIds") List<Integer> packageIds);

}
