package com.onekin.customdiff.controller;

import java.util.List;
import java.util.Set;

import com.onekin.customdiff.model.SankeyItem;
import com.onekin.customdiff.model.SankeyNode;

public class SankeyResponse {

	private List<SankeyItem> sankeyItems;
	private Set<SankeyNode> nodes;

	public SankeyResponse(List<SankeyItem> sankeyItems, Set<SankeyNode> nodes) {
		super();
		this.sankeyItems = sankeyItems;
		this.nodes = nodes;
	}

	public List<SankeyItem> getSankeyItems() {
		return sankeyItems;
	}

	public void setSankeyItems(List<SankeyItem> sankeyItems) {
		this.sankeyItems = sankeyItems;
	}

	public Set<SankeyNode> getNodes() {
		return nodes;
	}

	public void setNodes(Set<SankeyNode> nodes) {
		this.nodes = nodes;
	}

}
