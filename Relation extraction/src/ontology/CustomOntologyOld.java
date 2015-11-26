package ontology;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.impl.ReifiedStatementImpl;

import edu.semweb.nlp.RelationsInDocument;
import edu.semweb.nlp.RelationsInSentence;
import edu.semweb.nlp.Triple;

/**
 * Class that defines a custom ontology
 *
 * @author Ekal.Golas
 */
public class CustomOntologyOld {
	public File getXMLFromOntology(final RelationsInDocument rd)
			throws IOException {
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);

		// Create an empty Model
		final String file = "data/Ontology.owl";
		final OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF);

		// Load Monterey and record time
		final double start = System.currentTimeMillis();
		model.read(new FileInputStream(new File(file)), "RDF/XML");
		final double end = System.currentTimeMillis();

		// Display the time taken
		final double time = (end - start) / 1000.0;
		System.out.println(String.format("Load of %s took %.1f seconds", file, time));
		
		List<Triple> triples = rd.getAllTriples();
		
		for(Triple triple : triples) {
			
		}

		// Write model to different formats
		final FileWriter xmlWriter = new FileWriter("data/Lab1_4_EkalGolas.nt");
		
		model.write(xmlWriter, "N-TRIPLES");

		return null;
	}
}