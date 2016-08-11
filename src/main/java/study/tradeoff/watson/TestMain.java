package study.tradeoff.watson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.TradeoffAnalytics;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Dilemma;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Option;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Problem;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.CategoricalColumn;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.Column;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.Column.Goal;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.DateColumn;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.NumericColumn;

public class TestMain {

	private static String username = "1fc066347-43ae-427f-818e-f5578714d003";
	private static String password = "XDeZYl4YPBxh";

	public static void main(String[] args) {

		TradeoffAnalytics service = new TradeoffAnalytics();
		service.setUsernameAndPassword(username, password);
		
		// Define the objectives.
		String price = "price";
		String weight = "weight";
		String brand = "brand";
		String rDate = "rDate";

		List<String> categories = new ArrayList<String>();
		categories.add("Apple");
		categories.add("HTC");
		categories.add("Samsung");

		List<String> preferences = new ArrayList<String>();
		preferences.add("Samsung");
		preferences.add("Apple");
		preferences.add("HTC");

		NumericColumn priceColumn = new NumericColumn();
		priceColumn.withKey(price);
		priceColumn.withGoal(Goal.MIN);
		priceColumn.withObjective(true);
		priceColumn.withFullName("Price");
		priceColumn.withRange(0, 400);
		priceColumn.setFormat("number:2");

		NumericColumn weightColumn = new NumericColumn();
		weightColumn.withKey(weight);
		weightColumn.withGoal(Goal.MIN);
		weightColumn.withObjective(true);
		weightColumn.withFullName("Weight");
		weightColumn.setFormat("number:0");

		CategoricalColumn brandColumn = new CategoricalColumn();
		brandColumn.withKey(brand);
		brandColumn.withGoal(Goal.MIN);
		brandColumn.withObjective(true);
		brandColumn.withFullName("Brand");
		brandColumn.setRange(categories);
		brandColumn.setPreference(preferences);

		DateColumn rDateColumn = new DateColumn();
		rDateColumn.withKey(rDate);
		rDateColumn.withGoal(Goal.MAX);
		rDateColumn.withFullName("Release Date");
		rDateColumn.setFormat("date: 'MMM dd, yyyy'");

		List<Column> columns = new ArrayList<Column>();
		columns.add(priceColumn);
		columns.add(weightColumn);
		columns.add(brandColumn);
		columns.add(rDateColumn);

		Problem problem = new Problem("phones");
		problem.setColumns(columns);

		// Define the options.
		List<Option> options = new ArrayList<Option>();
		problem.setOptions(options);

		HashMap<String, Object> galaxySpecs = new HashMap<String, Object>();
		galaxySpecs.put(price, 249);
		galaxySpecs.put(weight, 130);
		galaxySpecs.put(brand, "Samsung");
		galaxySpecs.put(rDate, "2013-04-29T00:00:00Z");
		options.add(new Option("1", "Samsung Galaxy S4").withValues(galaxySpecs));

		HashMap<String, Object> iphoneSpecs = new HashMap<String, Object>();
		iphoneSpecs.put(price, 449);
		iphoneSpecs.put(weight, 112);
		iphoneSpecs.put(brand, "Apple");
		iphoneSpecs.put(rDate, "2012-09-21T00:00:00Z");
		options.add(new Option("2", "Apple iPhone 5").withValues(iphoneSpecs));

		HashMap<String, Object> oneSpecs = new HashMap<String, Object>();
		oneSpecs.put(price, 299);
		oneSpecs.put(weight, 143);
		oneSpecs.put(brand, "HTC");
		oneSpecs.put(rDate, "2013-03-01T00:00:00Z");
		options.add(new Option("3", "HTC One").withValues(oneSpecs));

		// Call the service and get the problem resolution.
		Dilemma dilemma = service.dilemmas(problem, false);
		System.out.println(dilemma);

	}

}
