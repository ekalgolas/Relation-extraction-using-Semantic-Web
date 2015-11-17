package ontology;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;

import edu.semweb.nlp.RelationsInDocument;
import edu.semweb.nlp.RelationsInSentence;

/**
 * Class that defines a custom ontology
 *
 * @author Ekal.Golas
 */
public class CustomOntology {
	public File getXMLFromOntology(final RelationsInDocument rd)
			throws IOException {
		org.apache.log4j.Logger.getRootLogger()
		.setLevel(org.apache.log4j.Level.OFF);

		// Make a TDB-backed dataset
		final String directory = "data/MyDatabases/";
		final Dataset dataset = TDBFactory.createDataset(directory + "Dataset1");

		// create an empty Model
		final Model model = dataset.getDefaultModel();

		// Load Monterey and record time
		final double start = System.currentTimeMillis();
		final String file = "data/Ontology.owl";
		FileManager.get().readModel(model, file);
		final double end = System.currentTimeMillis();

		// Display the time taken
		final double time = (end - start) / 1000.0;
		System.out.println(String.format("Load of %s took %.1f seconds", file, time));

		final Property property = model.getProperty("hasLocation");

		for (final RelationsInSentence relationsInSentence : rd.getRelationsInSentences()) {
			final Map<String, List<String>> map = relationsInSentence.getNerTaggedEntityMap();
			final String sentence = relationsInSentence.getSentence();

			for (final String entry : map.keySet()) {
				final Resource resource = model.createResource("http://www.semanticweb.org/Relation-extraction#" + entry);
				resource.addProperty(property, map.get(entry)
					.get(0));
				System.out.println(sentence + " : " + map.get(entry));
			}
		}

		// Write model to different formats
		final FileWriter xmlWriter = new FileWriter("data/Lab1_4_EkalGolas.xml");

		model.write(xmlWriter, "RDF/XML");

		return null;
	}
}