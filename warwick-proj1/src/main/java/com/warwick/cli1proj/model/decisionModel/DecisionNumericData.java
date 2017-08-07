package com.warwick.cli1proj.model.decisionModel;

import java.util.Map;

public class DecisionNumericData {
	private int id;
	private int decision;
	private Map<String, Double>numericData;
	
	
	
	public DecisionNumericData() {
		super();
	}
	
	
	public DecisionNumericData(int id, int decision, Map<String, Double> numericData) {
		super();
		this.id = id;
		this.decision = decision;
		this.numericData = numericData;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDecision() {
		return decision;
	}
	public void setDecision(int decision) {
		this.decision = decision;
	}
	public Map<String, Double> getNumericData() {
		return numericData;
	}
	public void setNumericData(Map<String, Double> numericData) {
		this.numericData = numericData;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DecisionNumericData other = (DecisionNumericData) obj;
		if (decision != other.decision)
			return false;
		if (id != other.id)
			return false;
		if (numericData == null) {
			if (other.numericData != null)
				return false;
		} else if (!numericData.equals(other.numericData))
			return false;
		return true;
	}
	
	
	
}
