package com.example.aircomms.approach;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.aircomms.R;
import com.ortiz.touchview.TouchImageView;
public class PseudoTMA extends AppCompatActivity {

    private TouchImageView imageView;
    private float zoomLevel = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pseudo_tma);

        //Setup Image Zoom
        imageView = findViewById(R.id.pseudoTMA_ImageHolder);
        imageView.setMaxZoom(100);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void manageImageZoom (float zoomLevel) {
        if (zoomLevel >= imageView.getMinZoom() && zoomLevel < 2) {
            imageView.setZoom(2);
        } else if (zoomLevel >= 2 && zoomLevel < 4) {
            imageView.setZoom(4);
        } else if (zoomLevel >= 4 && zoomLevel < 10) {
            imageView.setZoom(10);
        } else if (zoomLevel >= 10) {
            imageView.setZoom(1);
        }
    }

    public void approachControl_pseudoTMA_back(View view) {
        onBackPressed();
    }

    public void approachControl_pseudoTMA_zoom(View view) {
        manageImageZoom(imageView.getCurrentZoom());
    }

}