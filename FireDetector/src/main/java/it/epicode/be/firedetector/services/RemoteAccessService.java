package it.epicode.be.firedetector.services;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.springframework.stereotype.Service;
import it.epicode.be.firedetector.models.Probe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteAccessService {

	private String credentials = "{\"username\": \"CirceoAlarm\", \"password\": \"circeo123\"}";
	private String login = "http://localhost:1515/auth/login";
	private String alarm = "http://localhost:1515/alarm";
	private static String token = "";

	public String getToken() throws NullPointerException {
		String response = connect(login, credentials);
		int x = response.indexOf("token");
		response = response.substring(x + 8);
		int y = response.indexOf("\",\"");
		return response.substring(0, y);
	}

	public void emergencyAlert(Probe p) {
		try {
			if (token == "") token = getToken();
			String alarmUrl = alarm + "?idsonda=" + p.getId() + "&lat=" + parse(p.getLatitude().toString()) 
				+ "&lon=" + parse(p.getLongitude().toString()) + "&smokelevel=" + p.getDanger();
			log.info(connect(alarmUrl, p.toString()));
		} catch (NullPointerException e) {
			log.info("Senza token non è possibile comunicare lo stato d'Emergenza");
		}
	}

	public String parse(String coordinate) {
		String s = coordinate;
		s = s.replace("°", "g");
		s = s.replace("'", "m");
		return s.replace("\"", "s");
	}

	public String connect(String url, String body) {
		String response = null;
		try {
			URL x = new URL(url);
			HttpURLConnection httpCon = (HttpURLConnection) x.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Accept", "application/json");
			if (token != "")
				httpCon.setRequestProperty("Authorization", "Bearer " + token);
			OutputStream os = httpCon.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			osw.write(body);
			osw.flush();
			osw.close();
			os.close();
			httpCon.connect();
			BufferedInputStream bis = new BufferedInputStream(httpCon.getInputStream());
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result2 = bis.read();
			while (result2 != -1) {
				buf.write((byte) result2);
				result2 = bis.read();
			}
			response = buf.toString();
		} catch (MalformedURLException e) {
			log.info("L'indirizzo immesso non sembra essere corretto.");
		} catch (ProtocolException e) {
			log.info("Il protocollo utilizzato non viene accettato.");
		} catch (UnsupportedEncodingException e) {
			log.info("Il parametro di codifica impostato non è supportato");
		} catch (IOException e) {
			log.info("Non è stato possibile portare a termine la chiamata http.");
		} catch (IllegalStateException e) {
			log.info("Non è stato possibile avviare la chiamata.");
		} catch (NullPointerException e) {
			log.info("Non è stato possibile leggere qualche dato fornito.");
		}
		return response;
	}
}
