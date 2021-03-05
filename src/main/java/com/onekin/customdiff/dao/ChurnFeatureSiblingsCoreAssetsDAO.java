package com.onekin.customdiff.dao;

import com.onekin.customdiff.dao.extractor.ChurnFeatureSiblingsAndPackagesExtractor;
import com.onekin.customdiff.dao.extractor.ChurnProductsAndFeatureSiblingsExtractor;
import com.onekin.customdiff.model.ChurnFeatureSiblingsAndPackages;
import com.onekin.customdiff.model.ChurnProductsAndFeatureSiblings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ChurnFeatureSiblingsCoreAssetsDAO {


    private final String CHURN_FEATURE_SIBLING_PER_PACKAGE= "SELECT fb.id_feature_group, cp.idpackage, cp.name, sum(cf.lines_added), sum(cf.lines_deleted) " +
            "FROM  customization_fact cf " +
            "inner join variation_point vp on cf.idvariationpoint=vp.idvariationpoint " +
            "inner join core_asset ca on ca.idcoreasset=vp.idcoreasset "+
            "inner join component_package cp on ca.idpackage=cp.idpackage "+
            "inner join feature_bridge fb on fb.id_feature_group=vp.id_feature_group " +
            "where fb.id_feature=:idFeature  and cp.idpackage in (:packageIds) " +
            "group by fb.id_feature_group, ca.idpackage;";

    private final String CHURN_FEATURE_SIBLING= "SELECT fb.id_feature_group, cp.idpackage, cp.name, sum(cf.lines_added), sum(cf.lines_deleted) " +
            "FROM  customization_fact cf " +
            "inner join variation_point vp on cf.idvariationpoint=vp.idvariationpoint " +
            "inner join core_asset ca on ca.idcoreasset=vp.idcoreasset "+
            "inner join component_package cp on ca.idpackage=cp.idpackage "+
            "inner join feature_bridge fb on fb.id_feature_group=vp.id_feature_group " +
            "where  fb.id_feature_group in (:featureSiblingIds) " +
            "group by fb.id_feature_group, ca.idpackage;";

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;


    public List<ChurnFeatureSiblingsAndPackages> getChurnFeatureSiblingsCoreAssetsInPackages(String idFeature, Set<Integer> packageIds) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("idFeature", idFeature);
        parameters.addValue("packageIds",packageIds);
        return namedJdbcTemplate.query(CHURN_FEATURE_SIBLING_PER_PACKAGE, parameters, new ChurnFeatureSiblingsAndPackagesExtractor());

    }

    public List<ChurnFeatureSiblingsAndPackages> getChurnFeatureSiblingsCoreAssets(Set<Integer> featureSiblingIds) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("featureSiblingIds", featureSiblingIds);
        return namedJdbcTemplate.query(CHURN_FEATURE_SIBLING, parameters, new ChurnFeatureSiblingsAndPackagesExtractor());

    }
}
