package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
	/**
	 * Search form
	 */
	private final static Form<SearchForm>	searchForm	= new Form<SearchForm>(SearchForm.class);

	/**
	 * Search form data
	 */
	private static Form<SearchForm>			form;

	public Result index() {
		return ok(views.html.index.render("Your new application is ready."));
	}

	public Result organizations() {
		final String query = "select distinct ?personName ?name ?sentence ?url where { " +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#subject> ?entity .\n" +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate> \"http://www.semanticweb.org/Relation-extraction/hasOrganization\" .\n" +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#object> ?relation .\n" +
				"?statement <http://www.semanticweb.org/Relation-extraction/sourceSentence> ?sentence .\n" +
				"?statement <http://www.semanticweb.org/Relation-extraction/sourceUrl> ?url .\n" +
				"?entity <http://www.semanticweb.org/Relation-extraction/hasName> ?personName .\n" +
				"FILTER regex(?personName, \"" + form.get().query + "\", \"i\") .\n" +
				"?relation <http://www.semanticweb.org/Relation-extraction/hasName> ?name}";

		HashMap<String, List<ResultInfo>> resultInfos = null;
		try {
			resultInfos = printResults(query);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return ok(views.html.results.render("Related Organizations to " + form.get().query, resultInfos));
	}

	public Result locations() {
		final String query = "select distinct ?personName ?name ?sentence ?url where { " +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#subject> ?entity .\n" +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate> \"http://www.semanticweb.org/Relation-extraction/hasLocation\" .\n" +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#object> ?relation .\n" +
				"?statement <http://www.semanticweb.org/Relation-extraction/sourceSentence> ?sentence .\n" +
				"?statement <http://www.semanticweb.org/Relation-extraction/sourceUrl> ?url .\n" +
				"?entity <http://www.semanticweb.org/Relation-extraction/hasName> ?personName .\n" +
				"FILTER regex(?personName, \"" + form.get().query + "\", \"i\") .\n" +
				"?relation <http://www.semanticweb.org/Relation-extraction/hasName> ?name}";

		HashMap<String, List<ResultInfo>> resultInfos = null;
		try {
			resultInfos = printResults(query);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return ok(views.html.results.render("Related Locations to " + form.get().query, resultInfos));
	}

	public Result dates() {
		final String query = "select distinct ?personName ?name ?sentence ?url where { " +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#subject> ?entity .\n" +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate> \"http://www.semanticweb.org/Relation-extraction/hasDate\" .\n" +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#object> ?relation .\n" +
				"?statement <http://www.semanticweb.org/Relation-extraction/sourceSentence> ?sentence .\n" +
				"?statement <http://www.semanticweb.org/Relation-extraction/sourceUrl> ?url .\n" +
				"?entity <http://www.semanticweb.org/Relation-extraction/hasName> ?personName .\n" +
				"FILTER regex(?personName, \"" + form.get().query + "\", \"i\") .\n" +
				"?relation <http://www.semanticweb.org/Relation-extraction/hasName> ?name}";

		HashMap<String, List<ResultInfo>> resultInfos = null;
		try {
			resultInfos = printResults(query);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return ok(views.html.results.render("Related Dates to " + form.get().query, resultInfos));
	}

	public Result values() {
		final String query = "select distinct ?personName ?name ?sentence ?url where { " +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#subject> ?entity .\n" +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate> \"http://www.semanticweb.org/Relation-extraction/hasMoney\" .\n" +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#object> ?relation .\n" +
				"?statement <http://www.semanticweb.org/Relation-extraction/sourceSentence> ?sentence .\n" +
				"?statement <http://www.semanticweb.org/Relation-extraction/sourceUrl> ?url .\n" +
				"?entity <http://www.semanticweb.org/Relation-extraction/hasName> ?personName .\n" +
				"FILTER regex(?personName, \"" + form.get().query + "\", \"i\") .\n" +
				"?relation <http://www.semanticweb.org/Relation-extraction/hasName> ?name}";

		HashMap<String, List<ResultInfo>> resultInfos = null;
		try {
			resultInfos = printResults(query);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return ok(views.html.results.render("Related Values to " + form.get().query, resultInfos));
	}

	public Result people() {
		form = searchForm.bindFromRequest();
		final String query = "select distinct ?personName ?name ?sentence ?url where { " +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#subject> ?entity .\n" +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate> \"http://www.semanticweb.org/Relation-extraction/hasPerson\" .\n" +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#object> ?relation .\n" +
				"?statement <http://www.semanticweb.org/Relation-extraction/sourceSentence> ?sentence .\n" +
				"?statement <http://www.semanticweb.org/Relation-extraction/sourceUrl> ?url .\n" +
				"?entity <http://www.semanticweb.org/Relation-extraction/hasName> ?personName .\n" +
				"FILTER regex(?personName, \"" + form.get().query + "\", \"i\") .\n" +
				"?relation <http://www.semanticweb.org/Relation-extraction/hasName> ?name}";

		HashMap<String, List<ResultInfo>> resultInfos = null;
		try {
			resultInfos = printResults(query);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return ok(views.html.results.render("Related People to " + form.get().query, resultInfos));
	}

	public Result peopleGet() {
		final String query = "select distinct ?personName ?name ?sentence ?url where { " +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#subject> ?entity .\n" +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate> \"http://www.semanticweb.org/Relation-extraction/hasPerson\" .\n" +
				"?statement <http://www.w3.org/1999/02/22-rdf-syntax-ns#object> ?relation .\n" +
				"?statement <http://www.semanticweb.org/Relation-extraction/sourceSentence> ?sentence .\n" +
				"?statement <http://www.semanticweb.org/Relation-extraction/sourceUrl> ?url .\n" +
				"?entity <http://www.semanticweb.org/Relation-extraction/hasName> ?personName .\n" +
				"FILTER regex(?personName, \"" + form.get().query + "\", \"i\") .\n" +
				"?relation <http://www.semanticweb.org/Relation-extraction/hasName> ?name}";

		HashMap<String, List<ResultInfo>> resultInfos = null;
		try {
			resultInfos = printResults(query);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return ok(views.html.results.render("Related People to " + form.get().query, resultInfos));
	}

	public static class SearchForm {
		public String	query;
	}

	public static class ResultInfo {
		public String	url;
		public String	sentence;
		public String	name;
		public String	personName;
	}

	public HashMap<String, List<ResultInfo>> printResults(final String query) throws IOException {
		final HashMap<String, List<ResultInfo>> resultInfos = new HashMap<>();

		final InputStream inputStream = new FileInputStream(new File("data\\Results.xml"));
		final Model model = ModelFactory.createDefaultModel();
		RDFDataMgr.read(model, inputStream, Lang.RDFXML);
		inputStream.close();

		final Query jenaQuery = QueryFactory.create(query);
		final QueryExecution queryExecution = QueryExecutionFactory.create(jenaQuery, model);

		final ResultSet resultSet = queryExecution.execSelect();
		while (resultSet.hasNext()) {
			final QuerySolution solution = resultSet.next();

			final ResultInfo resultInfo = new ResultInfo();
			resultInfo.name = solution.get("name").asLiteral().getLexicalForm();
			resultInfo.sentence = solution.get("sentence").asLiteral().getLexicalForm();
			resultInfo.url = solution.get("url").asLiteral().getLexicalForm();
			resultInfo.personName = solution.get("personName").asLiteral().getLexicalForm();

			if (!resultInfos.containsKey(resultInfo.name)) {
				resultInfos.put(resultInfo.name + " : (Related to " + resultInfo.personName + ")", new ArrayList<Application.ResultInfo>());
			}

			resultInfos.get(resultInfo.name + " : (Related to " + resultInfo.personName + ")").add(resultInfo);
		}

		return resultInfos;
	}
}