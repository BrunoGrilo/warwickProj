package decisiondata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.warwick.cli1proj.business.DecisionDataBean;
import com.warwick.cli1proj.exceptions.BaseException;
import com.warwick.cli1proj.model.decisionModel.DecisionNumericData;

@RunWith(SpringRunner.class)
public class DecisionCSVNumericDataTest {
	private final String DECISION_DATA_FOLDER_PATH = "src\\test\\resources\\decisionData";
	
	@TestConfiguration
    static class DecisionDataBeanTestContextConfiguration {
  
        @Bean
        public DecisionDataBean decisionDataBean() {
            return new DecisionDataBean();
        }
    }
 
    @Autowired
    private DecisionDataBean decisionDataBean;
 
    @Test
    public void assertSameFileContentProcessed_ExampleA_input_And_ExampleA_output() throws IOException, BaseException {
    	File inputF = new File(DECISION_DATA_FOLDER_PATH + "\\exampleA_input.csv");
    	List<DecisionNumericData> decisionData = decisionDataBean.loadFilteredCsvDecisionData(new FileInputStream(inputF));
    	
    	List<DecisionNumericData> exampleExpectedResult = new ArrayList<>();
    	Map<String, Double> vars = new HashMap<String, Double>();
    	String var1= "Var1";
    	
    	vars.put(var1, 20.0);
    	exampleExpectedResult.add(new DecisionNumericData(2, 1, vars));
    	
    	vars = new HashMap<String, Double>();
    	vars.put(var1, 30.0);
    	exampleExpectedResult.add(new DecisionNumericData(3, 0, vars));
    	
    	vars = new HashMap<String, Double>();
    	vars.put(var1, 40.0);
    	exampleExpectedResult.add(new DecisionNumericData(4, 1, vars));
    	
    	assertThat(exampleExpectedResult).hasSameElementsAs(decisionData);
     }
    
    @Test
    public void assertSameFileContentProcessed_ExampleA_input_And_ExampleB_output() throws IOException, BaseException {
    	File inputF = new File(DECISION_DATA_FOLDER_PATH + "\\exampleC_input.csv");
    	List<DecisionNumericData> decisionData = decisionDataBean.loadFilteredCsvDecisionData(new FileInputStream(inputF));
    	
    	List<DecisionNumericData> exampleExpectedResult = new ArrayList<>();
    	Map<String, Double> vars = new HashMap<String, Double>();
    	String var1= "Var1", var2 = "Var2";
    	
    	vars.put(var1, 20.0);
    	vars.put(var2, 4.0);
    	exampleExpectedResult.add(new DecisionNumericData(2, 1, vars));
    	
    	vars = new HashMap<String, Double>();
    	vars.put(var1, 30.0);
    	vars.put(var2, 1.0);
    	exampleExpectedResult.add(new DecisionNumericData(3, 0, vars));
    	
    	vars = new HashMap<String, Double>();
    	vars.put(var1, 40.0);
    	vars.put(var2, 7.0);
    	exampleExpectedResult.add(new DecisionNumericData(4, 1, vars));
    	
    	vars = new HashMap<String, Double>();
    	vars.put(var1, 50.0);
    	vars.put(var2, 5.0);
    	exampleExpectedResult.add(new DecisionNumericData(5, 0, vars));
    	
    	vars = new HashMap<String, Double>();
    	vars.put(var1, 20.0);
    	vars.put(var2, 11.0);
    	exampleExpectedResult.add(new DecisionNumericData(6, 0, vars));
    	
    	vars = new HashMap<String, Double>();
    	vars.put(var1, 25.0);
    	vars.put(var2, 6.0);
    	exampleExpectedResult.add(new DecisionNumericData(7, 0, vars));
    	
    	assertThat(exampleExpectedResult).hasSameElementsAs(decisionData);
     }

}
