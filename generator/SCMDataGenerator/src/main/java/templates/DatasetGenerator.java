package templates;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import tool.Starter;
import vocabulary.SCOR;

public class DatasetGenerator {

	final static Logger logger = Logger.getLogger(Starter.class);

	public DatasetGenerator() {
	}

	public void processParameters(String[] args) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("-depth", "");
		argMap.put("-industry", "");
		argMap.put("-partners", "");
		argMap.put("-size", "");

		String key;
		String value;

		for (int i = 0; i < args.length; i++) {
			key = args[i].substring(0, args[i].indexOf("="));
			value = args[i].substring(args[i].indexOf("=") + 1);

			if (argMap.containsKey(key)) {
				argMap.put(key, value);
				logger.info("Parameter set:" + key + "=" + value);
			} else {
				logger.info("Invalid parameter found: " + key);
			}
		}

	}

	public String getRandomValue() {
		Random r = new Random();
		int Low = 99;
		int High = 101;
		int R = r.nextInt(High - Low) + Low;

		return String.valueOf(R);
	}

	public String getRandomValue(int min, int max) {
		Random r = new Random();
		int R = r.nextInt(max - min) + min;

		return String.valueOf(R);
	}

	// public TreeNode<String> getSuppliers() {
	//
	// TreeNode<String> root = new TreeNode<String>("root");
	// {
	// TreeNode<String> node0 = root.addChild("node0");
	// TreeNode<String> node1 = root.addChild("node1");
	// TreeNode<String> node2 = root.addChild("node2");
	// {
	// TreeNode<String> node20 = node2.addChild(null);
	// TreeNode<String> node21 = node2.addChild("node21");
	// {
	// TreeNode<String> node210 = node20.addChild("node210");
	// }
	// }
	// }
	//
	// return root;
	//
	// }
//	public TreeNode<String> getClients() {
//
//		return null;
//
//	}

	public String generateData() throws FileNotFoundException {
		Model model = ModelFactory.createDefaultModel();

		String scorNS = "http://purl.org/eis/vocab/scor#";
		String skosNS = "http://www.w3.org/2004/02/skos/core#";
		String exNS = "http://example.org/";

		Resource process;
		Property processId = model.createProperty(exNS + "processId");
		Property hasCommitDate = model.createProperty(exNS + "hasCommitDate");
		Property isSubjectOf = model.createProperty(exNS + "isSubjectOf");
		Property hasSupplier = model.createProperty(exNS + "hasSupplier");
		Property hasCustomer = model.createProperty(exNS + "hasCustomer");
		
		String processIdString;

		for (int i = 0; i < 20000; i++) {
			
			processIdString = UUID.randomUUID().toString();
			process = model.createResource("process_" + processIdString);
			
			// generate additional information for context
			process.addProperty(processId, processIdString);
			process.addProperty(hasCommitDate, "2015-11-05");
			process.addProperty(isSubjectOf, "SmartCard II2");
			process.addProperty(hasSupplier, "Siemens");

			
			// generate SCOR information
			process.addProperty(RDF.type, SCOR.DeliverStockedProduct);

			process.addProperty(SCOR.hasMetricRL_33, this.getRandomValue());
			// process.addProperty(SCOR.hasMetricRL_35, ig.getRandomValue());
			// process.addProperty(SCOR.hasMetricRL_32, ig.getRandomValue());
			// process.addProperty(SCOR.hasMetricRL_34, ig.getRandomValue());
			// process.addProperty(SCOR.hasMetricRL_31, ig.getRandomValue());
			// process.addProperty(SCOR.hasMetricRL_43, ig.getRandomValue());
			// process.addProperty(SCOR.hasMetricRL_45, ig.getRandomValue());
			process.addProperty(SCOR.hasMetricRL_50, this.getRandomValue());
			// process.addProperty(SCOR.hasMetricRL_12, ig.getRandomValue());
			// process.addProperty(SCOR.hasMetricRL_41, ig.getRandomValue());
			// process.addProperty(SCOR.hasMetricRL_42, ig.getRandomValue());

			process.addProperty(SCOR.hasMetricAG_1, this.getRandomValue(10, 20));
			process.addProperty(SCOR.hasMetricAG_2, this.getRandomValue(10, 20));
			process.addProperty(SCOR.hasMetricAG_3, this.getRandomValue(10, 20));
			process.addProperty(SCOR.hasMetricAG_4, this.getRandomValue(10, 20));


			process.addProperty(SCOR.hasMetricCO_14,
					this.getRandomValue(10, 20));
			process.addProperty(SCOR.hasMetricCO_15,
					this.getRandomValue(10, 20));
			process.addProperty(SCOR.hasMetricCO_16,
					this.getRandomValue(10, 20));
			process.addProperty(SCOR.hasMetricCO_17,
					this.getRandomValue(10, 20));

			process.addProperty(SCOR.hasMetricAM_2, this.getRandomValue(10, 20));
			process.addProperty(SCOR.hasMetricAM_3, this.getRandomValue(10, 20));

			process.addProperty(SCOR.hasMetricRS_21,
					this.getRandomValue(10, 20));
			process.addProperty(SCOR.hasMetricRS_22,
					this.getRandomValue(10, 20));
			process.addProperty(SCOR.hasMetricRS_24,
					this.getRandomValue(10, 20));
		}
		
		
		PrintWriter out = new PrintWriter("dataset.ttl");
		
		model.write(out, "TURTLE");

		model.close();

		System.out.println("data created");

		return "URL";
	}
}

// :process_id_1 :hasProduct "SmartCard II2" ;
// :hasCommitDate "01-01-2015" ;
// :hasSupplier :Infineon ;
// :hasCustomer :Siemens .

// #--- SCOR Information
// :process_id_1 a :DeliverStockedProduct ;
// :hasMetricRL_33 90 ; # Deliver Item Accuracy
// :hasMetricRL_35 100 ; # Delivery Quantity Accuracy
// :hasMetricRL_32 75 ; # Customer CommitDateAchievement Time Customer
// Receiving
// :hasMetricRL_34 82 ; # Delivery Location Accuracy
// :hasMetricRL_31 92 ; # Compliance Documentation Accuracy
// :hasMetricRL_43 98 ; # Other Required Documentaiton Accuracy
// :hasMetricRL_45 100 ; # Payment Documentation Accuracy
// :hasMetricRL_50 100 ; # Shipping Documentation Accuracy
// :hasMetricRL_12 100 ; # % of Faultless Installations
// :hasMetricRL_41 100 ; # Orders Delivered Damage Free Conformance
// :hasMetricRL_42 94 . # Orders Delivered Defect Free Confromanace
//
