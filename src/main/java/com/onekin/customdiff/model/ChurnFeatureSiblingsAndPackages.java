package com.onekin.customdiff.model;


public class ChurnFeatureSiblingsAndPackages {
    int idFeatureSibling;
    String expression;
    // int id_product;
    int packageId;
    String packageName;
    int added;
    int deleted;
    long churn;

    public ChurnFeatureSiblingsAndPackages() {
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

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
