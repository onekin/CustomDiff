package com.onekin.customdiff.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.customdiff.model.ChurnCoreAssetsAndFeaturesByProduct;
import com.onekin.customdiff.model.ChurnFeaturesAndPackagesGrouped;
import com.onekin.customdiff.model.ChurnFeaturesPackageAssets;
import com.onekin.customdiff.model.ChurnPackageAndProduct;
import com.onekin.customdiff.model.ChurnProductPortfolioAndFeatures;
import com.onekin.customdiff.repository.ChurnCoreAssetsAndFeaturesByProductRepository;
import com.onekin.customdiff.repository.ChurnFeaturesComponentPackagesRepository;
import com.onekin.customdiff.repository.ChurnFeaturesPackageAssetsRepository;
import com.onekin.customdiff.repository.ChurnProductPortfolioAndFeaturesRepository;
import com.onekin.customdiff.utils.Formatting;

@Service
public class SankeyFilterService {
	
	@Autowired
	private ChurnProductPortfolioAndFeaturesRepository productAndFeaturesRepo;
	
	@Autowired
	private ChurnFeaturesPackageAssetsRepository featuresAndAssetsRepo;
	
	@Autowired
	private ChurnFeaturesComponentPackagesRepository featuresAndPackagesRepo;
	
	@Autowired
	private ChurnCoreAssetsAndFeaturesByProductRepository assetsAndProductRepo;
	
	
	
	



	public Iterator<ChurnProductPortfolioAndFeatures> getProductAndFeaturesChurnInFeatures(List<String> featureIds) {
		return productAndFeaturesRepo.findByIdFeatureIn(featureIds).iterator();
	}

	public List<ChurnFeaturesAndPackagesGrouped> getFeaturesAndPackagesChurnInFeaturesAndInPackages(List<String> featureIds, Set<String> rightPackages) {
		Set<Integer> rightPackageIds = Formatting.cleanListOfIds(rightPackages);
		return featuresAndPackagesRepo.findByIdfeatureInAndIdpackageIn(featureIds,rightPackageIds);
	}
	
	
	public List<ChurnFeaturesPackageAssets> getFeaturesAndAssetsChurnInFeaturesAndInAssets(List<String> featureIds, Set<String> rightAssets) {
		Set<Integer> rightAssetsIds = Formatting.cleanListOfIds(rightAssets);
		return featuresAndAssetsRepo.findByFeatureIdInAndIdcoreassetIn(featureIds,rightAssetsIds);
	}
	
	
	
	
	public List<ChurnPackageAndProduct> getPackagesAndProductsChurnInFeaturesAndInPackages(List<String> featureIds,
			Set<String> leftPackages) {
		Set<Integer> leftPackageIds = Formatting.cleanListOfIds(leftPackages);
		return assetsAndProductRepo.findByIdFeatureInAndPackageIdIn(featureIds,leftPackageIds);

	}
	
	
	public List<ChurnCoreAssetsAndFeaturesByProduct> getAssetsAndProductsChurnInFeaturesAndInPackages(List<String> featureIds,
			Set<String> leftAssets) {
		Set<Integer> leftAssetsIds = Formatting.cleanListOfIds(leftAssets);
		return assetsAndProductRepo.findByFeatureIdInAndIdcoreassetIn(featureIds,leftAssetsIds);

	}

	public List<ChurnPackageAndProduct> getPackagesAndProductsChurnInFeaturesAndNotInExistingPackages(
			List<String> featureIds, Set<String> leftPackages) {
		Set<Integer> leftPackageIds = Formatting.cleanListOfIds(leftPackages);
		return assetsAndProductRepo.findByIdFeatureInAndPackageIdNotIn(featureIds,leftPackageIds);
	}

	public List<ChurnFeaturesAndPackagesGrouped> getFeaturesAndPackagesChurnInFeaturesAndNotInPackages(
			List<String> featureIds, Set<String> allRightPackages) {
		Set<Integer> rightPackageIds = Formatting.cleanListOfIds(allRightPackages);
		return featuresAndPackagesRepo.findByIdfeatureInAndNotIdpackageIn(featureIds,rightPackageIds);
	}
	
	
	

}
