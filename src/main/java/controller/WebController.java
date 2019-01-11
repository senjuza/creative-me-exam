package controller;

import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.CharacterInfoResp;
import model.req.SearchParam;
import model.resp.RespData;
import util.LoggingRequestInterceptor;
import model.Character;

@PropertySource("classpath:gateway.properties")
@RestController
public class WebController {

	final static Logger log = LoggerFactory.getLogger(WebController.class);
	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private Environment env;
	
	@RequestMapping("/")
	public ModelAndView  index() {
		 
		 return new ModelAndView("redirect:index.html");
	}
	
	

	@PostMapping("/data")
	public String getData(@ModelAttribute  SearchParam searchParam) {
		
		RestTemplate rt = new RestTemplate();
		// set interceptors/requestFactory for debug
		ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
		ris.add(ri);
		rt.setInterceptors(ris);
		rt.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

		String fooResourceUrl = env.getProperty("gateway.url");

		//mapping search parameter
		if(searchParam.calculatePage()>1) {
			fooResourceUrl+="&page="+searchParam.calculatePage();
		}
		if(searchParam.getCharName()!=null && !searchParam.getCharName().equalsIgnoreCase("")) {
			fooResourceUrl+="&name="+searchParam.getCharName();
		}
		if(searchParam.getCharStatus()!=null && !searchParam.getCharStatus().equalsIgnoreCase("")) {
			fooResourceUrl+="&status="+searchParam.getCharStatus();
		}
		if(searchParam.getCharSpecies()!=null && !searchParam.getCharSpecies().equalsIgnoreCase("")) {
			fooResourceUrl+="&species="+searchParam.getCharSpecies();
		}
		if(searchParam.getCharGender()!=null && !searchParam.getCharGender().equalsIgnoreCase("")) {
			fooResourceUrl+="&gender="+searchParam.getCharGender();
		}
		
		CharacterInfoResp characterList=null;
		try {
			//call api gateway
			ResponseEntity<String> response = rt.getForEntity(fooResourceUrl, String.class);

			//mapping object
			characterList = mapper.readValue(response.getBody(), CharacterInfoResp.class);

		} catch (Exception e) {			
			//e.printStackTrace();
		}
		
		if(characterList==null) {
			return getJsonMapperString(new RespData());
		}
		
		//mapping data for response
		RespData respData = new RespData();
		respData.setRecordsFiltered(characterList.getInfo().getCount());
		respData.setRecordsTotal(characterList.getInfo().getCount());
		
		for(Character character : characterList.getCharacterList()) {
			List<String> data = new ArrayList<String>();
			data.add(Integer.toString(character.getId()));
			if(character.getImage()!=null || !character.getImage().equalsIgnoreCase("")) {
				String imgTag = "<img src='"+character.getImage()+"' width='120px' height='120px'/>";
				data.add(imgTag);
			}else {
				data.add("");
			}
			
			data.add(character.getName());
			data.add(character.getStatus());
			data.add(character.getSpecies());
			data.add(character.getGender());
			data.add(character.getOrigin().getName());
			data.add(character.getLocation().getName());

			respData.getData().add(data);
		}
		
		
		//respose json data
		return getJsonMapperString(respData);
	}
	
	
	@Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
	
	//-----------------------------------------------------
	//			private function
	//-----------------------------------------------------
	private String getJsonMapperString(RespData respData) {
		String responseJsonData="";
		try {
			responseJsonData = mapper.writeValueAsString(respData);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseJsonData;
		
	}
	
//	private String prettyPrintJsonString(JsonNode jsonNode) {
//	    try {
//	        ObjectMapper mapper = new ObjectMapper();
//	        Object json = mapper.readValue(jsonNode.toString(), Object.class);
//	        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
//	    } catch (Exception e) {
//	    	log.error(e.getMessage());
//	        return "Sorry, pretty print didn't work";
//	    }
//	}
	

}
