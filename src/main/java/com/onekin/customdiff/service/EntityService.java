package com.onekin.customdiff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.customdiff.model.ComponentPackage;
import com.onekin.customdiff.model.Feature;
import com.onekin.customdiff.model.ProductRelease;
import com.onekin.customdiff.repository.ComponentPackageRepository;
import com.onekin.customdiff.repository.FeatureRepository;
import com.onekin.customdiff.repository.ProductReleaseRepository;

@Service
public class EntityService {
	
	@Autowired
	private ProductReleaseRepository productRepository;
	
	@Autowired
	private FeatureRepository featureRepository;
	
	@Autowired
	private ComponentPackageRepository packageRepository;
	
	public Iterable<ProductRelease> getProducts() {
		return productRepository.findAll();
	}


	public Iterable<Feature> getFeatures() {
		return featureRepository.findAll();
	}


	public Iterable<ComponentPackage> getPackages() {
		return packageRepository.findAll();
	}
}
