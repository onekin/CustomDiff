package com.onekin.customdiff.dao.extractor;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FeatureSiblingExpressionExtractor implements ResultSetExtractor<String> {

    @Override
    public String extractData(ResultSet rs) throws SQLException {
        String expression ="FeatureSibling: ";
        while (rs.next()) {
            expression+= rs.getString(1)+" ";
        }
        return expression;
    }

}