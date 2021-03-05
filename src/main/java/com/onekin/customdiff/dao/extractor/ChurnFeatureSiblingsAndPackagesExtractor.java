package com.onekin.customdiff.dao.extractor;

import com.onekin.customdiff.model.ChurnFeatureSiblingsAndPackages;
import com.onekin.customdiff.model.ChurnProductsAndFeatureSiblings;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChurnFeatureSiblingsAndPackagesExtractor implements RowMapper<ChurnFeatureSiblingsAndPackages> {


    @Override
    public ChurnFeatureSiblingsAndPackages mapRow(ResultSet rs, int rowNum) throws SQLException {
        ChurnFeatureSiblingsAndPackages churnFeatureSiblingsAndPackages = new ChurnFeatureSiblingsAndPackages();
        churnFeatureSiblingsAndPackages.setIdFeatureSibling(rs.getInt(1));
        churnFeatureSiblingsAndPackages.setPackageId(rs.getInt(2));
        churnFeatureSiblingsAndPackages.setPackageName(rs.getString(3));
        churnFeatureSiblingsAndPackages.setAdded(rs.getInt(4));
        churnFeatureSiblingsAndPackages.setDeleted(rs.getInt(5));
        churnFeatureSiblingsAndPackages.setChurn(churnFeatureSiblingsAndPackages.getAdded()+churnFeatureSiblingsAndPackages.getDeleted());
        return churnFeatureSiblingsAndPackages;
    }
}