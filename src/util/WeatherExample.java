package util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class WeatherExample {
    public static void main(String[] args) {
        final String API = System.getenv("API_KEY");
        String city = "Seoul";
        final String URL = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API + "&units=metric";

        try {
            // URL 객체 생성
            URL url = new URL(URL);
            // URL 과 연결
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // 정상 연결이 되었는지 확인(status == 200)
            if (connection.getResponseCode() == 200) {
                StringBuffer stringBuffer = new StringBuffer();
                String str;
                // InputStream을 문자 스트림으로 변환 후 BufferedReader 보조 스트림에 연결
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()));
                // 한 줄씩 읽어 StringBuffer에 추가
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                // JSON 파싱
                JsonObject data = JsonParser.parseString(stringBuffer.toString()).getAsJsonObject();
                JsonObject main = data.getAsJsonObject("main");
                String temp = main.getAsJsonPrimitive("temp").getAsString();

                // 소수점 첫 번째 자리까지 출력하기 위해 반올림
                double temp_double  = (Math.round(Double.parseDouble(temp) * 10) / 10.0);

                // 서울의 온도 출력
                System.out.println(city + "의 온도는 : " + temp_double);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
