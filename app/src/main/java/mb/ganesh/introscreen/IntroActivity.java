package mb.ganesh.introscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    ViewPager viewPager;
    IntroViewPagerAdapter adapter;
    Button nextBtn , startBtn;
    TabLayout tabIndicator;
    Animation animation;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (restorePrefData()){
            startActivity(new Intent(IntroActivity.this , MainActivity.class));
            finish();
        }


        setContentView(R.layout.activity_intro);



        tabIndicator = findViewById(R.id.tabIndicator);
        startBtn = findViewById(R.id.getStartBtn);
        nextBtn = findViewById(R.id.nextBtn);
        animation = AnimationUtils.loadAnimation(this , R.anim.button_anim );

        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Video Classes" , "People will gets unlimited video classes" , R.drawable.video));
        mList.add(new ScreenItem("User Friendly UI" , "You just get user friendly ui" , R.drawable.friend));
        mList.add(new ScreenItem("Quiz for every topics" , "You get every end of the topics has quiz " , R.drawable.exam));
        mList.add(new ScreenItem("2Marks and 5Marks" , "Here 2m and 5m also available" , R.drawable.video));
        mList.add(new ScreenItem("Offline Functionality" , "Once you read material its also available on offline" , R.drawable.friend));

        viewPager = findViewById(R.id.viewPager2);
        adapter = new IntroViewPagerAdapter(this , mList);
        viewPager.setAdapter(adapter);

        tabIndicator.setupWithViewPager(viewPager);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this , MainActivity.class));
                saveData();
                finish();
            }
        });


        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = viewPager.getCurrentItem();
                if (position < mList.size()){
                    position++;
                    viewPager.setCurrentItem(position);
                }

                if (position == mList.size() -1 ){
                    loadLastScreen();
                }
            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences preferences = getSharedPreferences("myPref" , MODE_PRIVATE);
        return preferences.getBoolean("isOpened" , false);
    }

    private void loadLastScreen() {
        startBtn.setVisibility(View.VISIBLE);
        nextBtn.setVisibility(View.GONE);

        startBtn.setAnimation(animation);
    }

    public void saveData(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPref" , MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isOpened" , true );
        editor.apply();

    }
}