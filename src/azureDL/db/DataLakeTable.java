package azureDL.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import azureDL.utilities.Definitions;



@Entity(name="DataLakeTable")
@Table(name=Definitions.DATALAKE_SCHEMA+"DL_JAVA_WEB_APP")
public class DataLakeTable extends GenericInstance{
	
	private static final long serialVersionUID = 7578452807773571310L;
	
	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="HOME")
	private String home;

	@Column(name="AWAY")
	private String away;
	
	@Column(name="DATE")
	private String date;
	
	@Column(name="QUOTE")
	private float quote;
	
	@Column(name="BET_TYPE")
	private String betType;

	@Column(name="TS_INS")
	private String ts_ins;
	
	@Column(name="BETTER")
	private String better;
	
	@Column(name="SPORT")
	private String sport;
	
	@Column(name="LEAGUE")
	private String league;
	
	@Column(name="PREDICTION")
	private String prediction;
	
	@Column(name="SIST_PROV")
	private String sistProv;
	
	@Column(name="TS_GET")
	private String ts_get;

	public DataLakeTable(){
		
	}
	
	public DataLakeTable(int id, String home, String away, float quote, String betType, String date, String league, String better,
			String prediction, String sistProv, String sport){
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		this.home = home;
		this.away = away;
		this.quote = quote;
		this.betType = betType;
		this.date = date;
		this.league = league;
		this.better = better;
		this.prediction = prediction;
		this.sistProv = sistProv;
		this.ts_ins = format.format(Calendar.getInstance().getTime().toString());
		this.sport = sport;
	}
	
	public DataLakeTable(int id, String[] params){
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		this.id = id;
		this.home = params[0];
		this.away = params[1];
		this.date = params[2];
		this.quote = Float.valueOf(params[3].replace(",","."));
		this.ts_get = params[4];
		this.league = params[5];
		this.better = params[6];
		this.betType = params[7];
		this.sistProv = params[8];
		this.prediction = params[9];
		this.sport = params[10];
		this.ts_ins = format.format(Calendar.getInstance().getTime()).toString();
	}
	
	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getAway() {
		return away;
	}

	public void setAway(String away) {
		this.away = away;
	}

	public float getQuote() {
		return quote;
	}

	public void setQuote(float quote) {
		this.quote = quote;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public String getBetter() {
		return better;
	}

	public void setBetter(String better) {
		this.better = better;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public String getSistProv() {
		return sistProv;
	}

	public void setSistProv(String sistProv) {
		this.sistProv = sistProv;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTs_get() {
		return ts_get;
	}

	public void setTs_get(String ts_get) {
		this.ts_get = ts_get;
	}
	
	public String getTs_ins() {
		return ts_ins;
	}

	public void setTs_ins(String ts_ins) {
		this.ts_ins = ts_ins;
	}
	
	public void printRow(){
		System.out.println(Definitions.PRINT_SEPARATOR);
		System.out.println("home : " + this.getHome());
		System.out.println("away : " + this.getAway());
		System.out.println("quote : " + this.getQuote());
		System.out.println("ts_get : " + this.getTs_get());
		System.out.println("date : " + this.getDate());
		System.out.println("sport : " + this.getSport());
		System.out.println("league : " + this.getLeague());
		System.out.println("better : " + this.getBetter());
		System.out.println("betType : " + this.getBetType());
		System.out.println("prediction : " + this.getPrediction());
		System.out.println("sistprov : " + this.getSistProv());
	}
	
}
