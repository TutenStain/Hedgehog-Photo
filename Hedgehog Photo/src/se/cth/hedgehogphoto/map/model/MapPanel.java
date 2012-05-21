package se.cth.hedgehogphoto.map.model;
/*******************************************************************************
 * Copyright (c) 2008, 2012 Stepan Rutz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stepan Rutz - initial implementation
 *    Florian Minges - modifier
 *******************************************************************************/

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import se.cth.hedgehogphoto.Constants;
import se.cth.hedgehogphoto.database.LocationObject;
import se.cth.hedgehogphoto.log.Log;


/**
 * MapPanel display tiles from openstreetmap as is. This simple minimal viewer supports zoom around mouse-click center and has a simple api.
 * A number of tiles are cached. See {@link #CACHE_SIZE} constant. If you use this it will create traffic on the tileserver you are
 * using. Please be conscious about this.
 *
 * This class is a JPanel which can be integrated into any swing app just by creating an instance and adding like a JLabel.
 *
 * The map has the size <code>256*1<<zoomlevel</code>. This measure is referred to as map-coordinates. Geometric locations
 * like longitude and latitude can be obtained by helper methods. Note that a point in map-coordinates corresponds to a given
 * geometric position but also depending on the current zoom level.
 *
 * You can zoomIn around current mouse position by left double click. Left right click zooms out.
 *
 * <p>
 * Methods of interest are
 * <ul>
 * <li>{@link #setZoom(int)} which sets the map's zoom level. Values between 1 and 18 are allowed.</li>
 * <li>{@link #setMapPosition(Point)} which sets the map's top left corner. (In map coordinates)</li>
 * <li>{@link #setCenterPosition(Point)} which sets the map's center position. (In map coordinates)</li>
 * <li>{@link #computePosition(java.awt.geom.Point2D.Double)} returns the position in the map panels coordinate system
 * for the given longitude and latitude. If you want to center the map around this geometric location you need
 * to pass the result to the method</li>
 * </ul>
 * </p>
 *
 * <p>As mentioned above Longitude/Latitude functionality is available via the method {@link #computePosition(java.awt.geom.Point2D.Double)}.
 * If you have a GIS database you can get this info out of it for a given town/location, invoke {@link #computePosition(java.awt.geom.Point2D.Double)} to
 * translate to a position for the given zoom level and center the view around this position using {@link #setCenterPosition(Point)}.
 * </p>
 *
 * <p>The properties <code>zoom</code> and <code>mapPosition</code> are bound and can be tracked via
 * regular {@link PropertyChangeListener}s.</p>
 *
 * <p>License is EPL (Eclipse Public License).  Contact at stepan.rutz@gmx.de</p>
 *
 * @author stepan.rutz
 * @modifiedby Florian Minges
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel {

    private static final Logger log = Log.getLogger();

    public static final class TileServer {
        private final String url;
        private final int maxZoom;
        private boolean broken;

        private TileServer(String url, int maxZoom) {
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

    /* constants ... */
    private static final TileServer[] TILESERVERS = {
        new TileServer("http://tile.openstreetmap.org/", 18),
        /*new TileServer("http://tah.openstreetmap.org/Tiles/tile/", 17),*/ /*This TileServer appears to be broken. */
    };

    private static final String NAMEFINDER_URL = "http://gazetteer.openstreetmap.org/namefinder/search.xml";
    /* The Preferred width and height of the panel. Doesn't change the actual map size, but is important for calculations. */
    private static final int PREFERRED_WIDTH = se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_WIDTH;
    private static final int PREFERRED_HEIGHT = se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_HEIGHT;

    private static final int ANIMATION_FPS = 15, ANIMATION_DURARTION_MS = 500;
    


    /* TODO: Display the ABOUT_MSG somewhere else in a proper manner. */
    private static final int TILE_SIZE = 256;
    private static final int CACHE_SIZE = 256;
    private static final String ABOUT_MSG =
        "MapPanel - Minimal Openstreetmap/Maptile Viewer\r\n" +
        "Web: http://mappanel.sourceforge.net\r\n" +
        "Written by stepan.rutz. Contact stepan.rutz@gmx.de\r\n\r\n" +
        "Tileserver-URLs: " + Arrays.toString(TILESERVERS) + "\r\n" +
        "Namefinder-URL: " + NAMEFINDER_URL + "\r\n" +
        "Tileserver and Namefinder are part of Openstreetmap or associated projects.\r\n\r\n" +
        "MapPanel gets its data from these servers.\r\n\r\n" +
        "Please visit and support the actual projects at http://www.openstreetmap.org/.\r\n" +
        "And keep in mind this application is just a simple alternative renderer for swing.\r\n";

    private static final int MAGNIFIER_SIZE = 100; 

    //-------------------------------------------------------------------------
    // tile url construction.
    // change here to support some other tile

    public static String getTileString(TileServer tileServer, int xtile, int ytile, int zoom) {
        String number = ("" + zoom + "/" + xtile + "/" + ytile);
        String url = tileServer.getURL() + number + ".png";
        return url;
    }

    //-------------------------------------------------------------------------
    // map impl.
    
    private static Point mapPosition = new Point(0, 0);
    protected static int zoom;
    private Dimension mapSize = new Dimension(0, 0);
    private Point centerPosition = new Point(0, 0);

    private TileServer tileServer = TILESERVERS[0];

    private DragListener mouseListener = new DragListener();
    private TileCache cache = new TileCache();
    private ControlPanel controlPanel = new ControlPanel();

    private boolean useAnimations = true;
    private Animation animation;

    protected double smoothScale = 1.0D;
    private int smoothOffset = 0;
    private Point smoothPosition, smoothPivot;
    private Rectangle magnifyRegion;
    
    /** If true, it's possible to move around and scroll in the map. 
     *  If false, it's a static map. */
    private boolean interactionEnabled;

    public MapPanel() {
        this(new Point(8282, 5179), 16);
    }

    protected MapPanel(Point mapPosition, int zoom) {
        
        try {
            // disable animation on windows7 for now
            useAnimations = !("Windows Vista".equals(System.getProperty("os.name")) && "6.1".equals(System.getProperty("os.version")));
        } catch (Exception e) {
            // be defensive here
            log.log(Level.INFO, "failed to check for win7", e);
        }
        
        setLayout(new MapLayout());
        setOpaque(true);
        setBackground(new Color(0xc0, 0xc0, 0xc0));
        add(controlPanel);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        addMouseWheelListener(mouseListener);
        setZoom(zoom);
        updateMapPositionWithoutFire(mapPosition.x, mapPosition.y);

        checkTileServers();
        checkActiveTileServer();
        interactionEnabled = false; //will prevent listener to work correctly
    }
    
    public void addObserver(PropertyChangeListener listener) {
    	addPropertyChangeListener(listener);
    }
    
    /*---------------------------------------------------------------------
    					HERE COMES SOME IMPORTANT CODE FOR OURS IMPLEMENTATION OF THE MAPPANEL@author Florian
    ---------------------------------------------------------------------*/
    
    private List<LocationObject> locations;
    private Point.Double centerLocation;
    
    public void calibrate(List<LocationObject> locations) {
    	setBounds(0, 0, PREFERRED_WIDTH, PREFERRED_HEIGHT);
    	this.locations = locations;
    	updateCenterLocation();
    	adjustZoom();
		enableControlPanel(true);
		enableInteraction(true);
		setOpaque(true);
    }
    
    /** Calculates the proper zoom so that every location fits on the 
	 *  visible part of the map. */
	private void adjustZoom() {
		if (centerLocation.getX() == 0.0 && centerLocation.getY() == 0.0) {
			setZoom(1);
			centerMap();
			return;
		}
		int zoom = 16;
		setZoom(zoom);
		centerMap();
		while (!allLocationsVisible() && zoom != 1) {
			setZoom(--zoom);
			centerMap();
		} 
		
	}
	
	/** Internal method for updating the centerLocation-variable. */
	private void updateCenterLocation() {
		Point.Double location = new Point.Double();
		location.setLocation(averageLongitude(), averageLatitude());
		centerLocation = location;
	}
	
	/** Tells the map to center its' view to the specified centerLocation. */
	private void centerMap() {
		Point position = computeMapPosition(centerLocation.getX(), centerLocation.getY());
		setCenterPosition(position);
	}
	
	/** Returns true if all Locations are visible on the map. */
	private boolean allLocationsVisible() {
		boolean result = true;
		int nbrOfLocations = locations.size();
		for (int index = 0; index < nbrOfLocations; index++) {
			Point pixelPosition = getPixelPosition(locations.get(index));
			if (!validPixelPosition(pixelPosition)) {
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	/** Returns true if the passed pixel p is: 0 < p < SIZE.
	 *  ie if it is part of the map. Returns false otherwise. */
	private boolean validPixelPosition(Point pixelPosition) {
		boolean longitudeOK = (pixelPosition.x > Constants.PREFERRED_MODULE_WIDTH * 0.05 && pixelPosition.x < Constants.PREFERRED_MODULE_WIDTH * 0.95);
		boolean latitudeOK = (pixelPosition.y > Constants.PREFERRED_MODULE_HEIGHT * 0.10 && pixelPosition.y < Constants.PREFERRED_MODULE_HEIGHT * 0.90);
		return (longitudeOK && latitudeOK);
	}
	
	/** Returns the average Latitude for the stored locations.
	 *  If there are no locations, 0.0 is returned. */
	private double averageLatitude() {
		double totalLatitude = 0.0;
		double nbrOfLocations = locations.size();
		for(int i = 0; i < nbrOfLocations; i++) {
			totalLatitude += locations.get(i).getLatitude();
		}
		
		double averageLatitude = nbrOfLocations != 0 ? totalLatitude / nbrOfLocations : 0.0; 
		return averageLatitude;
	}
	
	/** Returns the average Longitude for the stored locations.
	 *  If there are no locations, 0.0 is returned. */
	private double averageLongitude() {
		double totalLongitude = 0.0;
		double nbrOfLocations = locations.size();
		for(int i = 0; i < nbrOfLocations; i++) {
			totalLongitude += locations.get(i).getLongitude();
		}

		double averageLongitude = nbrOfLocations != 0 ? totalLongitude / nbrOfLocations : 0.0; 
		return averageLongitude;
	}
    
    /** Returns a list of pixel coordinates for all Locations.
	 *  These pixel coordinates specify where, relative to the map,
	 *  where the locationMarkers have to be placed. */
	public Point getPixelPosition(LocationObject location) {
		Point pixelPosition = computeMapPosition(location);
		pixelPosition.x = pixelPosition.x - getMapPosition().x;
		pixelPosition.y = pixelPosition.y - getMapPosition().y;
		return pixelPosition;
	}
	
	public static Point computePixelPositionOnMap(double longitude, double latitude) {
		Point p = computeMapPosition(longitude, latitude);
		p.x -= mapPosition.x;
		p.y -= mapPosition.y;
		return p;
	}
    
    private static Point computeMapPosition(LocationObject location) {
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		return computeMapPosition(longitude, latitude);
	}
	
	private static Point computeMapPosition(double longitude, double latitude) {
		return computePosition(new Point2D.Double(longitude, latitude));
	}
    
    /*---------------------------------------------------------------------
						HERE ENDS SOME IMPORTANT CODE
	---------------------------------------------------------------------*/

	@SuppressWarnings("unused")
	private void checkTileServers() {
        for (TileServer tileServer : TILESERVERS) {
            String urlstring = getTileString(tileServer, 1, 1, 1);
            try {
                URL url = new URL(urlstring);
                Object content = url.getContent();
            } catch (Exception e) {
                log.log(Level.SEVERE, "failed to get content from url " + urlstring);
                tileServer.setBroken(true);
            }
        }
    }

    private void checkActiveTileServer() {
    	/* IF POSSIBLE: Make it only check if the tileServer != null
    	 * if that is the case, call checkTileServers and see if it is broken. */
        if (getTileServer() != null && getTileServer().isBroken()) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                	String error = "The tileserver \"" + getTileServer().getURL() + "\" could not be reached.\r\nMaybe configuring a http-proxy is required.";
                    JOptionPane.showMessageDialog(
                            SwingUtilities.getWindowAncestor(MapPanel.this),
                            error, "TileServer not reachable.", JOptionPane.ERROR_MESSAGE);
                    Log.getLogger().log(Level.SEVERE, error);
                }
            });
        }
    }

    public void nextTileServer() {
        int index = Arrays.asList(TILESERVERS).indexOf(getTileServer());
        if (index == -1)
            return;
        setTileServer(TILESERVERS[(index + 1) % TILESERVERS.length]);
        repaint();
    }

    public TileServer getTileServer() {
        return tileServer;
    }

    public void setTileServer(TileServer tileServer) {
        if(this.tileServer == tileServer)
            return;
        this.tileServer = tileServer;
        while (getZoom() > tileServer.getMaxZoom())
            zoomOut(new Point(getWidth() / 2, getHeight() / 2));
        checkActiveTileServer();
    }

    public boolean isUseAnimations() {
        return useAnimations;
    }

    public void setUseAnimations(boolean useAnimations) {
        this.useAnimations = useAnimations;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }
    
    public TileCache getCache() {
        return cache;
    }

    public Point getMapPosition() {
        return new Point(mapPosition.x, mapPosition.y);
    }

    public void setMapPosition(Point mapPosition) {
        setMapPosition(mapPosition.x, mapPosition.y);
    }

    public void setMapPosition(int x, int y) {
        if (mapPosition.x == x && mapPosition.y == y)
            return;
        Point oldValue = new Point(mapPosition.x, mapPosition.y);
        updateMapPositionWithoutFire(x, y);
        firePropertyChange("mapPosition", oldValue, mapPosition);
    }
    
    public void updateMapPositionWithoutFire(int x, int y) {
    	if (mapPosition.x == x && mapPosition.y == y)
            return;
    	mapPosition.x = x;
        mapPosition.y = y;
        centerPosition.x = mapPosition.x + PREFERRED_WIDTH / 2;
        centerPosition.y = mapPosition.y + PREFERRED_HEIGHT / 2;
    }

    public void translateMapPosition(int tx, int ty) {
        setMapPosition(mapPosition.x + tx, mapPosition.y + ty);
    }

    protected int getZoom() {
        return zoom;
    }

    protected void setZoom(int zoom) {
        if (zoom == MapPanel.zoom) 
            return;
        MapPanel.zoom = Math.min(getTileServer().getMaxZoom(), zoom);
        mapSize.width = getXMax();
        mapSize.height = getYMax();
    }

    /** Enables/disables map interaction
     *  If set to false, it becomes a static map. */
    public void enableInteraction(boolean state) {
    	interactionEnabled = state;
    }
    
    /** Hides/shows overlay and control panel */
    public void enableAllOverlayPanels(boolean state) {
    	enableControlPanel(state);
    }
    
    /** Doesn't actually enable/disable the panel,
     * just hides or shows it. */
    public void enableControlPanel(boolean state) {
    	getControlPanel().setVisible(state);
    }

    public void zoomInAnimated(Point pivot) {
        if (!useAnimations) {
            zoomIn(pivot);
            return;
        }
        if (animation != null)
            return;
        mouseListener.downCoords = null;
        animation = new Animation(AnimationType.ZOOM_IN, ANIMATION_FPS, ANIMATION_DURARTION_MS) {
            protected void onComplete() {
                smoothScale = 1.0d;
                smoothPosition = smoothPivot = null;
                smoothOffset = 0;
                animation = null;
                repaint();
            }
            protected void onFrame() {
                smoothScale = 1.0 + getFactor();
                repaint();
            }

        };
        smoothPosition = new Point(mapPosition.x, mapPosition.y);
        smoothPivot = new Point(pivot.x, pivot.y);
        smoothOffset = -1;
        zoomIn(pivot);
        animation.run();
    }

    public void zoomOutAnimated(Point pivot) {
        if (!useAnimations) {
            zoomOut(pivot);
            return;
        }
        if (animation != null)
            return;
        mouseListener.downCoords = null;
        animation = new Animation(AnimationType.ZOOM_OUT, ANIMATION_FPS, ANIMATION_DURARTION_MS) {
            protected void onComplete() {
                smoothScale = 1.0d;
                smoothPosition = smoothPivot = null;
                smoothOffset = 0;
                animation = null;
                repaint();
            }
            protected void onFrame() {
                smoothScale = 1 - .5 * getFactor();
                repaint();
            }

        };
        smoothPosition = new Point(mapPosition.x, mapPosition.y);
        smoothPivot = new Point(pivot.x, pivot.y);
        smoothOffset = 1;
        zoomOut(pivot);
        animation.run();
    }

    public void zoomIn(Point pivot) {
        if (getZoom() >= getTileServer().getMaxZoom())
            return;
        Dimension oldValue = new Dimension(mapSize.width, mapSize.height);
        int dx = pivot.x - PREFERRED_WIDTH / 2;
        int dy = pivot.y - PREFERRED_WIDTH / 2;
        Point mapPosition = getMapPosition();
        setZoom(getZoom() + 1);
        updateMapPositionWithoutFire(mapPosition.x * 2 + dx + PREFERRED_WIDTH / 2, 
        							mapPosition.y * 2 + dy + PREFERRED_HEIGHT / 2); //zooms in so that the cursor stays were it was (points to the same location)
        firePropertyChange(Global.ZOOM_IN_EVENT, oldValue, mapSize);
        repaint();
    }

    public void zoomOut(Point pivot) {
        if (getZoom() <= 1)
            return;
        Dimension oldValue = new Dimension(mapSize.width, mapSize.height);
        int dx = pivot.x;
        int dy = pivot.y;
        Point mapPosition = getMapPosition();
        setZoom(getZoom() - 1);
        updateMapPositionWithoutFire((mapPosition.x - dx) / 2, 
        							(mapPosition.y - dy) / 2); //zooms out so that the cursor stays were it was (points to the same location)
        firePropertyChange(Global.ZOOM_OUT_EVENT, oldValue, mapSize);
        repaint();
    }

    public int getXTileCount() {
        return (1 << zoom);
    }

    public int getYTileCount() {
        return (1 << zoom);
    }

    public int getXMax() {
        return TILE_SIZE * getXTileCount();
    }

    public int getYMax() {
        return TILE_SIZE * getYTileCount();
    }

    public Point getCursorPosition() {
        return new Point(mapPosition.x + mouseListener.mouseCoords.x, mapPosition.y + mouseListener.mouseCoords.y);
    }

    public Point getTile(Point position) {
        return new Point((int) Math.floor(((double) position.x) / TILE_SIZE),(int) Math.floor(((double) position.y) / TILE_SIZE));
    }

    public Point getCenterPosition() {
    	if (getWidth() != 0 && getHeight() != 0)
    		return new Point(mapPosition.x + getWidth() / 2, mapPosition.y + getHeight() / 2);
    	return new Point(mapPosition.x + PREFERRED_WIDTH / 2, mapPosition.y + PREFERRED_HEIGHT / 2);
    }
    
    public Point getStoredCenterPosition() {
    	return centerPosition;
    }

    public void setCenterPosition(Point p) {
        setMapPosition(p.x - PREFERRED_WIDTH / 2, p.y - PREFERRED_HEIGHT / 2);
        centerPosition = p; 
    }

    public Point.Double getLongitudeLatitude(Point position) {
        return new Point.Double(
                position2lon(position.x),
                position2lat(position.y));
    }

    public static Point computePosition(Point.Double coords) {
        int x = lon2position(coords.x);
        int y = lat2position(coords.y);
        return new Point(x, y);
    }

    protected void paintComponent(Graphics gOrig) {
        super.paintComponent(gOrig);
        Graphics2D g = (Graphics2D) gOrig.create();
        try {
            paintInternal(g);
        } finally {
            g.dispose();
        }
    }

    /** Important inner class which should NOT be ported to the outside. */
    private static final class Painter {
        private final int zoom;
        private float transparency = 1F;
        private double scale = 1d;
        private final MapPanel mapPanel;

        private Painter(MapPanel mapPanel, int zoom) {
            this.mapPanel = mapPanel;
            this.zoom = zoom;
        }

        public float getTransparency() {
            return transparency;
        }

        public void setTransparency(float transparency) {
            this.transparency = transparency;
        }

        public double getScale() {
            return scale;
        }

        public void setScale(double scale) {
            this.scale = scale;
        }

        private void paint(Graphics2D gOrig, Point mapPosition, Point scalePosition) {
            Graphics2D g = (Graphics2D) gOrig.create();
            try {
                if (getTransparency() < 1f && getTransparency() >= 0f) {
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, transparency));
                }

                if (getScale() != 1d) {
                    AffineTransform xform = new AffineTransform();
                    xform.translate(scalePosition.x, scalePosition.y);
                    xform.scale(scale, scale);
                    xform.translate(-scalePosition.x, -scalePosition.y);
                    g.transform(xform);
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                }
                int width = mapPanel.getWidth();
                int height = mapPanel.getHeight();
                int x0 = (int) Math.floor(((double) mapPosition.x) / TILE_SIZE);
                int y0 = (int) Math.floor(((double) mapPosition.y) / TILE_SIZE);
                int x1 = (int) Math.ceil(((double) mapPosition.x + width) / TILE_SIZE);
                int y1 = (int) Math.ceil(((double) mapPosition.y + height) / TILE_SIZE);

                int dy = y0 * TILE_SIZE - mapPosition.y;
                for (int y = y0; y < y1; ++y) {
                    int dx = x0 * TILE_SIZE - mapPosition.x;
                    for (int x = x0; x < x1; ++x) {
                        paintTile(g, dx, dy, x, y);
                        dx += TILE_SIZE;
                    }
                    dy += TILE_SIZE;
                }
                
                if (getScale() == 1d && mapPanel.magnifyRegion != null) {
                    Rectangle magnifyRegion = new Rectangle(mapPanel.magnifyRegion);
                    magnifyRegion.translate(-mapPosition.x, -mapPosition.y);
                    g.setColor(Color.yellow);
                }
            } finally {
                g.dispose();
            }
        }

        private void paintTile(Graphics2D g, int dx, int dy, int x, int y) {
            boolean DEBUG = false;
            boolean DRAW_IMAGES = true;
            boolean DRAW_OUT_OF_BOUNDS = false;

            boolean imageDrawn = false;
            int xTileCount = 1 << zoom;
            int yTileCount = 1 << zoom;
            boolean tileInBounds = x >= 0 && x < xTileCount && y >= 0 && y < yTileCount;
            boolean drawImage = DRAW_IMAGES && tileInBounds;
            if (drawImage) {
                TileCache cache = mapPanel.getCache();
                TileServer tileServer = mapPanel.getTileServer();
                Image image = cache.get(tileServer, x, y, zoom);
                if (image == null) {
                    final String url = getTileString(tileServer, x, y, zoom);
                    try {
                        image = Toolkit.getDefaultToolkit().getImage(new URL(url));
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "failed to load url \"" + url + "\"", e);
                    }
                    if (image != null)
                        cache.put(tileServer, x, y, zoom, image);
                }
                if (image != null) {
                    g.drawImage(image, dx, dy, mapPanel);
                    imageDrawn = true;
                }
            }
            if (DEBUG && (!imageDrawn && (tileInBounds || DRAW_OUT_OF_BOUNDS))) {
                g.setColor(Color.blue);
                g.fillRect(dx + 4, dy + 4, TILE_SIZE - 8, TILE_SIZE - 8);
                g.setColor(Color.gray);
                String s = "T " + x + ", " + y + (!tileInBounds ? " #" : "");
                g.drawString(s, dx + 4+ 8, dy + 4 + 12);
            }
        }


    }

    private void paintInternal(Graphics2D g) {

        if (smoothPosition != null) {
            {
                Point position = getMapPosition();
                Painter painter = new Painter(this, getZoom());
                painter.paint(g, position, null);
            }
            Point position = new Point(smoothPosition.x, smoothPosition.y);
            Painter painter = new Painter(this, getZoom() + smoothOffset);
            painter.setScale(smoothScale);
            
            float t = (float) (animation == null ? 1f : 1 - animation.getFactor());
            painter.setTransparency(t);
            painter.paint(g, position, smoothPivot);
            if (animation != null && animation.getType() == AnimationType.ZOOM_IN) {
                int cx = smoothPivot.x, cy = smoothPivot.y;
                drawScaledRect(g, cx, cy, animation.getFactor(), 1 + animation.getFactor());
            } else if (animation != null && animation.getType() == AnimationType.ZOOM_OUT) {
                int cx = smoothPivot.x, cy = smoothPivot.y;
                drawScaledRect(g, cx, cy, animation.getFactor(), 2 - animation.getFactor());
            }
        }

        if (smoothPosition == null) {
            Point position = getMapPosition();
            Painter painter = new Painter(this, getZoom());
            painter.paint(g, position, null);
        }
        
        /** DO NOT REMOVE THIS CASE
         *	Since width/height of the panel initially is 0 before its painted,
         *	it appears to have the size "0". What was thought to be the center, 
         *	actually becomes the top left corner of the map. This case fixes that problem.
         * 	Though one should care for the possibility of an eternity loop as the maps
         * 	width and height are REALLY 0. This is also handled by checking if size is legit.
         **/
        if (centerPosition.equals(mapPosition) && legitMapSize()) {
        	setCenterPosition(centerPosition); 
        	paintInternal(g); /* calls itself, DANGEROUS. Could end up in eternal loop if setCenterPosition is buggy. */
        }
    }
    
    private boolean legitMapSize() {
    	return (PREFERRED_WIDTH != 0 && PREFERRED_HEIGHT != 0);
    }

    private void drawScaledRect(Graphics2D g, int cx, int cy, double f, double scale) {
        AffineTransform oldTransform = g.getTransform();
        g.translate(cx, cy);
        g.scale(scale, scale);
        g.translate(-cx, -cy);
        int c = 0x80 + (int) Math.floor(f * 0x60);
        if (c < 0) c = 0;
        else if (c > 255) c = 255;
        Color color = new Color(c, c, c);
        g.setColor(color);
        g.drawRect(cx - 40, cy - 30, 80, 60);
        g.setTransform(oldTransform);
    }

   //-------------------------------------------------------------------------
    // utils
    // TODO: The way of using static zoom and mapPosition-variables was probably a bad idea.
    // It will do for v1.0, but should get a more object-oriented design, so that one can
    // instantiate several maps if one wants to. This will however, result in a minor
    // architecture-design-rebuild.
    public static String format(double d) {
        return String.format("%.5f", d);
    }

    public static double getN(int y, int z) {
        double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
        return n;
    }

    public static double position2lon(int x, int z) {
        double xmax = TILE_SIZE * (1 << z);
        return x / xmax * 360.0 - 180;
    }

    public static double position2lat(int y, int z) {
        double ymax = TILE_SIZE * (1 << z);
        return Math.toDegrees(Math.atan(Math.sinh(Math.PI - (2.0 * Math.PI * y) / ymax)));
    }
    
    public double position2lon(int x) {
        return position2lon(x, zoom);
    }

    public double position2lat(int y) {
        return position2lat(y, zoom);
    }

    public static double tile2lon(int x, int z) {
        return x / Math.pow(2.0, z) * 360.0 - 180;
    }

    public static double tile2lat(int y, int z) {
        return Math.toDegrees(Math.atan(Math.sinh(Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z))));
    }

    public static int lon2position(double lon, int z) {
        double xmax = TILE_SIZE * (1 << z);
        return (int) Math.floor((lon + 180) / 360 * xmax);
    }

    public static int lat2position(double lat, int z) {
        double ymax = TILE_SIZE * (1 << z);
        return (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * ymax);
    }
    
    public static int lon2position(double lon) {
        return lon2position(lon, zoom);
    }

    public static int lat2position(double lat) {
        return lat2position(lat, zoom);
    }

    public static String getTileNumber(TileServer tileServer, double lat, double lon, int zoom) {
        int xtile = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
        int ytile = (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1 << zoom));
        return getTileString(tileServer, xtile, ytile, zoom);
    }

    private static void drawBackground(Graphics2D g, int width, int height) {
        Color color1 = Color.black;
        Color color2 = new Color(0x30, 0x30, 0x30);
        color1 = new Color(0xc0, 0xc0, 0xc0);
        color2 = new Color(0xe0, 0xe0, 0xe0);
        Composite oldComposite = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f));
        g.setPaint(new GradientPaint(0, 0, color1, 0, height, color2));
        g.fillRoundRect(0, 0, width, height, 4, 4);
        g.setComposite(oldComposite);
    }

    private static void drawRollover(Graphics2D g, int width, int height) {
        Color color1 = Color.white;
        Color color2 = new Color(0xc0, 0xc0, 0xc0);
        Composite oldComposite = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.25f));
        g.setPaint(new GradientPaint(0, 0, color1, width, height, color2));
        g.fillRoundRect(0, 0, width, height, 4, 4);
        g.setComposite(oldComposite);
    }

    private static BufferedImage flip(BufferedImage image, boolean horizontal, boolean vertical) {
        int width = image.getWidth(), height = image.getHeight();
        if (horizontal) {
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width / 2; ++x) {
                    int tmp = image.getRGB(x, y);
                    image.setRGB(x, y, image.getRGB(width - 1 - x, y));
                    image.setRGB(width - 1 - x, y, tmp);
                }
            }
        }
        if (vertical) {
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height / 2; ++y) {
                    int tmp = image.getRGB(x, y);
                    image.setRGB(x, y, image.getRGB(x, height - 1 - y));
                    image.setRGB(x, height - 1 - y, tmp);
                }
            }
        }
        return image;
    }

    private static BufferedImage makeIcon(Color background) {
        final int WIDTH = 16, HEIGHT = 16;
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < HEIGHT; ++y)
            for (int x = 0; x < WIDTH; ++x)
                image.setRGB(x, y, 0);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(background);
        g2d.fillOval(0, 0, WIDTH - 1, HEIGHT - 1);

        double hx = 4;
        double hy = 4;
        for (int y = 0; y < HEIGHT; ++y) {
            for (int x = 0; x < WIDTH; ++x) {
              double dx = x - hx;
              double dy = y - hy;
              double dist = Math.sqrt(dx * dx + dy * dy);
              if (dist > WIDTH) {
                 dist = WIDTH;
              }
              int color = image.getRGB(x, y);
              int a = (color >>> 24) & 0xff;
              int r = (color >>> 16) & 0xff;
              int g = (color >>> 8) & 0xff;
              int b = (color >>> 0) & 0xff;
              double coef = 0.7 - 0.7 * dist / WIDTH;
              image.setRGB(x, y, (a << 24) | ((int) (r + coef * (255 - r)) << 16) | ((int) (g + coef * (255 - g)) << 8) | (int) (b + coef * (255 - b)));
           }
        }
        g2d.setColor(Color.gray);
        g2d.drawOval(0, 0, WIDTH - 1, HEIGHT - 1);
        return image;
    }

    private static BufferedImage makeXArrow(Color background) {
        BufferedImage image = makeIcon(background);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillPolygon(new int[] { 10, 4, 10} , new int[] { 5, 8, 11 }, 3);
        image.flush();
        return image;

    }
    private static BufferedImage makeYArrow(Color background) {
        BufferedImage image = makeIcon(background);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillPolygon(new int[] { 5, 8, 11} , new int[] { 10, 4, 10 }, 3);
        image.flush();
        return image;
    }
    private static BufferedImage makePlus(Color background) {
        BufferedImage image = makeIcon(background);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillRect(4, 7, 8, 2);
        g.fillRect(7, 4, 2, 8);
        image.flush();
        return image;
    }
    private static BufferedImage makeMinus(Color background) {
        BufferedImage image = makeIcon(background);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillRect(4, 7, 8, 2);
        image.flush();
        return image;
    }


    //-------------------------------------------------------------------------
    // helpers
    private enum AnimationType {
        ZOOM_IN, ZOOM_OUT
    }
    
    private static abstract class Animation implements ActionListener {

        private final AnimationType type;
        private final Timer timer; 
        private long t0 = -1L;
        private long dt;
        private final long duration;

        public Animation(AnimationType type, int fps, long duration) {
            this.type = type;
            this.duration = duration;
            int delay = 1000 / fps;
            timer = new Timer(delay, this);
            timer.setCoalesce(true);
            timer.setInitialDelay(0);
        }
        
        public AnimationType getType() {
            return type;
        }

        protected abstract void onComplete();

        protected abstract void onFrame();

        public double getFactor() {
            return (double) getDt() / getDuration();
        }

        public void actionPerformed(ActionEvent e) {
            if (getDt() >= duration) {
                kill();
                onComplete();
                return;
            }
            onFrame();
        }

        public long getDuration() {
            return duration;
        }

        public long getDt() {
            if (!timer.isRunning())
                return dt;
            long now = System.currentTimeMillis();
            if (t0 < 0)
                t0 = now;
            return now - t0 + dt;
        }

        public void run() {
            if (timer.isRunning())
                return;
            timer.start();
        }

        public void kill() {
            if (!timer.isRunning())
                return;
            dt = getDt();
            timer.stop();
        }
    }

    private static class Tile {
        private final String key;
        public final int x, y, z;
        public Tile(String tileServer, int x, int y, int z) {
            this.key = tileServer;
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            result = prime * result + x;
            result = prime * result + y;
            result = prime * result + z;
            return result;
        }
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Tile other = (Tile) obj;
            if (key == null) {
                if (other.key != null)
                    return false;
            } else if (!key.equals(other.key))
                return false;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            if (z != other.z)
                return false;
            return true;
        }

    }

    private static class TileCache {
        private LinkedHashMap<Tile,Image> map = new LinkedHashMap<Tile,Image>(CACHE_SIZE, 0.75f, true) {
            protected boolean removeEldestEntry(java.util.Map.Entry<Tile,Image> eldest) {
                boolean remove = size() > CACHE_SIZE;
                return remove;
            }
        };
        
        public void put(TileServer tileServer, int x, int y, int z, Image image) {
            map.put(new Tile(tileServer.getURL(), x, y, z), image);
        }
        
        public Image get(TileServer tileServer, int x, int y, int z) {
            Image image = map.get(new Tile(tileServer.getURL(), x, y, z));
            return image;
        }
        
        @SuppressWarnings("unused")
        public int getSize() {
            return map.size();
        }
    }
    
	public static class CustomSplitPane extends JComponent  {
        private static final int SPACER_SIZE = 4;
        private final boolean horizonal;
        private final JComponent spacer;
        
        private double split = 0.5;
        private int dx, dy;
        private Component componentOne, componentTwo;
        
        public CustomSplitPane(boolean horizonal) {
            this.spacer = new JPanel();
            this.spacer.setOpaque(false);
            this.spacer.setCursor(horizonal ? Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR) : Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
            this.dx = this.dy = -1;
            this.horizonal = horizonal;
            
            /* because of jdk1.5, javafx */
            class SpacerMouseAdapter extends MouseAdapter implements MouseMotionListener {
                public void mouseReleased(MouseEvent e) {
                    Insets insets = getInsets();
                    int width = getWidth();
                    int height = getHeight();
                    int availw = width - insets.left - insets.right;
                    int availh = height - insets.top - insets.bottom;
                    if (CustomSplitPane.this.horizonal && dy != -1) {
                        setSplit((double) dx / availw);
                    } else if (dx != -1) {
                        setSplit((double) dy / availh);
                    }
                    dx = dy = -1;
                    spacer.setOpaque(false);
                    repaint();
                }
                
                public void mouseDragged(MouseEvent e) {
                    dx = e.getX() + spacer.getX(); 
                    dy = e.getY() + spacer.getY();
                    spacer.setOpaque(true);
                    if (dx != -1 && CustomSplitPane.this.horizonal) {
                        spacer.setBounds(dx, 0, SPACER_SIZE, getHeight());
                    } else if (dy != -1 && !CustomSplitPane.this.horizonal) {
                        spacer.setBounds(0, dy, getWidth(), SPACER_SIZE);
                    }
                    repaint();
                }
                
                public void mouseMoved(MouseEvent e) {
                }
            };
            SpacerMouseAdapter mouseAdapter = new SpacerMouseAdapter();
            spacer.addMouseListener(mouseAdapter);
            spacer.addMouseMotionListener(mouseAdapter);
            
            setLayout(new LayoutManager() {
                public void addLayoutComponent(String name, Component comp) {
                }
                
                public void removeLayoutComponent(Component comp) {
                }
                
                public Dimension minimumLayoutSize(Container parent) {
                    return new Dimension(1, 1);
                }
                
                public Dimension preferredLayoutSize(Container parent) {
                    return new Dimension(128, 128);
                }
                
                public void layoutContainer(Container parent) {
                    Insets insets = parent.getInsets();
                    int width = parent.getWidth();
                    int height = parent.getHeight();
                    int availw = width - insets.left - insets.right;
                    int availh = height - insets.top - insets.bottom;
                    
                    if (CustomSplitPane.this.horizonal) {
                        availw -= SPACER_SIZE;
                        int width1 = Math.max(0, (int) Math.floor(split * availw));
                        int width2 = Math.max(0, availw - width1);
                        if (componentOne.isVisible() && !componentTwo.isVisible()) {
                            spacer.setBounds(0, 0, 0, 0);
                            componentOne.setBounds(insets.left, insets.top, availw, availh);
                        } else if (!componentOne.isVisible() && componentTwo.isVisible()) {
                            spacer.setBounds(0, 0, 0, 0);
                            componentTwo.setBounds(insets.left, insets.top, availw, availh);
                        } else {
                            spacer.setBounds(insets.left + width1, insets.top, SPACER_SIZE, availh);
                            componentOne.setBounds(insets.left, insets.top, width1, availh);
                            componentTwo.setBounds(insets.left + width1 + SPACER_SIZE, insets.top, width2, availh);
                        }
                    } else {
                        availh -= SPACER_SIZE;
                        int height1 = Math.max(0, (int) Math.floor(split * availh));
                        int height2 = Math.max(0, availh - height1);
                        if (componentOne.isVisible() && !componentTwo.isVisible()) {
                            spacer.setBounds(0, 0, 0, 0);
                            componentOne.setBounds(insets.left, insets.top, availw, availh);
                        } else if (!componentOne.isVisible() && componentTwo.isVisible()) {
                            spacer.setBounds(0, 0, 0, 0);
                            componentTwo.setBounds(insets.left, insets.top, availw, availh);
                        } else {
                            spacer.setBounds(insets.left, insets.top + height1, availw, SPACER_SIZE);
                            componentOne.setBounds(insets.left, insets.top, availw, height1);
                            componentTwo.setBounds(insets.left, insets.top + height1 + SPACER_SIZE, availw, height2);
                        }
                    }
                }
            });
            add(spacer);
        }
        
        public double getSplit() {
            return split;
        }
        
        public void setSplit(double split) {
            if (split < 0)
                split = 0;
            else if (split > 1)
                split = 1;
            this.split = split;
            invalidate();
            validate();
        }
        
        public void setComponentOne(Component component) {
            this.componentOne = component;
            if (componentOne != null)
                add(componentOne);
        }

        public void setComponentTwo(Component component) {
            this.componentTwo = component;
            if (componentTwo != null)
                add(componentTwo);
        }
    }
    
    private class DragListener extends MouseAdapter implements MouseMotionListener, MouseWheelListener {
        private Point mouseCoords;
        private Point downCoords;
        private Point downPosition;

        public DragListener() {
            mouseCoords = new Point();
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() >= 2) {
                zoomInAnimated(new Point(mouseCoords.x, mouseCoords.y));
            } else if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() >= 2) {
                zoomOutAnimated(new Point(mouseCoords.x, mouseCoords.y));
            } else if (e.getButton() == MouseEvent.BUTTON2) {
                setCenterPosition(getCursorPosition());
                repaint();
            }
        }

        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                downCoords = e.getPoint();
                downPosition = getMapPosition();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                int cx = getCursorPosition().x;
                int cy = getCursorPosition().y;
                magnifyRegion = new Rectangle(cx - MAGNIFIER_SIZE / 2, cy - MAGNIFIER_SIZE / 2, MAGNIFIER_SIZE, MAGNIFIER_SIZE);
                repaint();
            }
        }

        public void mouseReleased(MouseEvent e) {
            handleDrag(e);
            downCoords = null;
            downPosition = null;
            magnifyRegion = null;
        }

        public void mouseMoved(MouseEvent e) {
            handlePosition(e);
        }

        public void mouseDragged(MouseEvent e) {
            handlePosition(e);
            handleDrag(e);
        }

        public void mouseExited(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        public void mouseEntered(MouseEvent me) {
            super.mouseEntered(me);
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }

        private void handlePosition(MouseEvent e) {
            mouseCoords = e.getPoint();
            MapPanel.this.repaint();
        }

        private void handleDrag(MouseEvent e) {
        	if (!interactionEnabled) {
        		/* interaction disabled */
        	} else if (downCoords != null) {
                int tx = downCoords.x - e.getX();
                int ty = downCoords.y - e.getY();
                setMapPosition(downPosition.x + tx, downPosition.y + ty);
                repaint();
            } else if (magnifyRegion != null) {
                int cx = getCursorPosition().x;
                int cy = getCursorPosition().y;
                magnifyRegion = new Rectangle(cx - MAGNIFIER_SIZE / 2, cy - MAGNIFIER_SIZE / 2, MAGNIFIER_SIZE, MAGNIFIER_SIZE);
                repaint();
            }
        }

        public void mouseWheelMoved(MouseWheelEvent e) {
            int rotation = e.getWheelRotation();
            if (!interactionEnabled) {
            	/* interaction disabled */
            } else if (rotation < 0) {
                zoomInAnimated(new Point(mouseCoords.x, mouseCoords.y));
            } else {
                zoomOutAnimated(new Point(mouseCoords.x, mouseCoords.y));
            }
        }
    }

	public final class ControlPanel extends JPanel {

        protected static final int MOVE_STEP = 32;

        private JButton makeButton(Action action) {
            JButton b = new JButton(action);
            b.setFocusable(false);
            b.setText(null);
            b.setContentAreaFilled(false);
            b.setBorder(BorderFactory.createEmptyBorder());
            BufferedImage image = (BufferedImage) ((ImageIcon)b.getIcon()).getImage();
            BufferedImage hl = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) hl.getGraphics();
            g.drawImage(image, 0, 0, null);
            drawRollover(g, hl.getWidth(), hl.getHeight());
            hl.flush();
            b.setRolloverIcon(new ImageIcon(hl));
            return b;
        }

        public ControlPanel() {
            setOpaque(false);
            setForeground(Color.white);
            setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
            setLayout(new BorderLayout());

            Action zoomInAction = new AbstractAction() {
                {
                    String text = "Zoom In";
                    putValue(Action.NAME, text);
                    putValue(Action.SHORT_DESCRIPTION, text);
                    putValue(Action.SMALL_ICON, new ImageIcon(flip(makePlus(new Color(0xc0, 0xc0, 0xc0)), false, false)));
                }

                public void actionPerformed(ActionEvent e) {
                    zoomInAnimated(new Point(MapPanel.this.getWidth() / 2, MapPanel.this.getHeight() / 2));
                }
            };
            Action zoomOutAction = new AbstractAction() {
                {
                    String text = "Zoom Out";
                    putValue(Action.NAME, text);
                    putValue(Action.SHORT_DESCRIPTION, text);
                    putValue(Action.SMALL_ICON, new ImageIcon(flip(makeMinus(new Color(0xc0, 0xc0, 0xc0)), false, false)));
                }

                public void actionPerformed(ActionEvent e) {
                    zoomOutAnimated(new Point(MapPanel.this.getWidth() / 2, MapPanel.this.getHeight() / 2));
                }
            };

            Action upAction = new AbstractAction() {
                {
                    String text = "Up";
                    putValue(Action.NAME, text);
                    putValue(Action.SHORT_DESCRIPTION, text);
                    putValue(Action.SMALL_ICON, new ImageIcon(flip(makeYArrow(new Color(0xc0, 0xc0, 0xc0)), false, false)));
                }

                public void actionPerformed(ActionEvent e) {
                    translateMapPosition(0, -MOVE_STEP);
                    MapPanel.this.repaint();
                }
            };
            Action downAction = new AbstractAction() {
                {
                    String text = "Down";
                    putValue(Action.NAME, text);
                    putValue(Action.SHORT_DESCRIPTION, text);
                    putValue(Action.SMALL_ICON, new ImageIcon(flip(makeYArrow(new Color(0xc0, 0xc0, 0xc0)), false, true)));
                }

                public void actionPerformed(ActionEvent e) {
                    translateMapPosition(0, +MOVE_STEP);
                    MapPanel.this.repaint();
                }
            };
            Action leftAction = new AbstractAction() {
                {
                    String text = "Left";
                    putValue(Action.NAME, text);
                    putValue(Action.SHORT_DESCRIPTION, text);
                    putValue(Action.SMALL_ICON, new ImageIcon(flip(makeXArrow(new Color(0xc0, 0xc0, 0xc0)), false, false)));
                }

                public void actionPerformed(ActionEvent e) {
                    translateMapPosition(-MOVE_STEP, 0);
                    MapPanel.this.repaint();
                }
            };
            Action rightAction = new AbstractAction() {
                {
                    String text = "Right";
                    putValue(Action.NAME, text);
                    putValue(Action.SHORT_DESCRIPTION, text);
                    putValue(Action.SMALL_ICON, new ImageIcon(flip(makeXArrow(new Color(0xc0, 0xc0, 0xc0)), true, false)));
                }

                public void actionPerformed(ActionEvent e) {
                    translateMapPosition(+MOVE_STEP, 0);
                    MapPanel.this.repaint();
                }
            };
            JPanel moves = new JPanel(new BorderLayout());
            moves.setOpaque(false);
            JPanel zooms = new JPanel(new BorderLayout(0, 0));
            zooms.setOpaque(false);
            zooms.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
            moves.add(makeButton(upAction), BorderLayout.NORTH);
            moves.add(makeButton(leftAction), BorderLayout.WEST);
            moves.add(makeButton(downAction), BorderLayout.SOUTH);
            moves.add(makeButton(rightAction), BorderLayout.EAST);
            zooms.add(makeButton(zoomInAction), BorderLayout.NORTH);
            zooms.add(makeButton(zoomOutAction), BorderLayout.SOUTH);
            add(moves, BorderLayout.NORTH);
            add(zooms, BorderLayout.SOUTH);
        }

        public void paint(Graphics gOrig) {
            Graphics2D g = (Graphics2D) gOrig.create();
            try {
                int w = getWidth(), h = getHeight();
                drawBackground(g, w, h);
            } finally {
                g.dispose();
            }
            super.paint(gOrig);
        }
    }


    private final class MapLayout implements LayoutManager {

        public void addLayoutComponent(String name, Component comp) {
        }
        public void removeLayoutComponent(Component comp) {
        }
        public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(1, 1);
        }
        public Dimension preferredLayoutSize(Container parent) {
            return new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        }
        public void layoutContainer(Container parent) {
            {
                Dimension psize = controlPanel.getPreferredSize();
                controlPanel.setBounds(20, 20, psize.width, psize.height);
            }
        }
    }
}


