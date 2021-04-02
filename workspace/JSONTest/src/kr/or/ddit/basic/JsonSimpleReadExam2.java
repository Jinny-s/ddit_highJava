package kr.or.ddit.basic;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 공공데이터 포털의 OPEN API
 * (안심식당 정보 가져오기)
 * @author jinny
 *
 */
public class JsonSimpleReadExam2 {
	private String svcKey = "Grid_20200713000000000605_1";  // 안심식당 정보 조회 서비스
	private String apiKey = "e138a4298d07a531676a1b25310ca933eb9afc80981d366fab60c64841eb6464"; // 개인별 발급.
	private String startIdx = "1";  		// 요청시작위치
	private String endIdx = "100";			// 요청종료위치
	private String relaxZipcode = "908000";	// 시도코드

	/**
	 * JSON
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private JSONObject getJSONObject() throws IOException, ParseException {
		URL url = new URL("http://211.237.50.150:7080/openapi/"+ apiKey
				+ "/json/"+ svcKey + "/" + startIdx  + "/" + endIdx
				+ "?RELAX_ZIPCODE=" + relaxZipcode);
		URLConnection urlConnection = url.openConnection();
		
//		System.out.println(url.toString());
		JSONParser parser = new JSONParser();

		Object obj = parser.parse(new InputStreamReader(urlConnection.getInputStream()));

		return (JSONObject)obj;
	}

	/**
	 * 시작
	 * @throws IOException
	 * @throws ParseException
	 */
	public void start() throws IOException, ParseException {


		JSONObject jsonfile = getJSONObject();

		JSONObject rootObj = (JSONObject) jsonfile.get(svcKey);

		long totalCnt = (long)rootObj.get("totalCnt"); // 전체 안심식당 수

		endIdx = totalCnt + ""; // // 전체 안심식당 수를 endIdx로 반환
		//-----------------------------------------------------------------------

		jsonfile = getJSONObject();

		rootObj = (JSONObject) jsonfile.get(svcKey);

		JSONObject result = (JSONObject)rootObj.get("result");
		String code = (String)result.get("code");

		if(code.equals("INFO-000")) { // 정상 상태

			JSONArray list = (JSONArray)rootObj.get("row");

			@SuppressWarnings("unchecked")
			Iterator<JSONObject> it = list.iterator();

			while(it.hasNext()){

				JSONObject tempJson = it.next();

				System.out.println("안심식당SEQ : " + tempJson.get("RELAX_SEQ"));
				System.out.println("사업장명 : " + tempJson.get("RELAX_RSTRNT_NM"));
				System.out.println("시도명 : " + tempJson.get("RELAX_SI_NM"));
				System.out.println("업종 : " + tempJson.get("RELAX_GUBUN"));
				System.out.println("업종상세 : " + tempJson.get("RELAX_GUBUN_DETAIL"));

				System.out.println("--------------------------------------------------");
			}
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		new JsonSimpleReadExam2().start();
	}
}
