package se.cth.hedgehogphoto.map;

public final class TileServer {
    private final String url;
    private final int maxZoom;
    private boolean broken;

    public TileServer(String url, int maxZoom) {
        this.url = url;
        this.maxZoom = maxZoom;
    }

    public String toString() {
        return url;
    }

    public int getMaxZoom() {
        return maxZoom;
    }
    public String getURL() {
        return url;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}