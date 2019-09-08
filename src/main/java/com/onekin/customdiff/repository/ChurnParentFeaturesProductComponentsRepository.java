package com.onekin.customdiff.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onekin.customdiff.model.ChurnParentFeaturesProductComponents;

@Repository
public interface ChurnParentFeaturesProductComponentsRepository extends CrudRepository<ChurnParentFeaturesProductComponents, Long>{

	Iterable<ChurnParentFeaturesProductComponents> getCustomsByIdproductrelease(int idproductrelease);
	
	
	@Query("Select new ChurnParentFeaturesProductComponents(c.id, c.id_parentfeature, c.parentfeaturename, c.package_name, c.idpackage, SUM(c.churn) as churn)"
			+ " from ChurnParentFeaturesProductComponents c where c.id_parentfeature in (:parentFeatures) group by c.id_parentfeature, id_parentfeature")
	Iterable<ChurnParentFeaturesProductComponents> findByParentFeatureIdsInGroupedByPackage(@Param("parentFeatures") Set<Integer> parentFeatures);


}
