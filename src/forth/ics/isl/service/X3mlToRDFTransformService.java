package forth.ics.isl.service;

import gr.forth.ics.isl.x3ml.X3MLEngineFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONObject;

/**
*
* @author Vangelis Kritsotakis
* 
* Web-service used for transforming X3ML files (along with the respective input files and the Generator Policy file) to RDF.
* 
* The service accepts a list of input files, a list of X3ML files and one generator file. The user has three alternative ways 
* of setting the required input:
* 	i.   Use file paths on the server;
* 	ii.  Use URLs of files; or 
* 	iii. use binary files (input Stream);
* 
* All the above ways can be mixed up as long as a) there is at least one input, one X3ML and one Generator Policy file and 
* b) there is only one Generator Policy set in either way.
* 
* There service mixes up all declared input and X3ML files but follows the priority order: Input Stream, File Path and finally 
* URL for the Generator policy. 
*/
@Path("/transform")
public class X3mlToRDFTransformService {
	
	@POST
    @Path("/x3mltoRdf")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
    public Response x3mltoRdftransform(@FormDataParam("inputFileStream") List<FormDataBodyPart> inputFormDataBodyPartList,
    								   @FormDataParam("x3mlFileStream") List<FormDataBodyPart> x3mlFormDataBodyPartList,
    								   @FormDataParam("generatorPolicyFileStream") FormDataBodyPart generatorPolicyFormDataBodyPart,
    								   @FormDataParam("inputFilePath") List<String> inputFilePathList,
    								   @FormDataParam("x3mlFilePath") List<String> x3mlFilePathList,
    								   @FormDataParam("generatorPolicyFilePath") String generatorPolicyFilePath,
    								   @FormDataParam("inputFileUrl") List<String> inputFileUrlList,
    								   @FormDataParam("x3mlFileUrl") List<String> x3mlFileUrlList,
    								   @FormDataParam("generatorPolicyFileUrl") String generatorPolicyFileUrl,
    								   @FormDataParam("transformContentType") String transformContentType) {
		
		JSONObject message = new JSONObject();
		int status = 0;
		boolean proceed = true;
		
		try {
			// Been Optimistic in advance
			message.put("message", "Completed Succesfully");
			message.put("status", "SUCCEED");
			status = 200;
			
			X3MLEngineFactory x3MLEngineFactory = X3MLEngineFactory.create();
			
			// Initialize the FormDataBodyPart lists in case of not been set
			if(inputFormDataBodyPartList == null)
				inputFormDataBodyPartList = new ArrayList<FormDataBodyPart>();
			if(x3mlFormDataBodyPartList == null)
				x3mlFormDataBodyPartList = new ArrayList<FormDataBodyPart>();
			
			// Source Input Files
			if(proceed) {
				if(inputFormDataBodyPartList.size() > 0 || inputFilePathList.size() > 0 || inputFileUrlList.size() > 0) {
					// InputStreams
					inputFormDataBodyPartList.forEach(inputFormDataBodyPart -> {
						InputStream inputFileStream = inputFormDataBodyPart.getEntityAs(InputStream.class);
						x3MLEngineFactory.withInput(inputFileStream);
					});
					// FilePaths
					inputFilePathList.forEach(filePath -> {
					    x3MLEngineFactory.withInputFiles(new File(filePath));
					});
					// FileURLs
					inputFileUrlList.forEach(fileUrlStr -> {
					    System.out.println(fileUrlStr);
						try {
							URL fileUrl = new URL(fileUrlStr);
							x3MLEngineFactory.withInput(fileUrl);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					});
				}
				else {
					message.put("message", "Please use at least one \"inputFileStream\" \"inputFilePath\" or \"inputFileUrl\" param.");
					message.put("status", "FAILED");
					status = 406;
					proceed = false;
				}
			}
			
			// X3ML Files
			if(proceed) {
				if(x3mlFormDataBodyPartList.size() > 0 || x3mlFilePathList.size() > 0 || x3mlFileUrlList.size() > 0) {
					// InputStreams
					x3mlFormDataBodyPartList.forEach(x3mlFormDataBodyPart -> {
						InputStream x3mlFileStream = x3mlFormDataBodyPart.getEntityAs(InputStream.class);
						System.out.println(x3mlFileStream);
						x3MLEngineFactory.withMappings(x3mlFileStream);
					});

					// FilePaths
					x3mlFilePathList.forEach(filePath -> {
					    System.out.println(filePath);
					    x3MLEngineFactory.withMappings(new File(filePath));
					});

					// FileURLs
					x3mlFileUrlList.forEach(fileUrlStr -> {
					    System.out.println(fileUrlStr);
						try {
							URL fileUrl = new URL(fileUrlStr);
							x3MLEngineFactory.withMappings(fileUrl);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					});
				}
				else {
					message.put("message", "Please use at least one \"x3mlFileStream\" \"x3mlFilePath\" or \"x3mlFileUrl\" param.");
					message.put("status", "FAILED");
					status = 406;
					proceed = false;
				}
			}
			
			// Generator Policy File
			if(proceed) {
				// Only one generator policy is allowed 
				// (priority Order: i. InputStream, ii. FilePath iii.FileURL)
				
				// InputStream
				if(generatorPolicyFormDataBodyPart != null) {
					InputStream generatorPolicyFileStream = generatorPolicyFormDataBodyPart.getEntityAs(InputStream.class);
					System.out.println(generatorPolicyFileStream);
					x3MLEngineFactory.withGeneratorPolicy(generatorPolicyFileStream);
				}
				// FilePath
				else if(generatorPolicyFilePath != null) {
					System.out.println(generatorPolicyFilePath);
					x3MLEngineFactory.withGeneratorPolicy(new File(generatorPolicyFilePath));
				}
				// FileURL
				else if(generatorPolicyFileUrl != null) { // generatorPolicyFileUrl is a String
					System.out.println(generatorPolicyFileUrl);
					try {
						// Mind that generatorPolicyFileUrl variable is a String
						URL fileUrl = new URL(generatorPolicyFileUrl);
						x3MLEngineFactory.withGeneratorPolicy(fileUrl);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
				
				else {
					message.put("message", "Please use either the \"generatorPolicyFilePath\" or \"generatorPolicyFileUrl\" param.");
					message.put("status", "FAILED");
					status = 406;
					proceed = false;
				}
			}
			
			if(proceed) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				if(transformContentType.equals("rdf-xml"))
					x3MLEngineFactory.withOutput(byteArrayOutputStream, X3MLEngineFactory.OutputFormat.RDF_XML);
				else if(transformContentType.equals("turtle"))
					x3MLEngineFactory.withOutput(byteArrayOutputStream, X3MLEngineFactory.OutputFormat.TURTLE);
				else
					x3MLEngineFactory.withOutput(byteArrayOutputStream, X3MLEngineFactory.OutputFormat.RDF_XML);
				x3MLEngineFactory.execute();
				String output = byteArrayOutputStream.toString();
				message.put("output", output.replace("\n", "").replace("\r", ""));
				
				String errors = "";
				if(!gr.forth.ics.isl.x3ml.X3MLEngine.exceptionMessagesList.equals("")) {
					errors = gr.forth.ics.isl.x3ml.X3MLEngine.exceptionMessagesList;
					message.put("errorMessage", errors);
				}
				//System.out.println();
				//System.out.println("output:");
				//System.out.println(output);
			}

		} catch (Exception ex) {
			message.put("message", "Failed to complete");
			message.put("errorMessage", ex.getMessage());
			message.put("status", "FAILED");
			status = 500;
		}
				
        return Response.status(status).entity(message.toString()).header("Access-Control-Allow-Origin", "*").build();
    }
	
}
