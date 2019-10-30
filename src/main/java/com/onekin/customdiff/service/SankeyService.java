package com.onekin.customdiff.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.customdiff.model.ChurnCoreAssetsAndFeaturesByProduct;
import com.onekin.customdiff.model.ChurnFeaturesAndPackagesGrouped;
import com.onekin.customdiff.model.ChurnFeaturesPackageAssets;
import com.onekin.customdiff.model.ChurnProductPortfolioAndFeatures;
import com.onekin.customdiff.model.SankeyLink;
import com.onekin.customdiff.model.SankeyNode;
import com.onekin.customdiff.model.enums.SankeyLinkType;
import com.onekin.customdiff.model.enums.SankeyNodeType;
import com.onekin.customdiff.repository.ChurnCoreAssetsAndFeaturesByProductRepository;
import com.onekin.customdiff.repository.ChurnFeaturesComponentPackagesRepository;
import com.onekin.customdiff.repository.ChurnFeaturesPackageAssetsRepository;
import com.onekin.customdiff.repository.ChurnProductPortfolioAndFeaturesRepository;
import com.onekin.customdiff.utils.PrefixConstants;

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

	public void setProductAndFeaturesChurn(List<SankeyLink> sankeyData, Set<SankeyNode> nodes) {
		Iterator<ChurnProductPortfolioAndFeatures> it = productAndFeaturesRepo.findAll().iterator();
		ChurnProductPortfolioAndFeatures churnProdFeature;
		SankeyLink sankeyLink;

		while (it.hasNext()) {
			churnProdFeature = it.next();
			if (!churnProdFeature.getIdFeature().equals("No Feature")) {
				sankeyLink = new SankeyLink(PrefixConstants.PRODUCT_PREFIX + churnProdFeature.getId_pr(),
						churnProdFeature.getIdFeature(), churnProdFeature.getChurn(), SankeyLinkType.PRODUCTFEATURE);
				sankeyData.add(sankeyLink);
				nodes.add(new SankeyNode(churnProdFeature.getIdFeature(), churnProdFeature.getFeaturemodified(), false,
						false, SankeyNodeType.FEATURE));
			}
		}

	}

	public Iterator<ChurnFeaturesPackageAssets> getFeaturesAndAssetsChurn() {
		return featuresAndAssetsRepo.findAll().iterator();
	}

	public Iterator<ChurnCoreAssetsAndFeaturesByProduct> getAssetsAndProductChurn() {
		return assetsAndProductRepo.findAll().iterator();
	}

	public void setFeaturesAndPackagesChurn(List<SankeyLink> sankeyData, Set<SankeyNode> nodes) {

		SankeyLink sankeyLink;

		ChurnFeaturesAndPackagesGrouped churnFeaturesAssets;
		Iterator<ChurnFeaturesAndPackagesGrouped> it = featuresAndPackagesRepo.getCustomsGroupByFeatures().iterator();

		while (it.hasNext()) {
			churnFeaturesAssets = it.next();
			if (!churnFeaturesAssets.getIdfeature().equals("No Feature")) {
				sankeyLink = new SankeyLink(churnFeaturesAssets.getIdfeature(),
						PrefixConstants.PACKAGE_PREFIX + churnFeaturesAssets.getIdpackage(),
						churnFeaturesAssets.getChurn(), SankeyLinkType.FEATUREPACKAGE);
				sankeyData.add(sankeyLink);
			}
		}
	}

	

	public void setInitialSankeyNodesAndLinks(List<SankeyLink> sankeyData, Set<SankeyNode> nodes) {
		/*
		 * setPackagesAndProductsChurn(sankeyData, nodes);
		 * setProductAndFeaturesChurn(sankeyData, nodes);
		 * setFeaturesAndPackagesChurn(sankeyData, nodes);
		 */
		setAggregatedProductAndFeatureChurn(sankeyData, nodes);

	}

	private void setAggregatedProductAndFeatureChurn(List<SankeyLink> sankeyData, Set<SankeyNode> nodes) {
		Integer agregatedChurn = productAndFeaturesRepo.getAgregatedChurn();
		SankeyLink sankeyLink = new SankeyLink("ALL-PR", "ALL-F", agregatedChurn, SankeyLinkType.PRODUCTFEATURE);
		sankeyData.add(sankeyLink);
		nodes.add(new SankeyNode("ALL-PR", "ALL PRODUCTS", true, false, SankeyNodeType.PRODUCT));
		nodes.add(new SankeyNode("ALL-F", "ALL FEATURES", true, false, SankeyNodeType.PARENTFEATURE));
	}

}
