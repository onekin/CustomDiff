package com.onekin.customdiff.dao.extractor;

import com.onekin.customdiff.model.CustomsByFeatureAndCoreAsset;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChurnFeatureSiblingsCustomizationExtractor implements RowMapper<CustomsByFeatureAndCoreAsset>{
    @Override
    public CustomsByFeatureAndCoreAsset mapRow(ResultSet rs, int i) throws SQLException {
        CustomsByFeatureAndCoreAsset customsByFeatureAndCoreAsset = new CustomsByFeatureAndCoreAsset();
        customsByFeatureAndCoreAsset.setIdcustomization(rs.getInt(1));
        customsByFeatureAndCoreAsset.setIdfeature(rs.getString(2));
        customsByFeatureAndCoreAsset.setIdcoreasset(rs.getInt(3));
        customsByFeatureAndCoreAsset.setCapath(rs.getString(4));
        customsByFeatureAndCoreAsset.setCaname(rs.getString(5));
        customsByFeatureAndCoreAsset.setIdproductrelease(rs.getInt(6));
        customsByFeatureAndCoreAsset.setAdded(rs.getInt(7));
        customsByFeatureAndCoreAsset.setDeleted(rs.getInt(8));
        customsByFeatureAndCoreAsset.setCustom_diff(rs.getString(9));
        customsByFeatureAndCoreAsset.setMessages(rs.getString(10));
        customsByFeatureAndCoreAsset.setMaindiff(rs.getString(11));
        customsByFeatureAndCoreAsset.setExpression(rs.getString(12));
        return customsByFeatureAndCoreAsset;
    }
}
