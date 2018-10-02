package edu.csupomona.cs480.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.primitives.UnsignedInteger;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.inference.TestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import edu.csupomona.cs480.App;
import edu.csupomona.cs480.data.GpsProduct;
import edu.csupomona.cs480.data.SsdProduct;
import edu.csupomona.cs480.data.User;
import edu.csupomona.cs480.data.provider.GpsProductManager;
import edu.csupomona.cs480.data.provider.UserManager;


/**
 * This is the controller used by Spring framework.
 * <p>
 * The basic function of this controller is to map
 * each HTTP API Path to the correspondent method.
 *
 */

@RestController
public class WebController {

	/**
	 * When the class instance is annotated with
	 * {@link Autowired}, it will be looking for the actual
	 * instance from the defined beans.
	 * <p>
	 * In our project, all the beans are defined in
	 * the {@link App} class.
	 */
	@Autowired
	private UserManager userManager;
	@Autowired
	private GpsProductManager gpsProductManager;

	/**
	 * This is a simple example of how the HTTP API works.
	 * It returns a String "OK" in the HTTP response.
	 * To try it, run the web application locally,
	 * in your web browser, type the link:
	 * 	http://localhost:8080/cs480/ping
	 */
	@RequestMapping(value = "/cs580/ping", method = RequestMethod.GET)
	String healthCheck() {
		// You can replace this with other string,
		// and run the application locally to check your changes
		// with the URL: http://localhost:8080/
		return "OK-CS480-Demo";
	}

	@RequestMapping(value = "/cs4800/hello", method = RequestMethod.GET)
	String helloWorld(){
		return "world!";
	}
	
	@RequestMapping(value = "/cs4800/name", method = RequestMethod.GET)
	String myName(){
		return "Jaewon Hong";
	}
	
	@RequestMapping(value = "/cs4800/math", method = RequestMethod.GET)
	String testStatisticalTests() throws Exception {
	    double[] x = new double[1000];
	    for (int i = 0; i < x.length; i++) {
	        x[i] = Math.random()*100+1;
	    }
	    double mu = new DescriptiveStatistics(x).getMean();
	    System.out.println("Mean: " + mu);
	    String value = "t-statistic for true mean (50): " + TestUtils.t(50, x) +
	    "  p-value for true mean (50): " + TestUtils.tTest(50,x) + 
	    "  t-statistic for false mean (75): " + TestUtils.t(75,x) +
	    "  p-value for false mean (75): " + TestUtils.tTest(75,x);
	    return value;
	}
	
	@RequestMapping(value = "/cs4800/motto", method = RequestMethod.GET)
	String motto(){
		return "Instrumentum Disciplinae";
	}
	
	@RequestMapping( value = "/cs4800/list", method = RequestMethod.GET )
	List<Integer> intList()
	{
		List<Integer> intList = new ArrayList<Integer>();
		for ( int i = 0; i < 10; i++ )
			intList.add( i );
		
		return intList;
	}
	
	@RequestMapping( value = "/cs4800/guava", method = RequestMethod.GET )
	List<String> unsignedIntegers()
	{
		List<String> list = new ArrayList<String>();
		list.add( "This is a list demonstrating unsigned integers in the Google Guava package" );
		list.add( "Integer.MAX_VALUE == " + Integer.MAX_VALUE );
		list.add( "UnsignedInteger.MAX_VALUE == " + UnsignedInteger.MAX_VALUE );
		list.add( "Integer.MIN_VALUE == " + Integer.MIN_VALUE );
		list.add( "UnsignedInteger.MIN_VALUE == " + UnsignedInteger.ZERO );
		list.add( "Given a 32-bit integer defined as 0xCA97FD36:" );
		list.add( "Integer.valueOf( 0xCA97FD36 ) == " + Integer.valueOf( 0xCA97FD36 ) );
		
		try
		{
			list.add( "UnsignedInteger.valueOf( 0xCA97FD36 ) == " + UnsignedInteger.valueOf( 0xCA97FD36 ) );
		}
		catch ( IllegalArgumentException e )
		{
			list.add( "-----------------------------------------------------------------------------------" );
			list.add( "UnsignedInteger.valueOf( 0xCA97FD36 ) throws an IllegalArgumentException instead.");
			list.add( "This is due to Java lacking true support for unsigned values," );
			list.add( "causing negative 32-bit values to throw errors when cast as unsigned" );
		}
		
		return list;
	}
	
	@RequestMapping( value = "/cs4800/ssd", method = RequestMethod.GET )
	ModelAndView spongeBob() {
		ModelAndView modelAndView = new ModelAndView("ssd");
		try {
			Document doc = Jsoup.connect("https://www.ebay.com/sch/i.html?_from=R40&_trksid=p2380057.m570.l1313.TR12.TRC2.A0.H0.Xssd.TRS0&_nkw=ssd&_sacat=0").get();
			Elements prices = doc.getElementsByClass("s-item__price");
			Elements titles = doc.getElementsByClass("s-item__title");
			Elements links = doc.getElementsByClass("s-item__link");
			List<SsdProduct> ssds = new ArrayList<>();
			String tempTitle = "";
			for (int i = 0; i < prices.size(); i++) {
				SsdProduct prod = new SsdProduct();
				tempTitle = titles.get(i).text();

				if(tempTitle.contains("SPONSORED") || tempTitle.contains("New Listing")) {
					tempTitle = tempTitle.replaceAll("SPONSORED", "");
					tempTitle =  tempTitle.replaceAll("New Listing", "");
				}
				
				prod.setTitle(tempTitle);
				prod.setPrice(prices.get(i).text());
				prod.setLink(links.get(i).attr("href"));
				
				ssds.add(prod);
			}
			
			modelAndView.addObject("ssds", ssds);	
			
			return modelAndView;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return modelAndView;
		}
	}
	/**
	 * This is a simple example of how to use a data manager
	 * to retrieve the data and return it as an HTTP response.
	 * <p>
	 * Note, when it returns from the Spring, it will be
	 * automatically converted to JSON format.
	 * <p>
	 * Try it in your web browser:
	 * 	http://localhost:8080/cs480/user/user101
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.GET)
	User getUser(@PathVariable("userId") String userId) {
		User user = userManager.getUser(userId);
		return user;
	}

	/**
	 * This is an example of sending an HTTP POST request to
	 * update a user's information (or create the user if not
	 * exists before).
	 *
	 * You can test this with a HTTP client by sending
	 *  http://localhost:8080/cs480/user/user101
	 *  	name=John major=CS
	 *
	 * Note, the URL will not work directly in browser, because
	 * it is not a GET request. You need to use a tool such as
	 * curl.
	 *
	 * @param id
	 * @param name
	 * @param major
	 * @return
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.POST)
	User updateUser(
			@PathVariable("userId") String id,
			@RequestParam("name") String name,
			@RequestParam(value = "major", required = false) String major) {
		User user = new User();
		user.setId(id);
		user.setMajor(major);
		user.setName(name);
		userManager.updateUser(user);
		return user;
	}

	/**
	 * This API deletes the user. It uses HTTP DELETE method.
	 *
	 * @param userId
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.DELETE)
	void deleteUser(
			@PathVariable("userId") String userId) {
		userManager.deleteUser(userId);
	}

	/**
	 * This API lists all the users in the current database.
	 *
	 * @return
	 */
	@RequestMapping(value = "/cs480/users/list", method = RequestMethod.GET)
	List<User> listAllUsers() {
		return userManager.listAllUsers();
	}
	
	@RequestMapping(value = "/cs480/gps/list", method = RequestMethod.GET)
	List<GpsProduct> listGpsProducts() {
		return gpsProductManager.listAllGpsProducts();
	}

	/*********** Web UI Test Utility **********/
	/**
	 * This method provide a simple web UI for you to test the different
	 * functionalities used in this web service.
	 */
	@RequestMapping(value = "/cs480/home", method = RequestMethod.GET)
	ModelAndView getUserHomepage() {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("users", listAllUsers());
		return modelAndView;
	}

}
