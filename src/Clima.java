import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Clima {

	public static String getClima() {
		try {
			String url = "http://api.wunderground.com/api/e53a2061f49ff1c9/conditions/q/MX/Tlaquepaque.json";
			URLConnection connection = new URL(url).openConnection();
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			InputStream r = connection.getInputStream();
			
			StringWriter writer = new StringWriter();
			IOUtils.copy(r, writer, "UTF-8");
			String string = writer.toString();
			
			JSONObject auxObj = (JSONObject) new JSONParser().parse(string);
			auxObj = (JSONObject) auxObj.get("current_observation");
			
			String txt = (String) auxObj.get("temperature_string");
			txt += " " + (String) auxObj.get("weather");
			
			return txt;	
		} catch (Exception e) {
			System.out.println(e);
		}
		return "ERROR";
	}
	
}
