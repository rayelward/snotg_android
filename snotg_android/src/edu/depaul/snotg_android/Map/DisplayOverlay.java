package edu.depaul.snotg_android.Map;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;


public class DisplayOverlay extends Overlay {
	
	private Paint paint;
	private double lat;
	private double lon;
	private double satAccuracy;
	private int numberSats;
	private float bearing;
	private double altitude;
	private float speed;
	private String currentProvider;
	public static boolean showData = true;
	
	//private final Context context;
	//Bitmap _scratch = BitmapFactory.decodeResource(getResources(), R.drawable.icon);

	
	@Override
	public void draw(Canvas canvas, MapView mapview, boolean shadow){
		super.draw(canvas, mapview, shadow);
		if(showData){
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setARGB(80,255,255,255);
			canvas.drawRect(0,0,350,33,paint);
			paint.setTextSize(11);
			paint.setARGB(180,0,0,0);
			canvas.drawText("Lat = "+lat+"  Long = "+lon+"  Alt = "+(int)altitude+" m", 8, 14, paint);
			canvas.drawText("Sat = "+numberSats+" Accur = "+(int)satAccuracy+" m"
                    +" speed = "+(int)speed+" m/s  bearing = "+(int)bearing+" deg", 8, 27, paint);
			canvas.drawText("Depaul Coordinates to USE = "+"41.9249247"+"  , = "+"-87.6550303", 8, 41, paint);
		}
	}
	
	// Method to insert sat data onto Map
	public void putSatStuff(double lat, double lon, double satAccuracy, float bearing, double altitude, float speed, String currentProvider, int numberSats){
		this.lat = lat;
		this.lon = lon;
		this.satAccuracy = satAccuracy;
		this.bearing = bearing;
		this.altitude = altitude;
		this.speed = speed;
		this.currentProvider = currentProvider;
		this.numberSats = numberSats;
	}
	
}
