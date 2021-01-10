package org.onestart;

import java.io.File;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.atlasmap.api.AtlasContext;
import io.atlasmap.api.AtlasContextFactory;
import io.atlasmap.api.AtlasSession;
import io.atlasmap.core.DefaultAtlasContextFactory;

@Path("/hello")
public class TransformationResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String transform(String source) throws Exception {
        // Artifacts
        
        //simple
        //String admLocation = "D:\\02-MyDev\\12-Camel\\02-MyDev\\10-Atlasmap-dev1\\transformation\\src\\main\\resources\\mapping\\simple-UI.0.json";

        //comment
        String admLocation = "/workspace/ApacheCamel/03-MyDev/quarkus-no-camel/atlasmap/json-to-json/transformation/src/main/resources/mapping/comment-UI.0.json";

        // Creating Atlasmap Session
        AtlasContextFactory factory = DefaultAtlasContextFactory.getInstance();
        AtlasContext context = factory.createContext(new File(admLocation));
        AtlasSession session = context.createSession();

        // Setting Source
        session.setSourceDocument("input_comment", source);

        // Transformation
        context.process(session);

        // Generating Destination
        String targetDoc = (String) session.getTargetDocument("output_comment");
        System.out.println("Source value is " + session.getSourceDocument("input_comment"));
        System.out.println("Transformed value is " + targetDoc);
        return targetDoc;
        
        // String source = Files.readString(Paths.get(inputFile));
        // System.out.println("Source document:\n" + source);
        // context.processValidation(session);
        // System.out.println(session.getValidations().toString());
        // if(session.getValidations() == null){
        // System.out.println("No Validations");
        // context.process(session);
        // }else{
        // System.out.println("Has Validations of
        // "+session.getValidations().getValidation().size());
        // Validations validations = session.getValidations();
        // for(Validation validation : validations.getValidation()){
        // System.out.println(" Validations "+validation.getMessage());
        // }
        // }
        // String inputFile =
        // "D:\\02-MyDev\\12-Camel\\02-MyDev\\10-Atlasmap-dev1\\transformation\\src\\main\\resources\\data\\input.json";

    }
}