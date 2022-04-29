package com.example.assignment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private String serviceKey = "=5LMImuDe4KYWVHN%2BC8ZiRhKnnboWUFJjia05xmbVjuw79Gv5upIZD%2BB03TgGptciKHQAYkX5TNgTptdIAqJYPg%3D%3D";

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
        Button btnTIP = (Button) findViewById(R.id.btnTIP);
        AutoCompleteTextView AuotoText1 = (AutoCompleteTextView) findViewById(R.id.AuotoText1);

        String[] items = {"청주", "오창", "서울", "대전", "대구", "부산", "독도", "제주"};

        AuotoText1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items));

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


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Location = "&nx=68&ny=109";

                switch (AuotoText1.getText().toString()){
                    case "청주":
                        Location = "&nx=69&ny=107";
                        break;
                    case "오창":
                        Location = "&nx=68&ny=109";
                        break;
                    case "서울":
                        Location = "&nx=60&ny=127";
                        break;
                    case "대전":
                        Location = "&nx=67&ny=100";
                        break;
                    case "대구":
                        Location = "&nx=89&ny=90";
                        break;
                    case "부산":
                        Location = "&nx=98&ny=76";
                        break;
                    case "독도":
                        Location = "&nx=144&ny=123";
                        break;
                    case "제주":
                        Location = "&nx=52&ny=38";
                        break;
                    default:
                        Location = "&nx=69&ny=107";
                        Toast.makeText(getApplicationContext(), AuotoText1.getText().toString() + "는 검색할 수 없는 지역입니다.", Toast.LENGTH_SHORT).show();
                        break;
                }

                String XYLocation = Location;

                String URL_weather = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey" +
                        serviceKey + "&pageNo=1&numOfRows=1000&dataType=XML&base_date=" + base_date + "&base_time=" + base_time_f + XYLocation ;

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

        btnTIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTEST.setText("지역 입력 후 갱신을 눌러주세요.\n\nTip1: 강수 형태 알아보기\n 없음(0)\n 비(1)\n 비/눈(2)\n 눈(3)\n 빗방울(5)\n 빗방울눈날림(6)\n 눈날림(7)\n\nTip2: 검색 가능한 지역\n- 청주, 오창, 서울, 대전\n- 대구, 부산, 독도, 제주\n- 지역 디폴트는 청주입니다!");
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
