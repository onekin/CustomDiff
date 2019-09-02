package com.onekin.customdiff.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.ChurnFeaturesAndPackagesGrouped;
import com.onekin.customdiff.model.ChurnFeaturesComponentPackages;

@Transactional
public interface ChurnFeaturesComponentPackagesRepository extends CrudRepository<ChurnFeaturesComponentPackages, Long> {

	Iterable<ChurnFeaturesComponentPackages> getCustomsByIdproductrelease(int id_productrelease);

	Iterable<ChurnFeaturesComponentPackages> getCustomsByIdfeature(String idfeature);

	@Query(value="SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c GROUP BY c.idfeature, c.idpackage")
	Iterable<ChurnFeaturesAndPackagesGrouped> getCustomsGroupByFeatures();

	List<ChurnFeaturesComponentPackages> findByIdpackage(int packageId);


}
