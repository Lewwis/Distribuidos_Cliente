import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonManager {
	
	private static String getString(JSONObject _jobj) {
		return _jobj.toString();
	}
	
	private static JSONObject setJson(String _txtJson) {
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(_txtJson);
			return (JSONObject) obj;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public static String prepareJson(int _type, String _user,String[] _destino, String _txt) {
		JSONObject jobj = new JSONObject();
		
		jobj.put("Tipo", _type);
		jobj.put("Origen", _user);
		jobj.put("Destino", _destino);
		jobj.put("Datos", _txt);
		
		return jobj.toJSONString();
	}
	
	public static int decodeJson() {
		return 0;
	}
	
}
