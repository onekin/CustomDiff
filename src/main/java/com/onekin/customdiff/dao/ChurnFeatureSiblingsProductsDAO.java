package com.onekin.customdiff.dao;

import com.onekin.customdiff.dao.extractor.ChurnFeatureSiblingsAndPackagesExtractor;
import com.onekin.customdiff.dao.extractor.CustomsByFeatureAndCoreAssetExtractor;
import com.onekin.customdiff.model.ChurnFeatureSiblingsAndPackages;
import com.onekin.customdiff.model.CustomsByFeatureAndCoreAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ChurnFeatureSiblingsProductsDAO {


    private final String CUSTOMS_FEATURE_SIBLING_AND_PRODUCT = "SELECT cf.idcustomization, fb.id_feature, ca.idcoreasset, ca.path, ca.name," +
            "cf.idproductrelease, sum(cf.lines_added), sum(cf.lines_deleted), cf.custom_diff, cf.message_set, cf.greater_diff, vp.expression " +
            "FROM  customization_fact cf " +
            "inner join variation_point vp on cf.idvariationpoint=vp.idvariationpoint " +
            "inner join core_asset ca on ca.idcoreasset=vp.idcoreasset " +
            "inner join component_package cp on ca.idpackage=cp.idpackage " +
            "inner join feature_bridge fb on fb.id_feature_group=vp.id_feature_group " +
            "where fb.id_feature_group=:featureSiblingId  and cf.idproductrelease = :productId " +
            "group by fb.id_feature_group, ca.idpackage;";

    private final String CUSTOMS_FEATURE_SIBLING_AND_PACKAGE= "SELECT cf.idcustomization, fb.id_feature, ca.idcoreasset, ca.path, ca.name," +
            "cf.idproductrelease, sum(cf.lines_added), sum(cf.lines_deleted), cf.custom_diff, cf.message_set, cf.greater_diff, vp.expression " +
            "FROM  customization_fact cf " +
            "inner join variation_point vp on cf.idvariationpoint=vp.idvariationpoint " +
            "inner join core_asset ca on ca.idcoreasset=vp.idcoreasset " +
            "inner join component_package cp on ca.idpackage=cp.idpackage " +
            "inner join feature_bridge fb on fb.id_feature_group=vp.id_feature_group " +
            "where fb.id_feature_group=:featureSiblingId  and ca.idpackage = :packageId " +
            "group by fb.id_feature_group, ca.idpackage;";


    private final String CUSTOMS_FEATURE_SIBLING_AND_ASSET= "SELECT cf.idcustomization, fb.id_feature, ca.idcoreasset, ca.path, ca.name," +
            "cf.idproductrelease, sum(cf.lines_added), sum(cf.lines_deleted), cf.custom_diff, cf.message_set, cf.greater_diff, vp.expression " +
            "FROM  customization_fact cf " +
            "inner join variation_point vp on cf.idvariationpoint=vp.idvariationpoint " +
            "inner join core_asset ca on ca.idcoreasset=vp.idcoreasset " +
            "inner join component_package cp on ca.idpackage=cp.idpackage " +
            "inner join feature_bridge fb on fb.id_feature_group=vp.id_feature_group " +
            "where fb.id_feature_group=:featureSiblingId  and ca.idcoreasset = :assetId " +
            "group by fb.id_feature_group, ca.idpackage;";

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;


    public List<CustomsByFeatureAndCoreAsset> findByIdproductreleaseAndIdFeatureSibling(int featureSiblingId, int productId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("featureSiblingId", featureSiblingId);
        parameters.addValue("productId", productId);
        return namedJdbcTemplate.query(CUSTOMS_FEATURE_SIBLING_AND_PRODUCT, parameters, new CustomsByFeatureAndCoreAssetExtractor());

    }

    public List<CustomsByFeatureAndCoreAsset> findIdFeatureSiblingAndPackage(int featureSiblingId, int packageId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("featureSiblingId", featureSiblingId);
        parameters.addValue("packageId", packageId);
        return namedJdbcTemplate.query(CUSTOMS_FEATURE_SIBLING_AND_PACKAGE, parameters, new CustomsByFeatureAndCoreAssetExtractor());
    }

    public List<CustomsByFeatureAndCoreAsset> findIdFeatureSiblingAndAsset(int featureSiblingId, int assetId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("featureSiblingId", featureSiblingId);
        parameters.addValue("assetId", assetId);
        return namedJdbcTemplate.query(CUSTOMS_FEATURE_SIBLING_AND_ASSET, parameters, new CustomsByFeatureAndCoreAssetExtractor());
    }
}
