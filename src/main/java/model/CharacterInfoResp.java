package model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterInfoResp {
	 
	@JsonProperty("info")
	private Info info; 
	@JsonProperty("results")
	private List<Character> characterList;
	
	
	
	public Info getInfo() {
		return info;
	}



	public void setInfo(Info info) {
		this.info = info;
	}



	public List<Character> getCharacterList() {
		return characterList;
	}



	public void setCharacterList(List<Character> characterList) {
		this.characterList = characterList;
	}



	@Override
	public String toString() {
		return "CharacterInfoResp [info=" + info + ", characterList=" + characterList + "]";
	} 
	
	
}
