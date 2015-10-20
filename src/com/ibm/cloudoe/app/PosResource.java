package com.ibm.cloudoe.app;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

@Path("/pos")
public class PosResource {

	@SuppressWarnings("unchecked")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public JSONArray getPosTagging(@FormParam("sentence") String sentence) {
		
		POSModel model = new POSModelLoader().load(new File(getClass().getResource("../resources/en-pos-maxent.bin").getFile()));
		POSTaggerME tagger = new POSTaggerME(model);

        String tokens[] = WhitespaceTokenizer.INSTANCE.tokenize(sentence);
        String[] tags = tagger.tag(tokens);
        POSSample sample = new POSSample(tokens, tags);
        String posTokens[] = sample.getSentence();
        String posTags[] = sample.getTags();
        
        JSONArray array = new JSONArray();
               
        for (int i = 0; i < posTokens.length; i++) {
            JSONObject tempObj = new JSONObject();
            tempObj.put("token", posTokens[i]);
            tempObj.put("tag", posTags[i]);
            array.add(tempObj);
        }       
        
		return array;
	}
}