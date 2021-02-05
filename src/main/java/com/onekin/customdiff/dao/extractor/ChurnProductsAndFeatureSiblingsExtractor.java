package com.onekin.customdiff.dao.extractor;

import com.onekin.customdiff.model.ChurnProductsAndFeatureSiblings;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChurnProductsAndFeatureSiblingsExtractor implements RowMapper<ChurnProductsAndFeatureSiblings> {


    @Override
    public ChurnProductsAndFeatureSiblings mapRow(ResultSet rs,int rowNum) throws SQLException {
        ChurnProductsAndFeatureSiblings churnProductsAndFeatureSiblings = new ChurnProductsAndFeatureSiblings();
        churnProductsAndFeatureSiblings.setIdFeatureSibling(rs.getInt(1));
        churnProductsAndFeatureSiblings.setIdProduct(rs.getInt(2));
        churnProductsAndFeatureSiblings.setProductName(rs.getString(3));
        churnProductsAndFeatureSiblings.setAdded(rs.getInt(4));
        churnProductsAndFeatureSiblings.setDeleted(rs.getInt(5));
        churnProductsAndFeatureSiblings.setChurn(churnProductsAndFeatureSiblings.getAdded()+churnProductsAndFeatureSiblings.getDeleted());
        return churnProductsAndFeatureSiblings;
    }
}
