package witcomram.thaitour;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.Resources.NotFoundException;

public class RawPullParser {
	
	private Item currentTour  = null;
	private String currentTag = null;
	List<Item> tours = new ArrayList<>();

	public List<Item> parseXML(Context context) {

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			
			InputStream stream = context.getResources().openRawResource(R.raw.tour);
			xpp.setInput(stream, null);

			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					handleStartTag(xpp.getName());
				} else if (eventType == XmlPullParser.END_TAG) {
					currentTag = null;
				} else if (eventType == XmlPullParser.TEXT) {
					handleText(xpp.getText());
				}
				eventType = xpp.next();
			}

		} catch (NotFoundException e) {
		} catch (XmlPullParserException e) {
		} catch (IOException e) {
		}

		return tours;
	}

	private void handleText(String text) {
		String xmlText = text;
		if (currentTour != null && currentTag != null) {
			if (currentTag.equals("id")) {
				Integer id = Integer.parseInt(xmlText);
				currentTour.setId(id);
			} 
			else if (currentTag.equals("title")) {
				currentTour.setTitle(xmlText);
			}
			else if (currentTag.equals("description")) {
				currentTour.setDescription(xmlText);
			}
			else if (currentTag.equals("city")) {
				currentTour.setCity(xmlText);
			}
			else if (currentTag.equals("image")) {
				currentTour.setImage(xmlText);
			}
			else if (currentTag.equals("choice")) {
				int choice = Integer.parseInt(xmlText);
				currentTour.setChoice(choice);
			}
			else if (currentTag.equals("latitude")) {
				double latitude = Double.parseDouble(xmlText);
				currentTour.setLatitude(latitude);
			}
			else if (currentTag.equals("longitude")) {
				double longitude = Double.parseDouble(xmlText);
				currentTour.setLongitude(longitude);
			}
		}
	}

	private void handleStartTag(String name) {
		if (name.equals("tour")) {
			currentTour = new Item();
			tours.add(currentTour);
		}
		else {
			currentTag = name;
		}
	}
}
