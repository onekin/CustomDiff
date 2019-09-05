package com.onekin.customdiff.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.onekin.customdiff.model.ChurnCoreAssetsAndFeaturesByProduct;
import com.onekin.customdiff.model.ChurnPackageAndProduct;

@Transactional
public interface ChurnCoreAssetsAndFeaturesByProductRepository
		extends CrudRepository<ChurnCoreAssetsAndFeaturesByProduct, String> {

	Iterable<ChurnCoreAssetsAndFeaturesByProduct> getCustomsByIdproductrelease(int idproductrelease);

	Iterable<ChurnCoreAssetsAndFeaturesByProduct> getCustomsByFeatureId(String idfeature);

	@Query(value = "SELECT new ChurnCoreAssetsAndFeaturesByProduct(c.id, c.idproductrelease, c.featureId, c.idcoreasset, "
			+ "c.pr_name, c.ca_name, c.ca_path, c.packageId, c.packageName, SUM(c.churn)) FROM ChurnCoreAssetsAndFeaturesByProduct c where c.packageId=:idPackage and featureId!='No Feature'  GROUP BY c.idproductrelease ,c.idcoreasset")
	Iterable<ChurnCoreAssetsAndFeaturesByProduct> findByPackageIdGroupByAssetId(@Param(value="idPackage") int idPackage);

	@Query(value = "SELECT new com.onekin.customdiff.model.ChurnPackageAndProduct(c.id, c.packageId, c.idproductrelease, "
			+ "c.pr_name, c.packageName, SUM(c.churn)) FROM ChurnCoreAssetsAndFeaturesByProduct c WHERE c.featureId!='No Feature' GROUP BY c.idproductrelease ,c.packageId")
	Iterable<ChurnPackageAndProduct> findGroupedByProductAndPackage();

	@Query(value = "SELECT new com.onekin.customdiff.model.ChurnPackageAndProduct(c.id, c.packageId, c.idproductrelease, "
			+ "c.pr_name, c.packageName, SUM(c.churn)) FROM ChurnCoreAssetsAndFeaturesByProduct c WHERE c.packageId = :packageId and c.featureId!='No Feature' GROUP BY c.idproductrelease")
	List<ChurnPackageAndProduct> findByIdPackage(@Param(value="packageId") Integer packageId);
	
	@Query(value = "SELECT new com.onekin.customdiff.model.ChurnPackageAndProduct(c.id, c.packageId, c.idproductrelease, "
			+ "c.pr_name, c.packageName, SUM(c.churn)) FROM ChurnCoreAssetsAndFeaturesByProduct c WHERE c.packageId = :packageId and c.featureId IN (:featureIds) GROUP BY c.idproductrelease")
	List<ChurnPackageAndProduct> findByIdPackageAndFeaturesIn(@Param(value="packageId") Integer packageId, @Param(value="featureIds") List<String> featureIds);

	@Query(value = "SELECT new com.onekin.customdiff.model.ChurnPackageAndProduct(c.id, c.packageId, c.idproductrelease, "
			+ "c.pr_name, c.packageName, SUM(c.churn)) FROM ChurnCoreAssetsAndFeaturesByProduct c WHERE  c.featureId IN (:featureIds) AND c.packageId IN (:packageIds) GROUP BY c.idproductrelease, c.packageId")
	List<ChurnPackageAndProduct> findByIdFeatureInAndPackageIdIn(@Param(value="featureIds") List<String> featureIds, @Param(value="packageIds") List<Integer> packageIds);	

	
	
	List<ChurnCoreAssetsAndFeaturesByProduct> findByFeatureIdInAndIdcoreassetIn(List<String> featureIds, List<Integer> leftAssetsIds);

	
	@Query(value = "SELECT new ChurnCoreAssetsAndFeaturesByProduct(c.id, c.idproductrelease, c.featureId, c.idcoreasset, "
			+ "c.pr_name, c.ca_name, c.ca_path, c.packageId, c.packageName, SUM(c.churn)) FROM ChurnCoreAssetsAndFeaturesByProduct c where c.packageId=:idPackage and c.featureId in (:featureIds) GROUP BY c.idproductrelease ,c.idcoreasset")
	List<ChurnCoreAssetsAndFeaturesByProduct> findByPackageIdAndFeaturesInGroupByAssetId(@Param(value="idPackage") int idPackage, @Param(value="featureIds") List<String> featureIds);

	
	@Query(value = "SELECT new com.onekin.customdiff.model.ChurnPackageAndProduct(c.id, c.packageId, c.idproductrelease, "
			+ "c.pr_name, c.packageName, SUM(c.churn)) FROM ChurnCoreAssetsAndFeaturesByProduct c WHERE  c.featureId IN (:featureIds) AND c.packageId NOT IN (:packageIds) GROUP BY c.idproductrelease, c.packageId")
	List<ChurnPackageAndProduct> findByIdFeatureInAndPackageIdNotIn(@Param(value="featureIds") List<String> featureIds,
			@Param(value="packageIds") List<Integer> packageIds);

}
