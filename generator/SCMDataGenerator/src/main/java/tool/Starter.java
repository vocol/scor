package tool;


import java.io.IOException;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

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
		String datasetURL = dg.generateData();

		// execute queries & print results
		Benchmarker bm = new Benchmarker();
		bm.setDatasetURL(datasetURL);
		bm.executeQueries();


	}


}
