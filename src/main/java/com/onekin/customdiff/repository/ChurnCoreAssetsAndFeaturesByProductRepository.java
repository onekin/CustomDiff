package com.onekin.customdiff.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.ChurnCoreAssetsAndFeaturesByProduct;

@Transactional
public interface  ChurnCoreAssetsAndFeaturesByProductRepository extends CrudRepository<ChurnCoreAssetsAndFeaturesByProduct, Long>{

	Iterable<ChurnCoreAssetsAndFeaturesByProduct> getCustomsByIdproductrelease(int idproductrelease);

	Iterable<ChurnCoreAssetsAndFeaturesByProduct> getCustomsByFeatureId(String idfeature);

	@Query(value="SELECT new ChurnCoreAssetsAndFeaturesByProduct(c.id, c.idproductrelease, c.featureId, c.idcoreasset, "
			+ "c.pr_name, c.ca_name, c.ca_path, c.packageId, SUM(c.churn)) FROM ChurnCoreAssetsAndFeaturesByProduct c where c.packageId=:idPackage GROUP BY c.idproductrelease ,c.idcoreasset")
	Iterable<ChurnCoreAssetsAndFeaturesByProduct> findByPackageIdGroupByAssetId(int idPackage);
	
	

}
