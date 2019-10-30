package com.onekin.customdiff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onekin.customdiff.model.CustomsByFeatureAndCoreAsset;
import com.onekin.customdiff.service.NodeCustomizationService;

@Controller
@RequestMapping("/node-customizations")
public class NodeCustomizationController {

	@Autowired
	private NodeCustomizationService nodeCustomizationService;

	@GetMapping("/package/{packageId}")
	public String getPackageCustomizations(@PathVariable(name = "packageId", required = true) String packageId,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> customizations = nodeCustomizationService
				.getPackageCustomizations(packageId);
		model.addAttribute("customizations", customizations);
		model.addAttribute("nodeType","package");
		//TODO get package name
		model.addAttribute("nodeName","Package");

		return "node-diffs";
	}

	@GetMapping("/feature/{featureId}")
	public String getFeatureCustomizations(@PathVariable(name = "featureId", required = true) String featureId,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> customizations = nodeCustomizationService
				.getFeatureCustomizations(featureId);
		model.addAttribute("customizations", customizations);
		model.addAttribute("nodeType","feature");
		model.addAttribute("nodeName",featureId);
		return "node-diffs";
	}

	@GetMapping("/asset/{assetId}")
	public String getAssetCustomizations(@PathVariable(name = "assetId", required = true) String assetId, Model model) {
		List<CustomsByFeatureAndCoreAsset> customizations = nodeCustomizationService.getAssetCustomizations(assetId);
		model.addAttribute("customizations", customizations);
		model.addAttribute("nodeType","core asset");
		model.addAttribute("nodeName",customizations.get(0).getCapath());

		return "node-diffs";
	}

	@GetMapping("/product/{productId}")
	public String getProductCustomizations(@PathVariable(name = "productId", required = true) String productId,
			Model model) {
		List<CustomsByFeatureAndCoreAsset> customizations = nodeCustomizationService
				.getProductCustomizations(productId);
		model.addAttribute("customizations", customizations);
		model.addAttribute("nodeType","product release");
		model.addAttribute("nodeName",customizations.get(0).getPrname());
		return "node-diffs";
	}

}
