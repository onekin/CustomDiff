package com.onekin.customdiff.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.customdiff.model.ChurnFeaturesAndPackagesGrouped;
import com.onekin.customdiff.model.ChurnPackageAndProduct;
import com.onekin.customdiff.model.SankeyLink;
import com.onekin.customdiff.model.SankeyNode;
import com.onekin.customdiff.model.enums.SankeyLinkType;
import com.onekin.customdiff.model.enums.SankeyNodeType;
import com.onekin.customdiff.repository.ChurnCoreAssetsAndFeaturesByProductRepository;
import com.onekin.customdiff.repository.ChurnFeaturesComponentPackagesRepository;
import com.onekin.customdiff.utils.PrefixConstants;

@Service
public class CollapseService {

	@Autowired
	private ChurnCoreAssetsAndFeaturesByProductRepository assetsAndProductRepo;

	@Autowired
	private ChurnFeaturesComponentPackagesRepository featuresAndPackagesRepo;

	public void collapseLeftFilesIntoPackage(Set<SankeyNode> nodes, List<SankeyLink> sankeyLinks, String packageId) {
		List<ChurnPackageAndProduct> churnPackageAndProductList = assetsAndProductRepo
				.findByIdPackage(Integer.valueOf(packageId));
		SankeyNode node;
		SankeyLink sankeyLink;
		for (ChurnPackageAndProduct packageAndProductChurn : churnPackageAndProductList) {
			sankeyLink = new SankeyLink(PrefixConstants.PACKAGE_PREFIX + packageAndProductChurn.getIdPackage() + "'",
					PrefixConstants.PRODUCT_PREFIX + packageAndProductChurn.getIdProductRelease(),
					packageAndProductChurn.getChurn(), SankeyLinkType.PACKAGEPRODUCT);
			sankeyLinks.add(sankeyLink);
		}
		node = new SankeyNode(PrefixConstants.PACKAGE_PREFIX + churnPackageAndProductList.get(0).getIdPackage() + "'",
				churnPackageAndProductList.get(0).getPackageName(), true, false, SankeyNodeType.LEFTPACKAGE);
		nodes.add(node);
	}

	public void collapseRightFilesIntoPackage(Set<SankeyNode> nodes, List<SankeyLink> sankeyLinks, String packageId) {
		List<ChurnFeaturesAndPackagesGrouped> churnFeaturesPackagesList = featuresAndPackagesRepo
				.findByIdPackageGroupByFeatures(Integer.valueOf(packageId));
		SankeyNode node;
		SankeyLink sankeyLink;
		for (ChurnFeaturesAndPackagesGrouped churnFeaturesAndPackage : churnFeaturesPackagesList) {
			if (!churnFeaturesAndPackage.getIdfeature().equals("No Feature")) {
				sankeyLink = new SankeyLink(churnFeaturesAndPackage.getIdfeature(),
						PrefixConstants.PACKAGE_PREFIX + churnFeaturesAndPackage.getIdpackage(),
						churnFeaturesAndPackage.getChurn(), SankeyLinkType.FEATUREPACKAGE);
				sankeyLinks.add(sankeyLink);

			}
		}
		node = new SankeyNode(PrefixConstants.PACKAGE_PREFIX + churnFeaturesPackagesList.get(0).getIdpackage(),
				churnFeaturesPackagesList.get(0).getPackage_name(), true, false, SankeyNodeType.RIGHTPACKAGE);
		nodes.add(node);
	}

	public void collapseLeftFilesIntoPackageFiltered(Set<SankeyNode> nodes, List<SankeyLink> sankeyLinks,
			String packageId, List<String> featureIds) {
		List<ChurnPackageAndProduct> churnPackageAndProductList = assetsAndProductRepo
				.findByIdPackageAndFeaturesIn(Integer.valueOf(packageId), featureIds);
		SankeyNode node;
		SankeyLink sankeyLink;
		for (ChurnPackageAndProduct packageAndProductChurn : churnPackageAndProductList) {
			sankeyLink = new SankeyLink(PrefixConstants.PACKAGE_PREFIX + packageAndProductChurn.getIdPackage() + "'",
					PrefixConstants.PRODUCT_PREFIX + packageAndProductChurn.getIdProductRelease(),
					packageAndProductChurn.getChurn(), SankeyLinkType.PACKAGEPRODUCT);
			sankeyLinks.add(sankeyLink);
		}
		node = new SankeyNode(PrefixConstants.PACKAGE_PREFIX + churnPackageAndProductList.get(0).getIdPackage() + "'",
				churnPackageAndProductList.get(0).getPackageName(), true, false, SankeyNodeType.LEFTPACKAGE);
		nodes.add(node);
	}

	public void collapseRightFilesIntoPackageFiltered(Set<SankeyNode> nodes, List<SankeyLink> sankeyLinks,
			String packageId, List<String> featureIds) {
		List<ChurnFeaturesAndPackagesGrouped> churnFeaturesPackagesList = featuresAndPackagesRepo
				.findByIdPackageAndFeaturesInGroupByFeatures(Integer.valueOf(packageId), featureIds);
		SankeyNode node;
		SankeyLink sankeyLink;
		for (ChurnFeaturesAndPackagesGrouped churnFeaturesAndPackage : churnFeaturesPackagesList) {
			if (!churnFeaturesAndPackage.getIdfeature().equals("No Feature")) {
				sankeyLink = new SankeyLink(churnFeaturesAndPackage.getIdfeature(),
						PrefixConstants.PACKAGE_PREFIX + churnFeaturesAndPackage.getIdpackage(),
						churnFeaturesAndPackage.getChurn(), SankeyLinkType.FEATUREPACKAGE);
				sankeyLinks.add(sankeyLink);

			}
		}
		node = new SankeyNode(PrefixConstants.PACKAGE_PREFIX + churnFeaturesPackagesList.get(0).getIdpackage(),
				churnFeaturesPackagesList.get(0).getPackage_name(), true, false, SankeyNodeType.RIGHTPACKAGE);
		nodes.add(node);
	}
}
