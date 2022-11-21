package com.example.project_final_term;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static float scaleX = 1, scaleY = 1;
    static float angle = 0;
    static float color = 1;
    static float saturation_ = 1;

    LinearLayout baseLayout$;
    ImageButton imageButton_ZoomIn, imageButton_ZoomOut, imageButton_Rotate, imageButton_Bright, imageButton_Dark, imageButton_Gray;
    MyGraphicView graphicView$;
    Button button_SelectImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("FINAL TERM project");

        baseLayout$ = (LinearLayout) findViewById(R.id.baseLayout);
        LinearLayout pictureLayout$ = (LinearLayout) findViewById(R.id.pictureLayout);
        graphicView$ = (MyGraphicView) new MyGraphicView(this);
        pictureLayout$.addView(graphicView$);

        button_SelectImage = (Button) findViewById(R.id.btnSelectImage);
        registerForContextMenu(button_SelectImage);


        clickIcon_ZoomIn();
        clickIcon_ZoomOut();
        clickIcon_Rotate();
        clickIcon_Bright();
        clickIcon_Dark();
        clickIcon_Gray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater$ = getMenuInflater();
        menuInflater$.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.itemPink):
                baseLayout$.setBackgroundColor(getResources().getColor(R.color.pink));
                return true;
            case (R.id.itemLightGreen):
                baseLayout$.setBackgroundColor(getResources().getColor(R.color.light_green));
                return true;
            case (R.id.itemSkyBlue):
                baseLayout$.setBackgroundColor(getResources().getColor(R.color.sky_blue));
                return true;
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menu.setHeaderTitle("Select Image");
        if(v == button_SelectImage) {
            menuInflater.inflate(R.menu.menu_select_image, menu);
        }
    }

    private void clickIcon_ZoomIn() {
        imageButton_ZoomIn = (ImageButton) findViewById(R.id.ibZoomin);
        imageButton_ZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleX = scaleX + 0.1f;
                scaleY = scaleY + 0.1f;
                graphicView$.invalidate();
            }
        });
    }

    private void clickIcon_ZoomOut() {
        imageButton_ZoomOut = (ImageButton) findViewById(R.id.ibZoomout);
        imageButton_ZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleX = scaleX - 0.1f;
                scaleY = scaleY - 0.1f;
                graphicView$.invalidate();
            }
        });
    }

    private void clickIcon_Rotate() {
        imageButton_Rotate = (ImageButton) findViewById(R.id.ibRotate);
        imageButton_Rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                angle = angle + 10;
                graphicView$.invalidate();
            }
        });

    }

    private void clickIcon_Bright() {
        imageButton_Bright = (ImageButton) findViewById(R.id.ibBright);
        imageButton_Bright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = color + 0.2f;
                graphicView$.invalidate();
            }
        });
    }

    private void clickIcon_Dark() {
        imageButton_Dark = (ImageButton) findViewById(R.id.ibDark);
        imageButton_Dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = color - 0.2f;
                graphicView$.invalidate();
            }
        });
    }

    private void clickIcon_Gray() {
        imageButton_Gray = (ImageButton) findViewById(R.id.ibGray);
        imageButton_Gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saturation_ ==0) {
                    saturation_ = 1;
                } else {
                    saturation_ = 0;
                }
                graphicView$.invalidate();
            }
        });
    }

    private static class MyGraphicView extends View {
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int CenX = this.getWidth() / 2;
            int CenY = this.getHeight() / 2;

            canvas.scale(scaleX, scaleY, CenX, CenY);       //ZoomIn, ZoomOut

            canvas.rotate(angle, CenX, CenY);       //Rotate

            Paint paint = new Paint();
            float[] array = { color ,   0   ,   0   ,   0   ,   0   ,
                                0   , color ,   0   ,   0   ,   0   ,
                                0   ,   0   , color ,   0   ,   0   ,
                                0   ,   0   ,   0   ,   1   ,   0   };
            ColorMatrix colorMatrix$ = new ColorMatrix(array);
            if(saturation_ == 0) {
                colorMatrix$.setSaturation(saturation_);
            }
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix$));

            Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.namor);

            int picX = (this.getWidth() - picture.getWidth()) / 2;
            int picY = (this.getWidth() - picture.getWidth()) / 2;

            canvas.drawBitmap(picture, picX, picY, paint);

            picture.recycle();
        }
    }
}