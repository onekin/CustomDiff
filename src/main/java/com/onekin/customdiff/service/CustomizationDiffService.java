package com.onekin.customdiff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.customdiff.model.CustomsByFeatureAndCoreAsset;
import com.onekin.customdiff.repository.CustomsByFeatureAndCoreAssetRepository;

@Service
public class CustomizationDiffService {

	@Autowired
	private CustomsByFeatureAndCoreAssetRepository customsByFeatureAndCoreAssetRepo;

	public List<CustomsByFeatureAndCoreAsset> getPackageAndProductsCustomizations(String packageId, String productId) {
		throw new UnsupportedOperationException();
	}

	public List<CustomsByFeatureAndCoreAsset> getProductAndAssetCustomizations(String assetId, String productId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CustomsByFeatureAndCoreAsset> getProductAndFeatureCustomizations(String productId, String featureId) {
		int productIdCleaned = Integer.valueOf(productId.split("-")[1]);
		return customsByFeatureAndCoreAssetRepo.findByIdproductreleaseAndIdfeature(productIdCleaned, featureId);
	}

	public List<CustomsByFeatureAndCoreAsset> getFeatureAndAssetCustomizations(String featureId, String assetId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CustomsByFeatureAndCoreAsset> getFeatureAndPackageCustomizations(String featureId, String packageId) {
		throw new UnsupportedOperationException();
	}
	
	
	
	
}
