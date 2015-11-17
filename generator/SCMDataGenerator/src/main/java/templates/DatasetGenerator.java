package templates;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

	Map<Integer, List<String>> products;
	Map<Integer, List<String>> companies;
	Map<String, String> argMap;

	Random randomGenerator;

	public DatasetGenerator() {
	}

	public void processParameters(String[] args) {
		argMap = new HashMap<String, String>();

		// settings default configuration
		argMap.put("-depth", "3");
		argMap.put("-industry", "automotive");
		argMap.put("-partners", "2");
		argMap.put("-size", "100000");

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

	public void setProductList(Industry industry) {
		products = new HashMap<Integer, List<String>>();

		switch (industry) {
		case Agriculture:
			products.put(0, Arrays.asList("Grain", "Barley", "Rye", "Corn",
					"Cane", "Potato"));
			products.put(1, Arrays.asList("Flour", "Barley four", "Rye flour",
					"Glucose Syrup", "Sugar"));
			products.put(2, Arrays.asList("Bread", "Beer", "Cornflakes",
					"Chips", "Baby Food"));
			products.put(3, Arrays.asList("North Sea Bread", "South Pole Beer",
					"Elbe Cornflakes", "RedLand Chips XL",
					"Primona's baby food"));
			products.put(4, Arrays.asList("North Sea Bread", "South Pole Beer",
					"Elbe Cornflakes", "RedLand Chips XL",
					"Primona's baby food"));
			products.put(5, Arrays.asList("North Sea Bread", "South Pole Beer",
					"Elbe Cornflakes", "RedLand Chips XL",
					"Primona's baby food"));
			break;
		case HealthCare:

			break;
		case Computer:

			break;
		case Automotive:

			break;

		default:
			break;
		}

	}

	public void setCompanies(Industry industry) {
		companies = new HashMap<Integer, List<String>>();

		switch (industry) {
		case Agriculture:
			companies.put(0, Arrays.asList("Farm Laketown East",
					"Farm Frankia", "Farm Al", "Farm Mountainrange",
					"Farm Hurus", "Farm Zumbialk"));
			companies.put(1, Arrays.asList("Land Trade Corp AP",
					"Land Trade Corp XIX", "Land Trade Corp UZA",
					"Land Trad Corp FFJ", "Glucose Syrup", "Sugar"));
			companies.put(2, Arrays.asList("South Pole Brewery",
					"Hullogs Cornflakes", "Fritz Baby Food",
					"Sangio Sugar Factory", "Zuruf Chips Corp"));
			companies.put(3, Arrays.asList("Matra Global Trade",
					"Zurank East Asia Trade Corp", "Salunga Australian Trade",
					"Hunguf European Trade", "Primona's baby food"));
			companies.put(4, Arrays.asList("Ledl Supermarkets",
					"Kermarkt Corp", "Yusaka Local Food", "Adla Hypermarkets",
					"Cirrifoor Liquid Trade"));
			companies.put(5, Arrays.asList("Adams Kiosk", "Yuris Bar",
					"Aladins Restaurant", "Blue Sea Store", "Zarkant Store"));
			break;
		case HealthCare:

			break;
		case Computer:

			break;
		case Automotive:

			break;

		default:
			break;
		}

	}

	public String getRandomProduct(int supplyChainLevel) {
		randomGenerator = new Random();
		int index = randomGenerator.nextInt(products.size() - 1);

		return products.get(supplyChainLevel).get(index);
	}

	public String getRandomCompany(int supplyChainLevel) {
		randomGenerator = new Random();
		int index = randomGenerator.nextInt(companies.size() - 1);

		return companies.get(supplyChainLevel).get(index);
	}

	public Model generateData() throws FileNotFoundException {

		this.setProductList(Industry.Agriculture);
		this.setCompanies(Industry.Agriculture);

		System.out.println(this.getRandomProduct(3));
		System.out.println(this.getRandomProduct(5));

		int size = Integer.parseInt(argMap.get("-size"));
		int depth = Integer.parseInt(argMap.get("-depth"));
		int width = Integer.parseInt(argMap.get("-partners"));

		System.out.println(depth + " " + width + " " + size);

		int numberOfMetricsCreated = 20;

		int edgeSize = size / ((int) depth * width);

		int realEdgeSize = edgeSize / numberOfMetricsCreated;

		System.out.println("edgeSize: " + edgeSize);

		// ------------------------------------------

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

		// initialize central node
		processIdString = UUID.randomUUID().toString();
		process = model.createResource("process_" + processIdString);

		// create 2x dimensional supply chain, example (depth 3, width 3):
		// (a) -> (c) -> (x)
		// (b) -> (c) -> (y)
		// (b) -> (d) -> (z)
		// number of edges depends on size

		String currentCompany = "";
		List<String> companyLevel = this.companies.get(0); // start with first
															// supplyChain List

		System.out.println("new edgeSize: " + edgeSize);

		for (int i = 0; i < width; i++) {

			currentCompany = companyLevel.get(i);

			for (int j = 0; j < depth; j++) {

				for (int k = 0; k < realEdgeSize; k++) {
					processIdString = UUID.randomUUID().toString();
					process = model
							.createResource("process_" + processIdString);

					// generate additional information for context
					process.addProperty(processId, processIdString);
					process.addProperty(hasCommitDate, "2015-11-05");
					process.addProperty(isSubjectOf, this.getRandomProduct(1));
					process.addProperty(hasSupplier, this.getRandomCompany(2));

					// generate SCOR information
					process.addProperty(RDF.type, SCOR.DeliverStockedProduct);

					process.addProperty(SCOR.hasMetricRL_33,
							this.getRandomValue());
					// process.addProperty(SCOR.hasMetricRL_35,
					// ig.getRandomValue());
					// process.addProperty(SCOR.hasMetricRL_32,
					// ig.getRandomValue());
					// process.addProperty(SCOR.hasMetricRL_34,
					// ig.getRandomValue());
					// process.addProperty(SCOR.hasMetricRL_31,
					// ig.getRandomValue());
					// process.addProperty(SCOR.hasMetricRL_43,
					// ig.getRandomValue());
					// process.addProperty(SCOR.hasMetricRL_45,
					// ig.getRandomValue());
					process.addProperty(SCOR.hasMetricRL_50,
							this.getRandomValue());
					// process.addProperty(SCOR.hasMetricRL_12,
					// ig.getRandomValue());
					// process.addProperty(SCOR.hasMetricRL_41,
					// ig.getRandomValue());
					// process.addProperty(SCOR.hasMetricRL_42,
					// ig.getRandomValue());

					process.addProperty(SCOR.hasMetricAG_1,
							this.getRandomValue(10, 20));
					process.addProperty(SCOR.hasMetricAG_2,
							this.getRandomValue(10, 20));
					process.addProperty(SCOR.hasMetricAG_3,
							this.getRandomValue(10, 20));
					process.addProperty(SCOR.hasMetricAG_4,
							this.getRandomValue(10, 20));

					process.addProperty(SCOR.hasMetricCO_14,
							this.getRandomValue(10, 20));
					process.addProperty(SCOR.hasMetricCO_15,
							this.getRandomValue(10, 20));
					process.addProperty(SCOR.hasMetricCO_16,
							this.getRandomValue(10, 20));
					process.addProperty(SCOR.hasMetricCO_17,
							this.getRandomValue(10, 20));

					process.addProperty(SCOR.hasMetricAM_2,
							this.getRandomValue(10, 20));
					process.addProperty(SCOR.hasMetricAM_3,
							this.getRandomValue(10, 20));

					process.addProperty(SCOR.hasMetricRS_21,
							this.getRandomValue(10, 20));
					process.addProperty(SCOR.hasMetricRS_22,
							this.getRandomValue(10, 20));
					process.addProperty(SCOR.hasMetricRS_24,
							this.getRandomValue(10, 20));

				}
			}
		}

		PrintWriter out = new PrintWriter("dataset.ttl");

		model.write(out, "TURTLE");
		
		return model;
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
