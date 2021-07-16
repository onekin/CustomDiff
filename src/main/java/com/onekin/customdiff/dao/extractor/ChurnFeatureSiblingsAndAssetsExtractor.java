package com.onekin.customdiff.dao.extractor;

import com.onekin.customdiff.model.ChurnFeatureSiblingsAndAssets;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChurnFeatureSiblingsAndAssetsExtractor  implements RowMapper<ChurnFeatureSiblingsAndAssets> {


    @Override
    public ChurnFeatureSiblingsAndAssets mapRow(ResultSet rs, int rowNum) throws SQLException {
        ChurnFeatureSiblingsAndAssets churnFeatureSiblingsAndAssets = new ChurnFeatureSiblingsAndAssets();
        churnFeatureSiblingsAndAssets.setIdFeatureSibling(rs.getInt(1));
        churnFeatureSiblingsAndAssets.setAssetId(rs.getInt(2));
        churnFeatureSiblingsAndAssets.geAssetName(rs.getString(3));
        churnFeatureSiblingsAndAssets.setAdded(rs.getInt(4));
        churnFeatureSiblingsAndAssets.setDeleted(rs.getInt(5));
        churnFeatureSiblingsAndAssets.setChurn(churnFeatureSiblingsAndAssets.getAdded()+churnFeatureSiblingsAndAssets.getDeleted());
        return churnFeatureSiblingsAndAssets;
    }
}