package web.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.sun.research.ws.wadl.Response;

public class CustomerRestClient {

	private static final String webServiceURI = "http://localhost:8080/Test/rest/";
	
	public static void main(String[] args) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		URI serviceUri = UriBuilder.fromUri(webServiceURI).build();
		WebTarget target = client.target(serviceUri);
		
		System.out.println(target.path("customer").path("test").request()
				.accept(MediaType.TEXT_PLAIN).get(Response.class).toString());
		
		System.out.println(target.path("customer").path("test").request()
				.accept(MediaType.TEXT_PLAIN).get(Response.class));
	}
	
}
