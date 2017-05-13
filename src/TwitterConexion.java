import javax.swing.JOptionPane;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConexion {

	// Realiza coneccion con Twitter
	public static void tweet(String _mensaje) {
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setOAuthConsumerKey("11HS7FSdergNW6JLFlkjZHumY");
			cb.setOAuthConsumerSecret("EQ9nObOZUnVkUh5XbuRKjF96c31K2tQ47LY1KVdziKCvuIYLF3");
			cb.setOAuthAccessToken("98982184-nxXsFvWZhpsUwAPNpD7WxfjcCY71QgXvIltf585Ss");
			cb.setOAuthAccessTokenSecret("L2ZiLxgYKfrgrAuel7CAm2oIy0rOBlucLjuwrS0QJNqzg");
			
			TwitterFactory  tf = new TwitterFactory(cb.build());
			Twitter t = tf.getInstance();
			System.out.println(t.getOAuthAccessToken());
			@SuppressWarnings("unused")
			Status s = t.updateStatus(_mensaje);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se ha podido realizar la ultima accion");
			System.out.println(e);
		}
	}
	
}
