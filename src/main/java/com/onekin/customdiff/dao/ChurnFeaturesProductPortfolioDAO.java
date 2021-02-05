package com.onekin.customdiff.dao;

import com.onekin.customdiff.dao.extractor.ChurnProductsAndFeatureSiblingsExtractor;
import com.onekin.customdiff.dao.extractor.FeatureSiblingExpressionExtractor;
import com.onekin.customdiff.model.ChurnProductsAndFeatureSiblings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChurnFeaturesProductPortfolioDAO {

    private final String CHURN_FEATURE_SIBLING_PER_PRODUCT = "SELECT fb.id_feature_group, pr.idproductrelease, pr.name, sum(cf.lines_added), sum(cf.lines_deleted) " +
            "FROM product_release pr inner join customization_fact cf on pr.idproductrelease=cf.idproductrelease " +
            "inner join variation_point vp on cf.idvariationpoint=vp.idvariationpoint " +
            "inner join feature_bridge fb on fb.id_feature_group=vp.id_feature_group " +
            "where fb.id_feature=:idFeature " +
            "group by fb.id_feature_group, pr.idproductrelease;";

    private final String FEATURE_SIBLINGS_EXPRESSION = "SELECT id_feature " +
            "FROM FEATURE_BRIDGE " +
            "where id_feature_group= :idFeature";


    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;


    public List<ChurnProductsAndFeatureSiblings> getChurnFeatureSiblingProducts(String idFeature) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("idFeature", idFeature);
        return namedJdbcTemplate.query(CHURN_FEATURE_SIBLING_PER_PRODUCT, parameters, new ChurnProductsAndFeatureSiblingsExtractor());

    }

    public void setFeatureSiblingsExpression(List<ChurnProductsAndFeatureSiblings> churnProductsAndFeaturesList) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (ChurnProductsAndFeatureSiblings churnProductsAndFeatureSiblings : churnProductsAndFeaturesList) {
            parameters.addValue("idFeature", churnProductsAndFeatureSiblings.getIdFeatureSibling());
            String expression = namedJdbcTemplate.query(FEATURE_SIBLINGS_EXPRESSION, parameters, new FeatureSiblingExpressionExtractor());
            churnProductsAndFeatureSiblings.setExpression(expression);
        }

    }
}
