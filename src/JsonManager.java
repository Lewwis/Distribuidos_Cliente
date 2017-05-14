import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonManager {
	
	// Convierte la entrada a un objeto tipo JSON
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
	
	// Devuelve un String en el formato del JSON al recibir los datos requeridos
	@SuppressWarnings("unchecked")
	public static String codeJson(int _type, String _user,String _destino, String _txt) {
		JSONObject jobj = new JSONObject();
		
		jobj.put("Tipo", _type);
		jobj.put("Origen", _user);
		jobj.put("Destino", _destino);
		jobj.put("Datos", _txt);
		
		return jobj.toJSONString();
	}
	
	// Devuelve el tipo de dato del JSON
	public static int getType(String _txtJson) {
		JSONObject jobj = setJson(_txtJson);
		return (int) jobj.get("Tipo");
	}
	
	// Devuelve el valor de la llave
	public static String getByKey(String _key, String _txtJson) {
		JSONObject jobj = setJson(_txtJson);
		return (String) jobj.get(_key);
	}
	
}
