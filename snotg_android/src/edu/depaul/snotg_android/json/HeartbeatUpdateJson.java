package edu.depaul.snotg_android.json;

public class HeartbeatUpdateJson {

	private double latit = 0d;
	private double longit = 0d;
	private String userName;
	public HeartbeatUpdateJson() {super(); }
	
	public HeartbeatUpdateJson(double latit, double longit, String userName) {
		this.latit = latit;
		this.longit = longit;
		this.userName = userName;
	}
	
	/**
	 * @return the latit
	 */
	public double getLatit() {
		return latit;
	}
	/**
	 * @param latit the latit to set
	 */
	public void setLatit(double latit) {
		this.latit = latit;
	}
	/**
	 * @return the longit
	 */
	public double getLongit() {
		return longit;
	}
	/**
	 * @param longit the longit to set
	 */
	public void setLongit(double longit) {
		this.longit = longit;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
