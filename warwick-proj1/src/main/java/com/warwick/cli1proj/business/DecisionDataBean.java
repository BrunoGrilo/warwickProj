package com.warwick.cli1proj.business;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.warwick.cli1proj.exceptions.BaseException;
import com.warwick.cli1proj.model.decisionModel.DecisionNumericData;

@Component
public class DecisionDataBean {
	
	private static final String ID_CSV_KEY = "Id";
	private static final String DECISION_CSV_KEY = "Decision";
	
	/*
	 * Reads csv file converting lines to DecisionNumericData and then filters all invalid data
	 */
	public List<DecisionNumericData> loadFilteredCsvDecisionData(InputStream f) throws IOException, BaseException{
		List<DecisionNumericData> results = new ArrayList<DecisionNumericData>();
		Short decision;
		Double varValue;
		int idIndex = -1, decisionIndex = -1;
		Map<Integer, String> indexToVarKeyMap = new HashMap<Integer, String>();
		Map<String, Double> decision1MinVarValuesMap = new HashMap<String, Double>();
		Map<String, Double> decision1MaxVarValuesMap = new HashMap<String, Double>();
		
		try (InputStreamReader inputReader = new InputStreamReader(f); 
			CSVReader reader = new CSVReader(inputReader, ',');){
			
			// read line by line
			String[] record = null;
			
			try {
				record = reader.readNext();
				
				//read csv header
				for (int i = 0; i < record.length; i++) {
					String key = record[i];
					switch (key) {
					case ID_CSV_KEY:
						idIndex = i;
						break;
					case DECISION_CSV_KEY:
						decisionIndex = i;
						break;
					default:
						indexToVarKeyMap.put(i, key);
					}
				}
				
				//read remaining csv
				while ((record = reader.readNext()) != null) {
					DecisionNumericData data = new DecisionNumericData();
					Map<String, Double> varsMap = new HashMap<String, Double>();
					
					data.setNumericData(varsMap);
					data.setId(Integer.parseInt(record[idIndex]));
					decision = Short.parseShort(record[decisionIndex]);
					data.setDecision(decision);
					
					for (int i = 0; i < record.length; i++) {
						
						if(i != idIndex && i != decisionIndex) {
							varValue = Double.parseDouble(record[i]);
							String varKey = indexToVarKeyMap.get(i);
							
							if(decision == 1) {
								Double minDecision1Var = decision1MinVarValuesMap.get(varKey), maxDecision1Var = decision1MaxVarValuesMap.get(varKey);
								if(minDecision1Var == null || varValue < minDecision1Var) {
									decision1MinVarValuesMap.put(varKey, varValue);
								}
								if(maxDecision1Var == null || varValue > maxDecision1Var) {
									decision1MaxVarValuesMap.put(varKey, varValue);
								}
							}
							
							varsMap.put(varKey, varValue);
						}
					}
					
					results.add(data);
				}
			} catch (NumberFormatException e) {
				throw new BaseException("Invalid format at line: " + reader.getLinesRead(), e);
			}
		}
		
		removeInvalidData(results, decision1MinVarValuesMap, decision1MaxVarValuesMap);

		return results;
	}

	private void removeInvalidData(List<DecisionNumericData> results, Map<String, Double> decision1MinVarValuesMap, Map<String, Double> decision1MaxVarValuesMap) {
		Iterator<DecisionNumericData> it = results.iterator();
		while (it.hasNext()) {
			DecisionNumericData decisionNumericData = it.next();
			
			if(decisionNumericData.getDecision() == 0) {
				boolean hasValuesInDecision1Interval = false;
				for (Entry<String, Double> varDataEntry : decisionNumericData.getNumericData().entrySet()) {
					String varKey = varDataEntry.getKey();
					Double varValue = varDataEntry.getValue();
					Double decision1MinVarValue = decision1MinVarValuesMap.get(varKey);
					Double decision1MaxVarValue = decision1MaxVarValuesMap.get(varKey);
					
					if(decision1MinVarValue !=null && decision1MaxVarValue != null && varValue >= decision1MinVarValue && varValue <= decision1MaxVarValue) {
						hasValuesInDecision1Interval = true;
						break;
					}
				}
				if(!hasValuesInDecision1Interval) {
					it.remove();
				}
			}
		}
	}

}
