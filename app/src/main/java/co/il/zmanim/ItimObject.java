package co.il.zmanim;

import com.google.gson.annotations.SerializedName;

public class ItimObject{

	@SerializedName("AmudAshachar")
	private String amudAshachar;

	@SerializedName("Chatzot")
	private String chatzot;

	@SerializedName("NetzAchama")
	private String netzAchama;

	@SerializedName("HeDate")
	private String heDate;

	@SerializedName("SofZmanKSMagen")
	private String sofZmanKSMagen;

	@SerializedName("Shkiah")
	private String shkiah;

	@SerializedName("MinchKtana")
	private String minchKtana;

	@SerializedName("MinchaGdola")
	private String minchaGdola;

	@SerializedName("SofZmanTfila")
	private String sofZmanTfila;

	@SerializedName("SofZmanKSHgaon")
	private String sofZmanKSHgaon;

	@SerializedName("TzetAshabat")
	private String tzetAshabat;

	@SerializedName("EnDate")
	private String enDate;

	@SerializedName("AdlakatNerot")
	private String adlakatNerot;

	public void setAmudAshachar(String amudAshachar){
		this.amudAshachar = amudAshachar;
	}

	public String getAmudAshachar(){
		return amudAshachar;
	}

	public void setChatzot(String chatzot){
		this.chatzot = chatzot;
	}

	public String getChatzot(){
		return chatzot;
	}

	public void setNetzAchama(String netzAchama){
		this.netzAchama = netzAchama;
	}

	public String getNetzAchama(){
		return netzAchama;
	}

	public void setHeDate(String heDate){
		this.heDate = heDate;
	}

	public String getHeDate(){
		return heDate;
	}

	public void setSofZmanKSMagen(String sofZmanKSMagen){
		this.sofZmanKSMagen = sofZmanKSMagen;
	}

	public String getSofZmanKSMagen(){
		return sofZmanKSMagen;
	}

	public void setShkiah(String shkiah){
		this.shkiah = shkiah;
	}

	public String getShkiah(){
		return shkiah;
	}

	public void setMinchKtana(String minchKtana){
		this.minchKtana = minchKtana;
	}

	public String getMinchKtana(){
		return minchKtana;
	}

	public void setMinchaGdola(String minchaGdola){
		this.minchaGdola = minchaGdola;
	}

	public String getMinchaGdola(){
		return minchaGdola;
	}

	public void setSofZmanTfila(String sofZmanTfila){
		this.sofZmanTfila = sofZmanTfila;
	}

	public String getSofZmanTfila(){
		return sofZmanTfila;
	}

	public void setSofZmanKSHgaon(String sofZmanKSHgaon){
		this.sofZmanKSHgaon = sofZmanKSHgaon;
	}

	public String getSofZmanKSHgaon(){
		return sofZmanKSHgaon;
	}

	public void setTzetAshabat(String tzetAshabat){
		this.tzetAshabat = tzetAshabat;
	}

	public String getTzetAshabat(){
		return tzetAshabat;
	}

	public void setEnDate(String enDate){
		this.enDate = enDate;
	}

	public String getEnDate(){
		return enDate;
	}

	public void setAdlakatNerot(String adlakatNerot){
		this.adlakatNerot = adlakatNerot;
	}

	public String getAdlakatNerot(){
		return adlakatNerot;
	}


	@Override
	public String toString() {
		return
				"עמוד השחר='" + amudAshachar + '\'' +
				",\n\n חצות='" + chatzot + '\'' +
				",\n\n נץ החמה='" + netzAchama + '\'' +
				",\n\n תאריך עיברי='" + heDate + '\'' +
				",\n\n סוף זמן ק'ש מג'א='" + sofZmanKSMagen + '\'' +
				",\n\n שקיעה='" + shkiah + '\'' +
				",\n\n מנחה קטנה='" + minchKtana + '\'' +
				",\n\n מנחה גדולה='" + minchaGdola + '\'' +
				",\n\n סוף זמן תפילה='" + sofZmanTfila + '\'' +
				",\n\n סוף זמן ק'ש הגאון='" + sofZmanKSHgaon + '\'' +
				",\n\n צאת השבת='" + tzetAshabat + '\'' +
				",\n\n תאריך לועזי='" + enDate + '\'' +
				",\n\n הדלקת נרות='" + adlakatNerot + '\''
				;
	}
}