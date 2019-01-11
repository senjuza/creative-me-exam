package model.req;

public class SearchParam {
	private int start;
	private int length;
	private String charName;
	private String charStatus;
	private String charGender;
	private String charSpecies;
	

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	public String getCharStatus() {
		return charStatus;
	}

	public void setCharStatus(String charStatus) {
		this.charStatus = charStatus;
	}

	public String getCharGender() {
		return charGender;
	}

	public void setCharGender(String charGender) {
		this.charGender = charGender;
	}

	public String getCharSpecies() {
		return charSpecies;
	}

	public void setCharSpecies(String charSpecies) {
		this.charSpecies = charSpecies;
	}

	//-------------	
	
	
	public int calculatePage() {
		int pageNum = 0;
		try {
			pageNum = start/length;
			pageNum++;
		}catch(Exception ex) {
			
		}
		return pageNum;
	}


	//-------------
	@Override
	public String toString() {
		return "SearchParam [start=" + start + ", length=" + length + ", charName=" + charName + ", charStatus="
				+ charStatus + ", charGender=" + charGender + ", charSpecies=" + charSpecies + "]";
	}
	

	
	
	
}
