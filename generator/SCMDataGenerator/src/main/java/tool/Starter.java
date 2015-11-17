package tool;


import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.hp.hpl.jena.rdf.model.Model;

import templates.Benchmarker;
import templates.DatasetGenerator;

public class Starter {

	final static Logger logger = Logger.getLogger(Starter.class);

	public static void main(String[] args) throws IOException {

		// set Log4j
		BasicConfigurator.configure();

		// process parameters
		DatasetGenerator dg = new DatasetGenerator();
		dg.processParameters(args);
		Model model = dg.generateData();

		// execute queries & print results
		Benchmarker bm = new Benchmarker();
		bm.executeQueries(model);


	}


}
