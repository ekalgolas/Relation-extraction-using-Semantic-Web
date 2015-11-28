package edu.semweb.rdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * Class to generate SPARQL queries and return results
 *
 * @author Ekal.Golas
 */
public class QueryProcessor {
	public static ResultSet processQuery(final String query) throws IOException {
		final InputStream inputStream = new FileInputStream(new File("data/Lab1_4_EkalGolas.nt"));
		final Model model = ModelFactory.createDefaultModel();
		model.read(inputStream, null);
		inputStream.close();

		final Query jenaQuery = QueryFactory.create(query);
		final QueryExecution queryExecution = QueryExecutionFactory.create(jenaQuery, model);

		final ResultSet resultSet = queryExecution.execSelect();
		queryExecution.close();

		return resultSet;
	}

	public static void printResults(final ResultSet resultSet) {
		while (resultSet.hasNext()) {
			final QuerySolution solution =  resultSet.next();
		}
	}
}