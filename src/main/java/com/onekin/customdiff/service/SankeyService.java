package com.onekin.customdiff.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.customdiff.model.ChurnCoreAssetsAndFeaturesByProduct;
import com.onekin.customdiff.model.ChurnFeaturesAndPackagesGrouped;
import com.onekin.customdiff.model.ChurnFeaturesComponentPackages;
import com.onekin.customdiff.model.ChurnFeaturesPackageAssets;
import com.onekin.customdiff.model.ChurnPackageAndProduct;
import com.onekin.customdiff.model.ChurnProductPortfolioAndFeatures;
import com.onekin.customdiff.repository.ChurnCoreAssetsAndFeaturesByProductRepository;
import com.onekin.customdiff.repository.ChurnFeaturesComponentPackagesRepository;
import com.onekin.customdiff.repository.ChurnFeaturesPackageAssetsRepository;
import com.onekin.customdiff.repository.ChurnPackageAndProductRepository;
import com.onekin.customdiff.repository.ChurnProductPortfolioAndFeaturesRepository;

@Service
public class SankeyService {

	@Autowired
	private ChurnProductPortfolioAndFeaturesRepository productAndFeaturesRepo;
	
	@Autowired
	private ChurnFeaturesPackageAssetsRepository featuresAndAssetsRepo;
	
	@Autowired
	private ChurnFeaturesComponentPackagesRepository featuresAndPackagesRepo;
	
	@Autowired
	private ChurnCoreAssetsAndFeaturesByProductRepository assetsAndProductRepo;
	
	@Autowired
	private ChurnPackageAndProductRepository packageAndProductRepo;
	
	public Iterator<ChurnProductPortfolioAndFeatures> getProductAndFeaturesChurn(){
		return productAndFeaturesRepo.findAll().iterator();
	}
	
	
	public Iterator<ChurnFeaturesPackageAssets> getFeaturesAndAssetsChurn(){
		return featuresAndAssetsRepo.findAll().iterator();
	}


	public Iterator<ChurnCoreAssetsAndFeaturesByProduct> getAssetsAndProductChurn() {
		return assetsAndProductRepo.findAll().iterator();
	}
	
	
	public Iterator<ChurnFeaturesAndPackagesGrouped> getFeaturesAndPackagesChurn() {
		return featuresAndPackagesRepo.getCustomsGroupByFeatures().iterator();
	}
	
	
	public Iterator<ChurnPackageAndProduct> getPackagesAndProductsChurn() {
		return packageAndProductRepo.findAll().iterator();
	}


	public Iterator<ChurnCoreAssetsAndFeaturesByProduct> getAssetsAndProductChurnByPackage(String idPackage) {
		return  assetsAndProductRepo.findByPackageIdGroupByAssetId(Integer.parseInt(idPackage)).iterator();
		
	}


	public Iterator<ChurnFeaturesPackageAssets> getAssetsAndFeaturesChurnByPackage(String idPackage) {
		return featuresAndAssetsRepo.findByPackageIdGroupedByFeaturesAndAsset(Integer.parseInt(idPackage)).iterator();
		
	}


	public List<ChurnPackageAndProduct> getPackagesAndProductsChurnByPackage(String packageId) {
		return packageAndProductRepo.findByIdPackage(Integer.valueOf(packageId));
	}


	public List<ChurnFeaturesComponentPackages> getFeaturesAndPackagesChurnByPackage(String packageId) {
		return featuresAndPackagesRepo.findByIdpackage(Integer.valueOf(packageId));
	}





}
