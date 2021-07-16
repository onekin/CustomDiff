package com.onekin.customdiff.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.onekin.customdiff.dao.ChurnFeatureSiblingsProductsDAO;
import com.onekin.customdiff.model.ChurnFeatureSiblingsAndPackages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.customdiff.model.CustomsByFeatureAndCoreAsset;
import com.onekin.customdiff.repository.CustomsByFeatureAndCoreAssetRepository;

@Service
public class LinkCustomizationDiffService {

	@Autowired
	private CustomsByFeatureAndCoreAssetRepository customsByFeatureAndCoreAssetRepo;

	@Autowired
	private ChurnFeatureSiblingsProductsDAO featureSiblingsProductsDAO;

	public List<CustomsByFeatureAndCoreAsset> getPackageAndProductsCustomizations(String packageId, String productId) {
		int productIdCleaned = Integer.valueOf(productId.split("-")[1]);
		int packageIdCleaned = Integer.valueOf(packageId.split("-")[1].replace("'", ""));

		return customsByFeatureAndCoreAssetRepo.findByIdpackageAndIdproductrelease(packageIdCleaned, productIdCleaned);
	}

	public List<CustomsByFeatureAndCoreAsset> getProductAndAssetCustomizations(String assetId, String productId) {
		int productIdCleaned = Integer.valueOf(productId.split("-")[1]);
		int assetIdCleaned = Integer.valueOf(assetId.split("-")[1].replace("'", ""));

		return customsByFeatureAndCoreAssetRepo.findByIdcoreassetAndIdproductrelease(assetIdCleaned, productIdCleaned);
	}

	public List<CustomsByFeatureAndCoreAsset> getProductAndFeatureCustomizations(String productId, String featureId) {
		int productIdCleaned = Integer.valueOf(productId.split("-")[1]);
		featureId = featureId.split("-")[1];
		return customsByFeatureAndCoreAssetRepo.findByIdproductreleaseAndIdfeature(productIdCleaned, featureId);
	}

	public List<CustomsByFeatureAndCoreAsset> getFeatureAndAssetCustomizations(String featureId, String assetId) {
		int assetIdCleaned = Integer.valueOf(assetId.split("-")[1].replace("'", ""));
		featureId = featureId.split("-")[1];
		return customsByFeatureAndCoreAssetRepo.findByIdcoreassetAndIdfeature(assetIdCleaned, featureId);
	}

	public List<CustomsByFeatureAndCoreAsset> getFeatureAndPackageCustomizations(String featureId, String packageId) {
		int packageIdCleaned = Integer.valueOf(packageId.split("-")[1].replace("'", ""));
		featureId = featureId.split("-")[1];
		return  customsByFeatureAndCoreAssetRepo.findByIdpackageAndIdfeature(packageIdCleaned, featureId);
	}

	public Set<String> getTangledFeatures(List<CustomsByFeatureAndCoreAsset> customizations, String featureName) {
		Set<String> tangledFeatures = new HashSet<>();
		if(featureName.contains("fe-")){
			featureName=featureName.split("-")[1];
		}
		for(CustomsByFeatureAndCoreAsset customByFeatureAndPackage: customizations){
			List<String> featureNames = customsByFeatureAndCoreAssetRepo.findFeatureByCustomId(customByFeatureAndPackage.getIdcustomization());
			if(!featureNames.isEmpty()) {
				tangledFeatures.addAll(featureNames);
			}
		}
		return tangledFeatures;
	}

    public List<CustomsByFeatureAndCoreAsset> getProductAndFeatureSibling(String from, String to) {
		int productIdCleaned = Integer.valueOf(from.split("-")[1]);
		int featureSiblingIdCleaned = Integer.valueOf(to.split("-")[1].replace("'", ""));

		return featureSiblingsProductsDAO.findByIdproductreleaseAndIdFeatureSibling(featureSiblingIdCleaned, productIdCleaned);
    }

	public List<CustomsByFeatureAndCoreAsset> getFeatureSiblingAndPackage(String from, String to) {
		int featureSiblingIdCleaned = Integer.valueOf(from.split("-")[1]);
		int packageIdCleaned = Integer.valueOf(to.split("-")[1].replace("'", ""));

		return featureSiblingsProductsDAO.findIdFeatureSiblingAndPackage(featureSiblingIdCleaned, packageIdCleaned);
	}

	public List<CustomsByFeatureAndCoreAsset> getFeatureSiblingAndAssets(String from, String to) {
		int featureSiblingIdCleaned = Integer.valueOf(from.split("-")[1]);
		int assetCleaned = Integer.valueOf(to.split("-")[1].replace("'", ""));

		return featureSiblingsProductsDAO.findIdFeatureSiblingAndAsset(featureSiblingIdCleaned, assetCleaned);
	}
}
