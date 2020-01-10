package forth.ics.isl.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import forth.ics.isl.blazegraph.*;
import forth.ics.isl.utils.PropertiesManager;
import forth.ics.isl.utils.ResponseStatus;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author mhalkiad
 */

@Path("/triplestoreWebServices")
public class TriplestoreWebServices {
    
    private PropertiesManager propertiesManager = PropertiesManager.getPropertiesManager();
    
    @GET
    @Path("/query")
    public Response query(@QueryParam("queryString") String queryString,
                          @QueryParam("service-url") String serviceURL,
                          @HeaderParam("Content-Type") String contentType,
                          @QueryParam("namespace") String namespace,
                          @QueryParam("token") String token,
                          //@QueryParam("username") String username,
                          //@QueryParam("password") String password,
                          @DefaultValue("0") @QueryParam("timeout") int timeout) throws IOException, ParseException {
        
        //authorization code
        String command = "curl ite_client:user@services.apollonis-infrastructure.gr:8081/oauth/check_token -d token=" + token;
        //String command = "curl ite_client:user@apollonisvm.imsi.athenarc.gr:8081/oauth/token -dgrant_type=password -dusername=" + username + " -dpassword=" + password;
        Process process = Runtime.getRuntime().exec(command);
        InputStream tokenStream = process.getInputStream();
        
        BufferedReader input = new BufferedReader(new InputStreamReader(tokenStream), 1);
        String inputLine;
        
        while((inputLine = input.readLine()) != null) {
          
            if(!inputLine.contains("invalid_token") && !inputLine.contains("error") && inputLine.contains("client_id") && inputLine.contains("user_name")) {
            //if(inputLine.contains("access_token") && !inputLine.contains("error")) {
                
                BlazegraphManager manager = new BlazegraphManager();

                if(serviceURL == null)
                    serviceURL = propertiesManager.getTripleStoreUrl();

                if(namespace == null)
                    namespace = propertiesManager.getTripleStoreNamespace();

                manager.openConnectionToBlazegraph(serviceURL + "/namespace/" + namespace + "/sparql");

                ResponseStatus responseStatus = manager.query(queryString, contentType, timeout);

                manager.closeConnectionToBlazeGraph();

                // Adding Access-Control-Allow-Origin to the header in order to resolve the CORS issue between modern browsers and server
                return Response.status(responseStatus.getStatus()).entity(responseStatus.getResponse()).header("Access-Control-Allow-Origin", "*").build();
            }
               
        }
        return Response.status(404).entity("Authorization failed").header("Access-Control-Allow-Origin", "*").build();
    }
    

    
    @POST
    @Path("/import")
    public Response importToBlazegraph(@QueryParam ("file") String file,
                                       @QueryParam("service-url") String serviceURL,
                                       @HeaderParam("Content-Type") String contentType,
                                       @QueryParam("namespace") String namespace,
                                       @QueryParam("token") String token,
                                       //@QueryParam("username") String username,
                                       //@QueryParam("password") String password,
                                       @DefaultValue("") @QueryParam("graph") String graph) throws IOException {

        //authorization code
        String command = "curl ite_client:user@services.apollonis-infrastructure.gr:8081/oauth/check_token -d token=" + token;
        //String command = "curl ite_client:user@apollonisvm.imsi.athenarc.gr:8081/oauth/token -dgrant_type=password -dusername=" + username + " -dpassword=" + password;
        Process process = Runtime.getRuntime().exec(command);
        InputStream tokenStream = process.getInputStream();
        
        BufferedReader input = new BufferedReader(new InputStreamReader(tokenStream), 1);
        String inputLine;
        
        while((inputLine = input.readLine()) != null) {
           
            if(!inputLine.contains("invalid_token") && !inputLine.contains("error") && inputLine.contains("client_id") && inputLine.contains("user_name")) {
            //if(inputLine.contains("access_token") && !inputLine.contains("error")) {
  
                BlazegraphManager manager = new BlazegraphManager();

                if(serviceURL == null)
                    serviceURL = propertiesManager.getTripleStoreUrl();

                if(namespace == null)
                    namespace = propertiesManager.getTripleStoreNamespace();

                manager.openConnectionToBlazegraph(serviceURL + "/namespace/" + namespace + "/sparql");

                RDFFormat format = Rio.getParserFormatForMIMEType(contentType).get();

                ResponseStatus responseStatus = manager.importFile(file, format, graph);

                manager.closeConnectionToBlazeGraph();

                return Response.status(responseStatus.getStatus()).entity(responseStatus.getResponse()).header("Access-Control-Allow-Origin", "*").build();
       // return Response.status(responseStatus.getStatus()).entity(responseStatus.getResponse()).build();
            }
        }
        return Response.status(404).entity("Authorization failed").header("Access-Control-Allow-Origin", "*").build();
    }    
    
    
    @POST
    @Path("/update")
    public Response update(@QueryParam("update") String updateMsg,
                           @QueryParam("namespace") String namespace,
                           @QueryParam("service-url") String serviceURL,
                           //@QueryParam("username") String username,
                           //@QueryParam("password") String password
                           @QueryParam("token") String token) throws IOException {
        
        //authorization code
        String command = "curl ite_client:user@services.apollonis-infrastructure.gr:8081/oauth/check_token -d token=" + token;
        //String command = "curl ite_client:user@apollonisvm.imsi.athenarc.gr:8081/oauth/token -dgrant_type=password -dusername=" + username + " -dpassword=" + password;
        Process process = Runtime.getRuntime().exec(command);
        InputStream tokenStream = process.getInputStream();
        
        BufferedReader input = new BufferedReader(new InputStreamReader(tokenStream), 1);
        String inputLine;
        
        while((inputLine = input.readLine()) != null) {
            
            if(!inputLine.contains("invalid_token") && !inputLine.contains("error") && inputLine.contains("client_id") && inputLine.contains("user_name")) {
            //if(inputLine.contains("access_token") && !inputLine.contains("error")) {
                BlazegraphManager manager = new BlazegraphManager();

                if(serviceURL == null)
                    serviceURL = propertiesManager.getTripleStoreUrl();

                if(namespace == null)
                    namespace = propertiesManager.getTripleStoreNamespace();

                manager.openConnectionToBlazegraph(serviceURL + "/namespace/" + namespace + "/sparql");

                manager.updateQuery(updateMsg);

                manager.closeConnectionToBlazeGraph();

                return Response.status(200).entity("Successfully updated").header("Access-Control-Allow-Origin", "*").build();
                //return Response.status(200).entity("Updated!!").build();
            }
        }
        return Response.status(404).entity("Authorization failed").header("Access-Control-Allow-Origin", "*").build();
    }
    
    
    @GET
    @Path("/export")
    public Response export(@QueryParam("filename") String filename, 
                                       @QueryParam("service-url") String serviceURL,
                                       @HeaderParam("Accept") String format,
                                       @QueryParam("namespace") String namespace,
                                       @QueryParam("token") String token,
                                       //@QueryParam("username") String username,
                                       //@QueryParam("password") String password,
                                       @DefaultValue("") @QueryParam("graph") String graph) throws IOException {
        //authorization code
        String command = "curl ite_client:user@services.apollonis-infrastructure.gr:8081/oauth/check_token -d token=" + token;
        //String command = "curl ite_client:user@apollonisvm.imsi.athenarc.gr:8081/oauth/token -dgrant_type=password -dusername=" + username + " -dpassword=" + password;
        Process process = Runtime.getRuntime().exec(command);
        InputStream tokenStream = process.getInputStream();
        
        BufferedReader input = new BufferedReader(new InputStreamReader(tokenStream), 1);
        String inputLine;
        
        while((inputLine = input.readLine()) != null) {
    
            if(!inputLine.contains("invalid_token") && !inputLine.contains("error") && inputLine.contains("client_id") && inputLine.contains("user_name")) {
            //if(inputLine.contains("access_token") && !inputLine.contains("error")) {
        
                    System.out.println("filename: " + filename + " namespace:" + namespace + " graph:" 
                                        + graph + " service-url:" + serviceURL + " accept:" + format);
                    BlazegraphManager manager = new BlazegraphManager();

                    if(serviceURL == null)
                        serviceURL = propertiesManager.getTripleStoreUrl();

                    if(namespace == null)
                        namespace = propertiesManager.getTripleStoreNamespace();

                    manager.openConnectionToBlazegraph(serviceURL + "/namespace/" + namespace + "/sparql");

                    RDFFormat rdfFormat = Rio.getParserFormatForMIMEType(format).get();

                    ResponseStatus responseStatus = manager.exportFile(filename, namespace, graph, rdfFormat);

                    manager.closeConnectionToBlazeGraph();

                    if(responseStatus.getStatus() == 200) {
                        String filepath = "/opt/tomcat/apache-tomcat-8.0.53/bin/" + responseStatus.getResponse();
                        File file = new File(filepath);
                        ResponseBuilder response = Response.ok((Object) file);
                        response.header("Content-Disposition","attachment; filename=" + responseStatus.getResponse());
                        return response.build();
                    }

                    return Response.status(responseStatus.getStatus()).entity(responseStatus.getResponse()).header("Access-Control-Allow-Origin", "*").build();
                    //return Response.status(responseStatus.getStatus()).entity(responseStatus.getResponse()).build();
                }
        }
        return Response.status(404).entity("Authorization failed").header("Access-Control-Allow-Origin", "*").build();
    }  	
}






