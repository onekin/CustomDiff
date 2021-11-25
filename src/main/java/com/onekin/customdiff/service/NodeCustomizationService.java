package com.onekin.customdiff.service;

import java.util.List;

import com.onekin.customdiff.dao.ChurnFeatureSiblingsCoreAssetsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.customdiff.model.CustomsByFeatureAndCoreAsset;
import com.onekin.customdiff.repository.CustomsByFeatureAndCoreAssetRepository;

@Service
public class NodeCustomizationService {

    @Autowired
    private CustomsByFeatureAndCoreAssetRepository customsByFeatureAndCoreAssetRepo;

    @Autowired
    private ChurnFeatureSiblingsCoreAssetsDAO churnFeatureSiblingsCoreAssetsDAO;

    public List<CustomsByFeatureAndCoreAsset> getPackageCustomizations(String packageId) {
        int packageIdCleaned = Integer.valueOf(packageId.split("-")[1].replace("'", ""));
        return customsByFeatureAndCoreAssetRepo.findByIdpackage(packageIdCleaned);
    }

    public List<CustomsByFeatureAndCoreAsset> getFeatureCustomizations(String featureId) {
        featureId = featureId.split("-")[1];
        return customsByFeatureAndCoreAssetRepo.findByIdfeature(featureId);
    }

    public List<CustomsByFeatureAndCoreAsset> getAssetCustomizations(String assetId) {
        int assetIdCleaned = Integer.valueOf(assetId.split("-")[1].replace("'", ""));
        return customsByFeatureAndCoreAssetRepo.findByIdcoreasset(assetIdCleaned);
    }

    public List<CustomsByFeatureAndCoreAsset> getProductCustomizations(String productId) {
        int productIdCleaned = Integer.valueOf(productId.split("-")[1]);
        return customsByFeatureAndCoreAssetRepo.findByIdproductrelease(productIdCleaned);
    }

    public List<CustomsByFeatureAndCoreAsset> getFeatureSiblingCustomizations(String featuresiblingId) {
        featuresiblingId = featuresiblingId.split("-")[1];
        return churnFeatureSiblingsCoreAssetsDAO.getCustomizations(featuresiblingId);
    }
}
