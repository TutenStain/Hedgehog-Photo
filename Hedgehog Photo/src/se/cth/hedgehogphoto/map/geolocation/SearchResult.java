package se.cth.hedgehogphoto.map.geolocation;

public final class SearchResult {
	private String type;
	private double lat, lon;
	private String name;
	private String category;
	private String info;
	private int zoom;
	private String description = "";

	public SearchResult() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return "SearchResult [category=" + category + ", info=" + info
				+ ", lat=" + lat + ", lon=" + lon + ", name=" + name
				+ ", type=" + type + ", zoom=" + zoom + ", description="
				+ description + "]";
	}

}
