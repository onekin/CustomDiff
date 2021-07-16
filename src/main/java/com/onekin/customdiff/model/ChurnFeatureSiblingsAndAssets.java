package com.onekin.customdiff.model;

public class ChurnFeatureSiblingsAndAssets {
    int idFeatureSibling;
    String expression;
    int assetId;
    String assetName;
    int added;
    int deleted;
    long churn;

    public ChurnFeatureSiblingsAndAssets() {
    }

    public int getIdFeatureSibling() {
        return idFeatureSibling;
    }

    public void setIdFeatureSibling(int idFeatureSibling) {
        this.idFeatureSibling = idFeatureSibling;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void geAssetName(String assetName) {
        this.assetName = assetName;
    }

    public int getAdded() {
        return added;
    }

    public void setAdded(int added) {
        this.added = added;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public long getChurn() {
        return churn;
    }

    public void setChurn(long churn) {
        this.churn = churn;
    }
}
