package com.example.sliding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MyTag";

    Animation translateLeftAnim;
    Animation translateRightAnim;

    //화면에 있는 2개의 id를 할당하기 위한 변수 선언
    LinearLayout targetPage;
    Button button;

    // 열기/닫기의 상태를 flag로 추가 할거야
    boolean isPageOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        targetPage = findViewById(R.id.targetPage);
        button = findViewById(R.id.button);

        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        //아래 정의해준 Listner를 쓰기 위해 객체 생성
        SlidingAnimationListener animListener = new SlidingAnimationListener();
        translateLeftAnim.setAnimationListener(animListener);
        translateRightAnim.setAnimationListener(animListener);
        // 그래서 버튼 클릭했을 때도 수정되어야 함

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPageOpen){ //page 열린 상태 : 오른쪽으로 닫아줘야해
                    targetPage.startAnimation(translateRightAnim);

                    Log.d(TAG, " == onClick : isPageOpen true 실행");
                }else{ //page 닫힌 상태 : 왼쪽으로 열어줘야해
                    targetPage.startAnimation(translateLeftAnim);

                    Log.d(TAG, " == onClick : isPageOpen false 실행");
                }
//
//                targetPage.setVisibility(View.VISIBLE); //첨에 gone으로 했던게 보이도록 세팅
//                targetPage.startAnimation(translateLeftAnim);
            }
        });
    }

    // 애니메이션이 끝났을 때 정보를 Listner로 받아올 수 있음
    class SlidingAnimationListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override //애니메이션이 끝나는 시점에 button 열기->닫기 로 변경해줄거고, 다시 layout 오른쪽으로 닫아줄거야
        public void onAnimationEnd(Animation animation) { //Q. 여기 잘 모르겠다ㅠㅠ 애니메이션이 끝나고나서 바꿔줄 부분을 적는건가?
            if (isPageOpen){ //페이지가 열려있다면
                targetPage.setVisibility(View.INVISIBLE); //안보이도록 설정  //Q.xml에서 이미 설정해준거 아닌가?

                button.setText("열기");
                isPageOpen = false;
            }else{ //페이지가 닫혀있다면
                targetPage.setVisibility(View.VISIBLE);

                button.setText("닫기");
                isPageOpen = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}