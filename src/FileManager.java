import java.io.File;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class FileManager {

	public static String fileToSend(String _fileName) {
		try {
			File file = new File(_fileName);
			return FileUtils.readFileToString(file,"US-ASCII");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return "ERROR";
	}
	
	public static void fileToRecive(String _fileName, String _content) {
		try {
			File path = new File(_fileName);
			InputStream in = IOUtils.toInputStream(_content,"US-ASCII");
			FileUtils.copyInputStreamToFile(in, path);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
