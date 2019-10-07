package forth.ics.isl.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

/**
*
* @author Vangelis Kritsotakis
* 
* Here are a few Java Jersey Client implementation examples (one for each service)
*/

public class WebServiceClient {

	private static final String REST_URI = "http://localhost:8080/WebServices";
	
	public static void main(String... args) throws IOException {
            
            
                // ##################### Executing SPARQL Import service ####################
//		
		String stringImportResponse = postSparqImport(REST_URI + "/webServices/import", "/home/mhalkiad/Documents/data.rdf", 
				"kb", "http://graph.kb.rdf", "application/rdf+xml","http://139.91.183.72:8091/blazegraph");
		System.out.println("Import Service Response:");
		System.out.println(stringImportResponse);
                
                // ##################### Executing SPARQL Export service ####################
//		
//		String stringExportResponse = getSparqlExportResults(REST_URI + "/webServices/export", "/home/mhalkiad/Documents/test361.rdf", 
//                                  "application/rdf+xml", "kb", "http://graph.kb.rdf", "http://139.91.183.72:8091/blazegraph");
//		System.out.println("Export Service Response:");
//		System.out.println(stringExportResponse);
//		
		// ##################### Executing SPARQL Query service ####################
//		
//		String stringQueryResponse = getSparqlQueryResults(REST_URI + "/webServices/query", "select * where {?s ?p ?o} limit 100", 
//				100, "kb", "application/json");
//		System.out.println("Query Service Response:");
//		System.out.println(stringQueryResponse);

//		// ##################### Executing SPARQL update service ####################
//		
//		String stringQueryResponse = postSparqUpdate(REST_URI + "/webServices/update", "select * where {?s ?p ?o} limit 100", "kb", "http://139.91.183.72:8091/blazegraph");
//		System.out.println("Update Service Response:");
//		System.out.println(stringQueryResponse);
//		
//		// ###################### Executing Transform service ######################
//		
//		// Setting Arguments - Source Input
//		// (2 Files on the client)
//		ArrayList<File> inputFileList = new ArrayList<File>();
//		inputFileList.add(new File("C:\\\\Workspaces\\\\reactTestWorkSpace\\\\Input files backup\\\\Transform Service Example\\\\X3ML example\\\\input-1.xml"));
//		inputFileList.add(new File("C:\\\\Workspaces\\\\reactTestWorkSpace\\\\Input files backup\\\\Transform Service Example\\\\X3ML example\\\\input-2.xml"));
//		// (1 FilePath (String) on the server)
//		ArrayList<String> inputFilePathList = new ArrayList<String>();
//		inputFilePathList.add("C:\\Workspaces\\reactTestWorkSpace\\Input files backup\\Transform Service Example\\X3ML example\\input-3.xml");
//		// (1 URL (String))
//		ArrayList<String> inputFileUrlList = new ArrayList<String>();
//		inputFileUrlList.add("file:///C:/Workspaces/reactTestWorkSpace/Input%20files%20backup/Transform%20Service%20Example/X3ML%20example/input-4.xml");
//		
//		// Setting Arguments - X3ML input
//		// (1 File on the client)
//		ArrayList<File> x3mlFileList = new ArrayList<File>();
//		x3mlFileList.add(new File("C:\\\\Workspaces\\\\reactTestWorkSpace\\\\Input files backup\\\\Transform Service Example\\\\X3ML example\\\\mappings-1.x3ml"));
//		// (1 FilePath (String) on the server)
//		ArrayList<String> x3mlFilePathList = new ArrayList<String>();
//		x3mlFilePathList.add("C:\\Workspaces\\reactTestWorkSpace\\Input files backup\\Transform Service Example\\X3ML example\\mappings-2.x3ml");
//		// (0 URL (String))
//		ArrayList<String> x3mlFileUrlList = new ArrayList<String>();
//		//x3mlFileUrlList.add("file:///C:/Workspaces/reactTestWorkSpace/Input%20files%20backup/Transform%20Service%20Example/X3ML%20example/mappings-2.x3ml");
//		
//		// Setting Arguments - Generator policy Input
//		// (1 File on the client)
//		File generatorPolicyFile = new File("C:\\\\Workspaces\\\\reactTestWorkSpace\\\\Input files backup\\\\Transform Service Example\\\\X3ML example\\\\generator-policy.xml");
//		//String generatorPolicyFilePath = "C:\\Workspaces\\reactTestWorkSpace\\Input files backup\\Transform Service Example\\X3ML example\\generator-policy.xml";
//		// (0 FilePath (String) on the server)
//		String generatorPolicyFilePath = null;
//		// (0 URL (String))
//		String generatorPolicyFileUrl = null;
//		
//		// Calling the transformation Service
//		String stringTransformationResponse = postX3mlToRDFTransformResults(REST_URI + "/transform/x3mltoRdf", 
//				inputFileList, inputFilePathList, inputFileUrlList, 
//				x3mlFileList, x3mlFilePathList, x3mlFileUrlList,
//				generatorPolicyFile, generatorPolicyFilePath, generatorPolicyFileUrl,
//				"rdf-xml");
//		
//		System.out.println("Transformation Service Response:");
//		System.out.println(stringTransformationResponse);
	}
	
	/**
	 * Returns a string representation of the output when calling a service that executes a SPARQL query on the triple store.
	 * 
	 * @param  relativeUrl  		A String representation of the absolute URL of the service.
	 * @param  timeout 				An int representing the timeout in milliseconds.
	 * @param  namespace 			The name of the namespace where the query will be executed
	 * @param  outputContentType	The content type of the output
	 * @return      the image at the specified URL
	 */
    public static String getSparqlQueryResults(String url, String query, int timeout, String namespace, String outputContentType) {
    	Client client = ClientBuilder.newClient();
    	Response response;
    	String strRes;
		try {
			response = client.target(url)
 			  .queryParam("queryString", URLEncoder.encode(query, StandardCharsets.UTF_8.name()).replace("+", "%20"))
 			  .queryParam("timeout", timeout)
 			  .queryParam("namespace", namespace)
 			  .request()
 			  .header("Content-Type", outputContentType)
 			  .get();
			strRes = response.readEntity(String.class);
                         System.out.println("GET: " + response.toString());
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			strRes = "There were error while executing the SPARQL query";
		}
                
       
        client.close();
        return strRes;
    }
    
    /**
	 * Returns a string representation of the output when calling the transformation service to get an RDF (or turtle).
	 * 
	 * @param  inputFileList  			An ArrayList of files on the client to be used as source inputs.
	 * @param  inputFilePathList  		An ArrayList of strings representing the file-paths of the source inputs on the server.
	 * @param  inputFileUrlList  		An ArrayList of strings representing the URLs of the source inputs.
	 * 									(All inputFileList, inputFilePathList, inputFileUrlList can be used simultaneously if desired).
	 * @param  x3mlFileList  			An ArrayList of files on the client to be used as X3ML document inputs.
	 * @param  x3mlFilePathList  		An ArrayList of strings representing the file-paths of the X3ML document inputs on the server.
	 * @param  x3mlFileUrlList  		An ArrayList of strings representing the URLs of the X3ML document inputs.
	 * 									(All x3mlFileList, x3mlFilePathList, x3mlFileUrlList can be used simultaneously if desired).
	 * @param  generatorPolicyFile 		A file on the client to be used as generatorPolicy File.
	 * @param  generatorPolicyFilePath 	A string representing the file-path of the generatorPolicy File on the server.
	 * @param  generatorPolicyFileUrl	A string representing the URL of the generatorPolicy File.
	 * 									(Since only one generator policy file is allowed, there is priority set: 
	 * 									1. generatorPolicyFile, 2. generatorPolicyFilePath, 3. generatorPolicyFileUrl
	 * 									if any of them is null then the service is not executed).
	 * @param  transformContentType		A string representation of the transformation's output format (rdf-xml or turtle)
	 * @return      A JSON object that contains the RDF or turtle content
	 */
    public static String postX3mlToRDFTransformResults(String url, 
    						ArrayList<File> inputFileList, ArrayList<String> inputFilePathList, ArrayList<String> inputFileUrlList, 
    						ArrayList<File> x3mlFileList, ArrayList<String> x3mlFilePathList, ArrayList<String> x3mlFileUrlList, 
    						File generatorPolicyFile, String generatorPolicyFilePath, String generatorPolicyFileUrl, 
				    		String transformContentType) {
    	
    	CloseableHttpClient client = HttpClients.createDefault();
    	HttpResponse response = null;
    	String strRes;
		try {
			
			MultipartEntityBuilder multipartBuilder = MultipartEntityBuilder.create();
			
			// source - file (ArrayList<File>)
			inputFileList.forEach(inputFile -> {
				multipartBuilder.addBinaryBody("inputFileStream", inputFile);
			});
			// source - filePath (ArrayList<String>)
			inputFilePathList.forEach(inputFilePath -> {
				multipartBuilder.addTextBody("inputFilePath", inputFilePath);
			});
			// source - URL (ArrayList<String>)
			inputFileUrlList.forEach(inputFileUrl -> {
				multipartBuilder.addTextBody("inputFileUrl", inputFileUrl);
			});
			
			// X3ML - file (ArrayList<File>)
			x3mlFileList.forEach(x3mlFile -> {
				multipartBuilder.addBinaryBody("x3mlFileStream", x3mlFile);
			});
			// X3ML - filePath (ArrayList<String>)
			x3mlFilePathList.forEach(x3mlFilePath -> {
				multipartBuilder.addTextBody("x3mlFilePath", x3mlFilePath);
			}); 
			// X3ML - URL (ArrayList<String>)
			x3mlFileUrlList.forEach(x3mlFileUrl -> {
				multipartBuilder.addTextBody("x3mlFileUrl", x3mlFileUrl);
			});
			
 			// Generator Policy - file (File)
			if(generatorPolicyFile != null) {
				multipartBuilder.addBinaryBody("generatorPolicyFileStream", generatorPolicyFile);
			}
			// Generator Policy - filePath (String)
			else if(generatorPolicyFilePath != null) {
				multipartBuilder.addTextBody("generatorPolicyFilePath", generatorPolicyFilePath);
			}
			// Generator Policy - URL (String)
			else if(generatorPolicyFileUrl != null) {
				multipartBuilder.addTextBody("generatorPolicyFileUrl", generatorPolicyFileUrl);
			}
			
			// transformContentType - Form-Data
			multipartBuilder.addTextBody("transformContentType", "rdf-xml");
		    
			HttpPost post = new HttpPost(url);
			HttpEntity entity = multipartBuilder.build();
			post.setEntity(entity);
			response = client.execute(post);
			
			strRes = EntityUtils.toString(response.getEntity(), "UTF-8");
		} 
		catch (Exception e) {
			e.printStackTrace();
			strRes = "There were error while executing the Transformation Service";
		}
		finally {
		     HttpClientUtils.closeQuietly(response);
		 }
        return strRes;
    }
    
    
    /** Imports data to Blazegraph triple store
	 * 
	 * lostSparqlImportToBlazegraph(String url, String filename, String namespace, String graph, String format) {
    	
    */
        public static String postSparqImport(String serviceUrl, String filename, 
                                             String namespace, String graph, String format,
                                             String dataUrl) throws IOException {
           
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(serviceUrl);
            
            post.setHeader("Content-Type", format);
            
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	    urlParameters.add(new BasicNameValuePair("file", filename));
	    urlParameters.add(new BasicNameValuePair("namespace", namespace));
	    urlParameters.add(new BasicNameValuePair("graph", graph));
            urlParameters.add(new BasicNameValuePair("service-url", dataUrl));

	    post.setEntity(new UrlEncodedFormEntity(urlParameters));

	    HttpResponse response = client.execute(post);
	    System.out.println("\nSending 'POST' request to URL : " + serviceUrl);
	    System.out.println("Post parameters : " + post.getEntity());
	    System.out.println("Response Code : " + 
                                    response.getStatusLine().getStatusCode());

            return "OK";
        }
        
    
        /** Update the existing data into Blazegraph triple store
	 * 
	 *
    	*/
        
        public static String postSparqUpdate(String serviceUrl, String updateString, 
                                             String namespace, String dataUrl) throws IOException {
           
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(serviceUrl);
           
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	    urlParameters.add(new BasicNameValuePair("update", updateString));
	    urlParameters.add(new BasicNameValuePair("namespace", namespace));
            urlParameters.add(new BasicNameValuePair("service-url", dataUrl));

	    post.setEntity(new UrlEncodedFormEntity(urlParameters));

	    HttpResponse response = client.execute(post);
	    System.out.println("\nSending 'POST' request to URL : " + serviceUrl);
	    System.out.println("Post parameters : " + post.getEntity());
	    System.out.println("Response Code : " + 
                                    response.getStatusLine().getStatusCode());

            return "OK";
        }
        
        
    
    /**
	 * Exports data of a particular namespace and graph from triple store into a specific format 
	 * 
	 * @param  serviceUrl  		A String representation of the absolute URL of the service.
	 * @param  filename             A String representation of the path of the file that will be stored
         * @param  namespace 		The name of the namespace where the export will be executed
	 * @param  graph		The name of the graph where the export will be executed
	 * @param  format               The content type of the output
	 * @return                     the image at the specified URL
	 */
    public static String getSparqlExportResults(String url, String filename, String format, String namespace, String graph, String triplestoreUrl) {
    	
        Client client = ClientBuilder.newClient();
    	Response response;
    	String strRes;

	response = client.target(url)
                    .queryParam("service-url", triplestoreUrl)
 		    .queryParam("filename", filename)
 		    .queryParam("graph", graph)
 	            .queryParam("namespace", namespace)
 		    .request()
 		    .header("Accept", format)
 		    .get();
	
        strRes = response.readEntity(String.class);
        System.out.println("response: " + response.toString());
        client.close();
        return strRes;
    }
    
}
