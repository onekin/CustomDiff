package com.onekin.customdiff.model;


public class ChurnProductsAndFeatureSiblings {
    int idFeatureSibling;
    String expression;
    // int id_product;
    int idProduct;
    String productName;
    int added;
    int deleted;
    long churn;

    public ChurnProductsAndFeatureSiblings() {
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

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
