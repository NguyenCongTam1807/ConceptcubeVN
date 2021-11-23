package com.example.conceptcube;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout constraint_layout;
    Button btn_shuffle, btn_back;
    int id ;
    GestureDetector gestureDetector;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        /**Create a random list of Image resource IDs**/
        Field[] drawables = R.drawable.class.getDeclaredFields();
        List<Integer> ImageIdList = new ArrayList<>();
        for (Field f : drawables) {
                if (f.getName().length()==3) {
                    id = getApplicationContext().getResources().getIdentifier(
                            f.getName(), "drawable", getApplicationContext().getPackageName()
                    );
                    ImageIdList.add(id);
                }
            }
        Collections.shuffle(ImageIdList);

        /**Create an empty array of ImageViews**/
        int len = ImageIdList.size();
        ImageView[] card = new ImageView[len];

        /**Set these cards face-down and display**/
        //Id of the face-down image
        id = getApplicationContext().getResources().getIdentifier(
                "bb", "drawable", getApplicationContext().getPackageName()
        );
        for (int i=0;i<len;i++) {
            card[i] = new ImageView(this);
            card[i].setImageResource(id);
            card[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            card[i].setZ(i);
            constraint_layout.addView(card[i]);

        btn_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.shuffle(ImageIdList);
                for (int i=0;i<len;i++) {
                    card[i].setImageResource(id);
                    card[i].setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    card[i].setX(0);
                    card[i].setY(0);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

            /**Touch, drag and drop events**/
            gestureDetector = new GestureDetector(this, new SingleTapConfirm());
            card[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    ImageView imageView = (ImageView) view;
                    System.out.println(event.getX()+ " " + event.getY());
                    if (gestureDetector.onTouchEvent(event)) {
                        imageView.setImageResource(ImageIdList.get((int)imageView.getZ()));
                        return true;
                    } else {
                        imageView.setX(event.getRawX()-imageView.getWidth()/2);
                        imageView.setY(event.getRawY()-imageView.getHeight()/2);
                    }

                    return false;
                }

            });


        }
    }
    public void findViews() {
        constraint_layout = findViewById(R.id.constraint_layout);
        btn_shuffle = findViewById(R.id.btn_shuffle);
        btn_back = findViewById(R.id.btn_back);
    }
    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            return true;
        }
    }
}
