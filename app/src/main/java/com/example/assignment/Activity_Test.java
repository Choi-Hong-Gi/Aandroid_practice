package com.example.assignment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.io.IOException;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class Activity_Test extends Activity {

    public class OpenAPI extends AsyncTask<Void, Void, String> {
        private String url;

        public OpenAPI(String url) {
            this.url = url;
        }

        @Override
        public String doInBackground(Void... params) {
            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            try {
                dBuilder = dbFactoty.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            try {
                doc = dBuilder.parse(url);
            } catch (IOException | SAXException e) {
                e.printStackTrace();
            }

            // root tag
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: result

            // parsersing tag
            NodeList nList = doc.getElementsByTagName("item");

            String realResult = "";

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    //Log.d("OPEN_API","baseDate: " + getTagValue("baseDate", eElement));
                    //Log.d("OPEN_API","baseTime: " + getTagValue("baseTime", eElement));
                    Log.d("OPEN_API", "category: " + getTagValue("category", eElement));
                    Log.d("OPEN_API", "obsrValue: " + getTagValue("obsrValue", eElement));
                    realResult = realResult + getTagValue("category", eElement).toString() + " : " + getTagValue("obsrValue", eElement).toString() + "\n\n";
                }    // if end
            }    // for end
            //System.out.println(realResult);
            return realResult;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
        }
    }

    private String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView tvTEST = (TextView) findViewById(R.id.textViewTEST);
        Button btnRefresh = (Button) findViewById(R.id.btnRefresh);
        Button btnEndTEST = (Button) findViewById(R.id.btnEndTEST);

        SimpleDateFormat real_time = new SimpleDateFormat("yyyyMMdd");
        Date time = new Date();
        String base_date = real_time.format(time);

        LocalTime now = LocalTime.now();

        String Hour = Integer.toString(now.getHour() - 1);
        String Minute = Integer.toString(now.getMinute());
        String base_time = Hour + Minute;

        if (base_time.length() != 4)
            base_time = "0" + base_time;

        final String base_time_f = base_time;


        String serviceKey = "=5LMImuDe4KYWVHN%2BC8ZiRhKnnboWUFJjia05xmbVjuw79Gv5upIZD%2BB03TgGptciKHQAYkX5TNgTptdIAqJYPg%3D%3D";
        String URL_weather = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey" +
                serviceKey + "&pageNo=1&numOfRows=1000&dataType=XML&base_date=" + base_date + "&base_time=" + base_time_f + "&nx=68&ny=109";


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                OpenAPI dust = new OpenAPI(URL_weather);
                dust.execute();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String RR = dust.doInBackground();
                        RR = RR.replace("PTY", "강수형태");
                        //없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7)
                        RR = RR.replace("REH", "습도(%)");
                        RR = RR.replace("RN1", "시간당 강수량(mm)");
                        RR = RR.replace("T1H", "기온(℃)");
                        RR = RR.replace("UUU", "동서풍(m/s)");
                        RR = RR.replace("VEC", "풍향(deg)");
                        RR = RR.replace("VVV", "남북풍(m/s)");
                        RR = RR.replace("WSD", "풍속(m/s)");


                        String finalRR = RR;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvTEST.setText(finalRR);
                            }
                        });
                    }
                }).start();
            }
        });

        btnEndTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


}
