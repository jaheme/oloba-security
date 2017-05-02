import com.oloba.core.http.JettyServer;

public class OlobaMain extends JettyServer {

	public static void main(String[] args) {
		System.setProperty("org.apache.jasper.compiler.disablejsr199", "false");
		OlobaMain m = new OlobaMain();
		m.setPort(Integer.parseInt("8500"));
		m.addWebappPath("oloba-security/src/main/webapp");
		m.setContextPath("/");
		m.start();

	}

}
