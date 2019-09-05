package com.onekin.customdiff.model;

import com.onekin.customdiff.model.enums.SankeyLinkType;

public class SankeyItem {

	private String from;
	private String to;
	private long weight;
	private SankeyLinkType sankeyLinkType;

	public SankeyItem() {
		super();
	}

	public SankeyItem(String from, String to, int weight, SankeyLinkType sankeyType) {
		super();
		this.from = from;
		this.to = to;
		this.weight = weight;
		this.sankeyLinkType = sankeyType;

	}

	public SankeyItem(String from, String to, long weight, SankeyLinkType sankeyType) {
		super();
		this.from = from;
		this.to = to;
		this.weight = weight;
		this.sankeyLinkType = sankeyType;

	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "[" + from + "," + to + "," + weight + "]";
	}

	public SankeyLinkType getSankeyLinkType() {
		return sankeyLinkType;
	}

	public void setSankeyLinkType(SankeyLinkType sankeyType) {
		this.sankeyLinkType = sankeyType;
	}

}
