package com.example.user.geocoding;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.util.List;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //맴버 변수 선언
    Geocoder geocoder;
    TextView result;
    List<Address> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Geocoder 객체 생성
        geocoder = new Geocoder(this);
        result = (TextView)findViewById(R.id.resultText);

    }//end onCreate()

    //onClick1 주소/지명을 변환하는 메소드
    //순방향 GeoCoding
    public void transformFromAddress(View v){
        list = null;
        String address = ((EditText)findViewById(R.id.address)).getText().toString();

        try{
            list = geocoder.getFromLocationName(address, 10);

        }catch (IOException e){
            result.setText("입출력 오류 :" + e.getMessage());
            e.printStackTrace();
        }

        if(list != null){
            if(list.size() == 0){
                result.setText("해당되는 주소 정보는 없습니다.");
            }else{
                result.setText(list.get(0).toString());
            }
        }

    }//end transformFromAddress


    //onClick2 위도/경도를 변환하는 메소드
    //역방향 GeoCoding
    public void transformFromCoordinate(View v){
        list = null;
        String latitude = ((EditText)findViewById(R.id.latitude)).getText().toString();
        String longtitude = ((EditText)findViewById(R.id.longtitude)).getText().toString();

        try{
            list = geocoder.getFromLocation(Double.parseDouble(latitude),
                                            Double.parseDouble(longtitude), 10);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }catch (IOException i){
            result.setText("입출력 오류 :" + i.getMessage());
            i.printStackTrace();
        }

        if(list != null){
            if(list.size() == 0){
                result.setText("해당되는 주소 정보는 없습니다.");
            }else{
                result.setText(list.get(0).toString());
            }
        }

    }//end transformFromCoordinate

    //onClick3 지도를 보여주는 메소드
    public void viewMap(View v){
        list = null;
        String address = ((EditText)findViewById(R.id.address)).getText().toString();

        try{
            list = geocoder.getFromLocationName(address, 10);

        }catch (IOException e){
            result.setText("입출력 오류 :" + e.getMessage());
            e.printStackTrace();
        }

        if(list != null){
            Address addr = list.get(0);
            double latitude = addr.getLatitude();   //위도 값 얻기
            double longitude = addr.getLongitude(); //경도 값 얻기

            //String 클래스의 format 메소드로 %f : 실수형태로 변환하여 대입한다.
            String sss = String.format("geo:%f, %f", latitude, longitude);

            /*
                인텐트 기능을 사용하면 안드로이드폰에 설치된 "Maps" 애플리케이션을
                내 안드로이드 폰 안에서 실행할 수 있다.
                즉 구글 Maps 애플리케이션을 실행하는 URI를 인텐트로 보낸다.

                암시적 인텐트로 지도를 본다. 이것은 실제 폰에서만 가능하다.
                ACTION_VIEW 상수는 데이터를 사용자에게 표시한다.
                주어진 위치에서 Maps 애플리케이션을 오픈한다.
                위도와 경도를 URI로 표시하여 Maps 애플리케이션을 실행한다.
             */

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sss));
            startActivity(intent);
        }

    }
}
