package decoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Decoder {
	public static String decode(String url) throws UnsupportedEncodingException, ParseException {
		String urlDecode = URLDecoder.decode(url, "UTF-8");
		String pattern = "(749@@@100118):(.*)###";
		Pattern r = Pattern.compile(pattern);
		
		Matcher match = r.matcher(urlDecode);
		JSONParser parse = new JSONParser();
		JSONObject json = new JSONObject();
		
		if(match.find()) {
			String str = match.group(2); // group2 is list links, check System.out.println.(match.group(2))
			
			json = (JSONObject) parse.parse(str);
			@SuppressWarnings("unchecked")
			List<String> listLinks = (List<String>) json.get("links");
			
			String result = "";
			if(listLinks != null) { // if json parse listLinks is null
				for(int i=0; i<listLinks.size()-2; i++) {
					result += listLinks.get(i) + "&&&";
				}
				
				result += listLinks.get(listLinks.size()-1);
			}
			
			return result;
		} else {
			return ""; // if not matching
		}
	}
}
