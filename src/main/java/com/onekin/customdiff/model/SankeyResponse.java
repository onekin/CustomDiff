package com.onekin.customdiff.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

	public void removeLinksByParentId(List<SankeyNode> nodesToRemove) {
		List<String> idsToRemove = nodesToRemove.stream().map(SankeyNode::getId).collect(Collectors.toList());
		this.sankeyItems.removeIf(x-> idsToRemove.contains(x.getFrom()) ||idsToRemove.contains(x.getTo()));		
	}

	public void removeLinksById(String expandId) {
		this.sankeyItems.removeIf(x -> x.getFrom().equals(expandId) | x.getTo().equals(expandId));
		
	}

}
