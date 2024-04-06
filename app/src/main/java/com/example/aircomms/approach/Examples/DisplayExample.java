package com.example.aircomms.approach.Examples;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.SharedPref;
import com.example.aircomms.approach.ApproachControl;
import com.example.aircomms.approach.PseudoTMA;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DisplayExample extends AppCompatActivity {
    private String startupCode;
    private int displayPageNumber;
    private TextView displayPageTextView;
    private FloatingActionButton displayPagePreviousButton, displayPageNextButton;
    private ConstraintLayout displayPageLayout;
    private ArrayList<ExampleItem> exampleItems;
    private ExampleItemAdapter exampleItemAdapter;
    private int flightStripVisibility;
    private CardView flightStripDeparture, flightStripArrival, flightStripStraightOut;

    private ArrayList<TextView> flightStripTextViews;
    private ArrayList<View> flightStripOtherViews;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(DisplayExample.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(DisplayExample.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_example);

        //Fetches Startup Code from Intent
        Bundle startup = getIntent().getExtras();
        if (startup != null) {
            startupCode = startup.getString("startupCode");
        }

        //Setup Title
        TextView title;
        title = findViewById(R.id.exampleHeaderTitle);
        setupTitle(title);

        //Setup Page
        displayPageTextView = findViewById(R.id.exampleHeaderPage);
        displayPagePreviousButton = findViewById(R.id.exampleHeaderSwapperLeft);
        displayPageNextButton = findViewById(R.id.exampleHeaderSwapperRight);
        displayPageLayout = findViewById(R.id.exampleHeaderLayout);
        displayPageNumber = 1;
        setupPage();

        //Setup Example Items
        exampleItems = setupExampleItems();

        //Setup RecyclerView
        RecyclerView exampleRecyclerView = findViewById(R.id.exampleRecyclerView);
        exampleRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        exampleRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Setup Adapter
        exampleItemAdapter = new ExampleItemAdapter(exampleItems, this);
        exampleRecyclerView.setAdapter(exampleItemAdapter);

        //Setup Flight Strip
        flightStripDeparture = findViewById(R.id.exampleFSDeparture);
        flightStripArrival = findViewById(R.id.exampleFSArrival);
        flightStripStraightOut = findViewById(R.id.exampleFSSTO);
        setupFlightStrip();

        //Setup Flight Strip Data on Adapter
        exampleItemAdapter.setFlightStripTextViews(flightStripTextViews);
        exampleItemAdapter.setFlightStripOtherViews(flightStripOtherViews);
        exampleItemAdapter.setStartupCode(startupCode);
        exampleItemAdapter.manageFlightStrip(-1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    @Override
    protected void onStop() {
        super.onStop();
        exampleItemAdapter.onStop();
    }

    public void approachControl_examples_back(View view) {
        onBackPressed();
    }

    private int setFlightStripVisibility(View view, LinearLayout layout) {
        int visibility = (view.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        view.setVisibility(visibility);
        return view.getVisibility();
    }

    private void showVisibility(View view, ConstraintLayout layout) {
        if (view.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(layout, new AutoTransition());
            view.setVisibility(View.VISIBLE);
        }
    }

    private void hideVisibility(View view, ConstraintLayout layout) {
        if (view.getVisibility() == View.VISIBLE) {
            TransitionManager.beginDelayedTransition(layout, new AutoTransition());
            view.setVisibility(View.GONE);
        }
    }

    private void setupTitle(TextView title) {
        switch (startupCode) {
            case ApproachControl.EXAMPLE_DEPARTURE:
                title.setText("Departure");
                break;
            case ApproachControl.EXAMPLE_ARRIVAL:
                title.setText("Arrival");
                break;
            case ApproachControl.EXAMPLE_STO:
                title.setText("Straight Out Departure");
                break;
        }
    }

    private void setupPage() {
        displayPageTextView.setText(displayPageNumber + " / 3");

        switch (displayPageNumber) {
            case 1:
                hideVisibility(displayPagePreviousButton,displayPageLayout);
                showVisibility(displayPageNextButton,displayPageLayout);
                break;
            case 3:
                showVisibility(displayPagePreviousButton,displayPageLayout);
                hideVisibility(displayPageNextButton,displayPageLayout);
                break;
            default:
                showVisibility(displayPagePreviousButton,displayPageLayout);
                showVisibility(displayPageNextButton,displayPageLayout);
                break;
        }
    }

    private ArrayList<ExampleItem> setupExampleItems() {
        ArrayList<ExampleItem> exampleItems = new ArrayList<>();

        //Add ArrayList Items
        switch (startupCode) {
            case ApproachControl.EXAMPLE_DEPARTURE:
                switch (displayPageNumber) {
                    case 1:
                        exampleItems.add(new ExampleItem("Requesting Aerodrome Information",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "APP, TWR, Go ahead."),
                                                new DialogueItem("APP:", "Request Aerodrome Information."),
                                                new DialogueItem("TWR:", "RWY06, QNH1015, Visibility 10000m",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("RWY06")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Zulu Yankee."),
                                                new DialogueItem("TWR:", "Oscar India.")
                                        )
                                ),
                                new ArrayList<>(
                                        Arrays.asList(
                                                new NoteItem("- Take Note of the Aerodrome information in a piece of paper / scratch paper."),
                                                new NoteItem("- Be mindful with words in color, they are necessary in Flight Progressive Strips.")
                                        )
                                ),
                                R.raw.example_departure1_1));

                        exampleItems.add(new ExampleItem("Pilot requests ATC Clearance for DEPARTURE from APP/Clearance Delivery(CD)",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0912")
                                                                )
                                                        )),
                                                new DialogueItem("PAL 321:", "Clearance Delivery, this is PAL 321."),
                                                new DialogueItem("CD:", "PAL 321, Clearance Delivery, Go Ahead."),
                                                new DialogueItem("PAL 321:", "We are an A380, requesting ATC Clearance to Hong Kong, FL350.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("A380"),
                                                                        new DialogueHighlight("Hong Kong"),
                                                                        new DialogueHighlight("FL350")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "PAL 321, standby for clearance.")
                                        )
                                ),R.raw.example_departure1_2));

                        exampleItems.add(new ExampleItem("APP/CD request ATC Clearance from ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0915")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "Manila Control, Clearance Delivery."),
                                                new DialogueItem("ACC:", "Clearance Delivery, Manila Control, Go ahead."),
                                                new DialogueItem("CD:", "Request ATC clearance for PAL 321, an A380, destination Hong Kong, requesting FL350."),
                                                new DialogueItem("ACC:", "Clearance ready."),
                                                new DialogueItem("CD:", "Ready to copy."),
                                                new DialogueItem("ACC:", "PAL 321 cleared Hong Kong via Tucaban W10, Flight plan route climb and maintain FL150 until further advise.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("Tucaban W10"),
                                                                        new DialogueHighlight("FL150")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "PAL 321 cleared Hong Kong via Tucaban W10, Flight plan route climb and maintain FL150 until further advise."),
                                                new DialogueItem("ACC:", "Readback correct. Alpha Alpha.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Alpha Alpha")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "Zulu Yankee.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Zulu Yankee")
                                                                )
                                                        ))
                                        )
                                ),R.raw.example_departure1_3));

                        exampleItems.add(new ExampleItem("APP/CD relays ATC Clearance to Pilot",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0916")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "PAL 321, Clearance ready."),
                                                new DialogueItem("PAL 321:", "Ready to copy."),
                                                new DialogueItem("CD:", "PAL 321 cleared Hong Kong airport via Tucaban W10 flight plan route climb and maintain FL350 on Tuc (2A) departure.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Tuc (2A)")
                                                                )
                                                        )),
                                                new DialogueItem("PAL 321:", "PAL 321 cleared Hong Kong airport via Tucaban W10 flight plan route climb and maintain FL350 on Tuc (2A) departure."),
                                                new DialogueItem("CD:", "Readback correct, contact Ground 121.8."),
                                                new DialogueItem("PAL 321:", "Switching to 121.8. Good day.")
                                        )
                                ),R.raw.example_departure1_4));

                        exampleItems.add(new ExampleItem("TOWER requests RELEASE for departing aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0918")
                                                                )
                                                        )),
                                                new DialogueItem("TWR:", "APP, TWR."),
                                                new DialogueItem("APP:", "TWR, APP, Go ahead."),
                                                new DialogueItem("TWR:", "Request release, PAL 321, rwy06."),
                                                new DialogueItem("APP:", "PAL 321 on Tuc (2A) Departure, 5000. RWY06, Release. ",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("5000")
                                                                )
                                                        )),
                                                new DialogueItem("TWR:", "Oscar India."),
                                                new DialogueItem("APP:", "Zulu Yankee.")
                                        )
                                ),R.raw.example_departure1_5));

                        exampleItems.add(new ExampleItem("Departure is airborne, TOWER relays departure time to APP",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("TWR:", "APP, TWR."),
                                                new DialogueItem("APP:", "Go ahead."),
                                                new DialogueItem("TWR:", "PAL 321 airborne RWY06 at 0920.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0920")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Zulu Yankee."),
                                                new DialogueItem("TWR:", "Oscar India.")
                                        )
                                ),R.raw.example_departure1_6));

                        exampleItems.add(new ExampleItem("Relay Airborne Time to ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("APP:", "Control, App"),
                                                new DialogueItem("ACC:", "Go ahead."),
                                                new DialogueItem("APP:", "PAL 321 airborne at time 0920."),
                                                new DialogueItem("ACC:", "Alpha Alpha."),
                                                new DialogueItem("APP:", "Zulu Yankee.")
                                        )
                                ),R.raw.example_departure1_7));

                        exampleItems.add(new ExampleItem("Initial Contact with Departing aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("PAL 321:", "APP, PAL 321, airborne runway 06 climbing 5000 on Tuc (2A) departure."),
                                                new DialogueItem("APP:", "PAL 321, APP, climb 8000 continue Tuc (2A) report when established.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("8000")
                                                                )
                                                        )),
                                                new DialogueItem("PAL 321:", "Roger. Climb 8000, continue Tuc (2A) departure, Will report when established."),
                                                new DialogueItem("PAL 321:", "APP. PAL 321, now established Radial 176 of Tucaban VOR, climbing 8000."),
                                                new DialogueItem("APP:", "PAL 321, climb FL150 UFA."),
                                                new DialogueItem("PAL 321:", "Roger, climbing flight level 150 UFA.")
                                        )
                                ),R.raw.example_departure1_8));

                        exampleItems.add(new ExampleItem("Transfer of Control to ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0934")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Control, APP, release."),
                                                new DialogueItem("ACC:", "App Go ahead."),
                                                new DialogueItem("APP:", "PAL 321, established R176 climbing Fl150UFA, release."),
                                                new DialogueItem("ACC:", "Alpha Alpha."),
                                                new DialogueItem("APP:", "Zulu Yankee.")
                                        )
                                ),R.raw.example_departure1_9));
                        break;
                    case 2:
                        exampleItems.add(new ExampleItem("Requesting Aerodrome Information",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "APP, TWR, Go ahead."),
                                                new DialogueItem("APP:", "Request Aerodrome Information."),
                                                new DialogueItem("TWR:", "RWY06, QNH1015, Visibility 10000 m",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("RWY06")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Papa Alpha."),
                                                new DialogueItem("TWR:", "Golf Bravo.")
                                        )
                                ),
                                new ArrayList<>(
                                        Arrays.asList(
                                                new NoteItem("- Take Note of the Aerodrome information in a piece of paper/scratch paper."),
                                                new NoteItem("- Be mindful with words in color, they are necessary in Flight Progressive Strips.")
                                        )
                                ),R.raw.example_departure2_1));

                        exampleItems.add(new ExampleItem("Pilot requests ATC Clearance for DEPARTURE from APP/Clearance Delivery(CD)",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1223")
                                                                )
                                                        )),
                                                new DialogueItem("UAL 490:", "Clearance Delivery, this is UAL 490.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("UAL 490")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "UAL 490, Clearance Delivery, Go Ahead."),
                                                new DialogueItem("UAL 490:", "We are an B747, requesting ATC Clearance to San Francisco, FL350.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("B747"),
                                                                        new DialogueHighlight("San Francisco"),
                                                                        new DialogueHighlight("FL350")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "UAL 490, standby for clearance.")
                                        )
                                ),R.raw.example_departure2_2));

                        exampleItems.add(new ExampleItem("APP/CD request ATC Clearance from ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1228")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "Manila Control, Clearance Delivery."),
                                                new DialogueItem("ACC:", "Clearance Delivery, Manila Control, Go ahead."),
                                                new DialogueItem("CD:", "Request ATC clearance for UAL 490, an B747, destination San Francisco, requesting FL350."),
                                                new DialogueItem("ACC:", "Clearance ready."),
                                                new DialogueItem("CD:", "Ready to copy."),
                                                new DialogueItem("ACC:", "UAL 490 cleared San Francisco via Maglayog G656 Y777, Flight plan route climb and maintain FL150 until further advise.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("Maglayog G656 Y777"),
                                                                        new DialogueHighlight("FL150")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "UAL 490 cleared San Francisco via Maglayog G656 Y777, Flight plan route climb and maintain FL150 until further advise."),
                                                new DialogueItem("ACC:", "Readback correct. November Delta.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("November Delta")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "Papa Alpha.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Papa Alpha")
                                                                )
                                                        ))
                                        )
                                ),R.raw.example_departure2_3));

                        exampleItems.add(new ExampleItem("APP/CD relays ATC Clearance to Pilot",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1229")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "UAL 490, Clearance ready."),
                                                new DialogueItem("UAL 490:", "Ready to copy."),
                                                new DialogueItem("CD:", "UAL 490 cleared San Francisco airport via Maglayog G656 Y777 flight plan route climb and maintain FL300on Mag(2A) departure.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Mag(2A)")
                                                                )
                                                        )),
                                                new DialogueItem("UAL 490:", "UAL 490 cleared San Francisco airport via Maglayog G656 Y777 flight plan route climb and maintain FL300on Mag(2A) departure."),
                                                new DialogueItem("CD:", "Readback correct, contact Ground 121.8."),
                                                new DialogueItem("UAL 490:", "Switching to 121.8. Good day.")
                                        )
                                ),R.raw.example_departure2_4));

                        exampleItems.add(new ExampleItem("TOWER requests RELEASE for departing aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1232")
                                                                )
                                                        )),
                                                new DialogueItem("TWR:", "APP, TWR."),
                                                new DialogueItem("APP:", "TWR, APP, Go ahead."),
                                                new DialogueItem("TWR:", "Request release, UAL 490, rwy06."),
                                                new DialogueItem("APP:", "UAL 490 on Mag(2A) Departure, 5000. RWY06, Release.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("5000")
                                                                )
                                                        )),
                                                new DialogueItem("TWR:", "Golf Bravo."),
                                                new DialogueItem("APP:", "Papa Alpha.")
                                        )
                                ),R.raw.example_departure2_5));

                        exampleItems.add(new ExampleItem("Departure is airborne, TOWER relays departure time to APP",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("TWR:", "APP, TWR."),
                                                new DialogueItem("APP:", "Go ahead."),
                                                new DialogueItem("TWR:", "UAL 490 airborne RWY06 at 1234.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1234")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Papa Alpha."),
                                                new DialogueItem("TWR:", "Golf Bravo.")
                                        )
                                ),R.raw.example_departure2_6));

                        exampleItems.add(new ExampleItem("Relay Airborne Time to ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("APP:", "Control, App."),
                                                new DialogueItem("ACC:", "Go ahead."),
                                                new DialogueItem("APP:", "UAL 490 airborne at time 1234."),
                                                new DialogueItem("ACC:", "November Delta."),
                                                new DialogueItem("APP:", "Papa Alpha.")
                                        )
                                ),R.raw.example_departure2_7));


                        exampleItems.add(new ExampleItem("Initial Contact with Departing aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("UAL 490:", "APP, UAL 490, airborne runway 06 climbing 5000 on Mag(2A) departure."),
                                                new DialogueItem("APP:", "UAL 490, APP, climb 9000 continue Mag(2A) report when established.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("9000")
                                                                )
                                                        )),
                                                new DialogueItem("UAL 490:", "Roger. Climb 9000, continue Mag(2A) departure, Will report when established."),
                                                new DialogueItem("UAl 490:", "APP. UAL 490, now established Radial 081 of Manila VOR, climbing 9000."),
                                                new DialogueItem("APP:", "UAL 490, climb FL150 UFA."),
                                                new DialogueItem("UAL 490:", "Roger, climbing flight level 150 UFA.")
                                        )
                                ),R.raw.example_departure2_8));

                        exampleItems.add(new ExampleItem("Transfer of Control to ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1245")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Control, APP, release."),
                                                new DialogueItem("ACC:", "App Go ahead."),
                                                new DialogueItem("APP:", "UAL 490, established R081 climbing Fl150UFA, release."),
                                                new DialogueItem("ACC:", "November Delta."),
                                                new DialogueItem("APP:", "Papa Alpha.")
                                        )
                                ),R.raw.example_departure2_9));
                        break;
                    case 3:
                        exampleItems.add(new ExampleItem("Requesting Aerodrome Information",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "APP, TWR, Go ahead."),
                                                new DialogueItem("APP:", "Request Aerodrome Information."),
                                                new DialogueItem("TWR:", "RWY06, QNH1015, Visibility 10000m."),
                                                new DialogueItem("APP:", "Romeo Juliet."),
                                                new DialogueItem("TWR:", "Delta Echo.")
                                        )
                                ),
                                new ArrayList<>(
                                        Arrays.asList(
                                                new NoteItem("- Take Note of the Aerodrome information in a piece of paper/scratch paper."),
                                                new NoteItem("- Be mindful with words in color, they are necessary in Flight Progressive Strips.")
                                        )
                                ),R.raw.example_departure3_1));

                        exampleItems.add(new ExampleItem("Pilot requests ATC Clearance for DEPARTURE from APP/Clearance Delivery(CD)",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1709")
                                                                )
                                                        )),
                                                new DialogueItem("CAL 123:", "Clearance Delivery, this is CAL 123.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("CAL 123")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "CAL 123, Clearance Delivery, Go Ahead."),
                                                new DialogueItem("CAl 123:", "We are an A350, requesting ATC Clearance to Shenzhen, FL320.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("A350"),
                                                                        new DialogueHighlight("Shenzhen"),
                                                                        new DialogueHighlight("FL320")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "CAL 123, standby for clearance.")
                                        )
                                ),R.raw.example_departure3_2));

                        exampleItems.add(new ExampleItem("APP/CD request ATC Clearance from ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1712")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "Manila Control, Clearance Delivery."),
                                                new DialogueItem("ACC:", "Clearance Delivery, Manila Control, Go ahead."),
                                                new DialogueItem("CD:", "Request ATC clearance for CAL 123, an A350, destination Shenzhen, requesting FL320."),
                                                new DialogueItem("ACC:", "Clearance ready."),
                                                new DialogueItem("CD:", "Ready to copy."),
                                                new DialogueItem("APP:", "CAL 123 cleared Shenzhen via Tucaban T555, Flight plan route climb and maintain FL150 until further advise.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("Tucaban T555"),
                                                                        new DialogueHighlight("FL150")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "CAL 123 cleared Shenzhen via Tucaban T555, Flight plan route climb and maintain FL150 until further advise."),
                                                new DialogueItem("ACC:", "Readback correct. Kilo Sierra.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Kilo Sierra")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "Romeo Juliet.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Romeo Juliet")
                                                                )
                                                        ))
                                        )
                                ),R.raw.example_departure3_3));

                        exampleItems.add(new ExampleItem("APP/CD relays ATC Clearance to Pilot",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1715")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "CAL 123, Clearance ready."),
                                                new DialogueItem("CAl 123:", "Ready to copy."),
                                                new DialogueItem("CD:", "CAL 123 cleared Shenzhen airport via Tucaban T555 flight plan route climb and maintain FL320 on Tuc (2A) departure.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Tuc (2A)")
                                                                )
                                                        )),
                                                new DialogueItem("CAl 123:", "CAL 123 cleared Shenzhen airport via Tucaban T555 flight plan route climb and maintain FL320 on Tuc (2A) departure."),
                                                new DialogueItem("CD:", "Readback correct, contact Ground 121.8."),
                                                new DialogueItem("CAl 123:", "Switching to 121.8. Good day.")
                                        )
                                ),R.raw.example_departure3_4));

                        exampleItems.add(new ExampleItem("TOWER requests RELEASE for departing aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1719")
                                                                )
                                                        )),
                                                new DialogueItem("TWR:", "APP, TWR."),
                                                new DialogueItem("APP:", "TWR, APP, Go ahead."),
                                                new DialogueItem("TWR:", "Request release, CAL 123, rwy06."),
                                                new DialogueItem("APP:", "CAL 123 on Tuc (2A) Departure, 5000. RWY06, Release.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("5000")
                                                                )
                                                        )),
                                                new DialogueItem("TWR:", "Delta Echo."),
                                                new DialogueItem("APP:", "Romeo Juliet.")
                                        )
                                ),R.raw.example_departure3_5));

                        exampleItems.add(new ExampleItem("Departure is airborne, TOWER relays departure time to APP",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("TWR:", "APP, TWR."),
                                                new DialogueItem("APP:", "Go ahead."),
                                                new DialogueItem("TWR:", "CAL 123 airborne RWY06 at 1721.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1721")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Romeo Juliet."),
                                                new DialogueItem("TWR:", "Delta Echo.")
                                        )
                                ),R.raw.example_departure3_6));

                        exampleItems.add(new ExampleItem("Relay Airborne Time to ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("APP:", "Control, App."),
                                                new DialogueItem("ACC:", "Go ahead."),
                                                new DialogueItem("APP:", "CAL 123 airborne at time 1721."),
                                                new DialogueItem("ACC:", "Kilo Sierra."),
                                                new DialogueItem("APP:", "Romeo Juliet.")
                                        )
                                ),R.raw.example_departure3_7));

                        exampleItems.add(new ExampleItem("Initial Contact with Departing aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("CAL 123:", "APP, CAL 123, airborne runway 06 climbing 5000 on Tuc (2A) departure."),
                                                new DialogueItem("APP:", "CAL 123, APP, climb 7000 continue Tuc (2A) report when established.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("7000")
                                                                )
                                                        )),
                                                new DialogueItem("CAL 123:", "Roger. Climb 7000, continue Tuc (2A) departure, Will report when established."),
                                                new DialogueItem("CAL 123:", "APP. CAL 123, now established Radial 176 of Tucaban VOR, climbing 7000."),
                                                new DialogueItem("APP:", "CAL 123, climb FL150 UFA."),
                                                new DialogueItem("CAL 123:", "Roger, climbing flight level 150 UFA.")
                                        )
                                ),R.raw.example_departure3_8));

                        exampleItems.add(new ExampleItem("Transfer of Control to ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1730")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Control, APP, release."),
                                                new DialogueItem("ACC:", "App Go ahead."),
                                                new DialogueItem("APP:", "CAL 123, established R176 climbing Fl150UFA, release."),
                                                new DialogueItem("ACC:", "Kilo Sierra."),
                                                new DialogueItem("APP:", "Romeo Juliet.")
                                        )
                                ),R.raw.example_departure3_9));
                        break;
                }
                break;
            case ApproachControl.EXAMPLE_ARRIVAL:
                switch (displayPageNumber) {
                    case 1:
                        exampleItems.add(new ExampleItem("ACC gives ESTIMATE of arriving traffic",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1147")
                                                                )
                                                        )),
                                                new DialogueItem("ACC:", "APP, Control, Estimate."),
                                                new DialogueItem("APP:", "Control, APP, Go ahead estimate."),
                                                new DialogueItem("ACC:", "Estimate, SRQ 512 B777, from RJTT, estimates Vamil at time 1230, FL150.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("SRQ 512 B777"),
                                                                        new DialogueHighlight("RJTT"),
                                                                        new DialogueHighlight("Vamil"),
                                                                        new DialogueHighlight("1230"),
                                                                        new DialogueHighlight("FL150")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "India Golf."),
                                                new DialogueItem("ACC:", "Yankee Tango.")
                                        )
                                ),R.raw.example_arrival1_1));

                        exampleItems.add(new ExampleItem("Relay to TOWER the sequence of arriving traffic with their estimates over the VOR",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "APP, TWR, Go ahead."),
                                                new DialogueItem("APP:", "Arrival Estimate, SRQ 512, an A320 from RJTT, estimates Manila VOR at 1241, Your control on final."),
                                                new DialogueItem("TWR:", "Alpha Charlie."),
                                                new DialogueItem("APP:", "India Golf.")
                                        )
                                ),R.raw.example_arrival1_2));

                        exampleItems.add(new ExampleItem("ACC Releases arrivals to APP",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("ACC:", "APP, Control, RELEASE."),
                                                new DialogueItem("APP:", "Control, APP, Go ahead."),
                                                new DialogueItem("ACC:", "Estimate, SRQ 512, descending FL150, approaching Vamil, Release."),
                                                new DialogueItem("APP:", "Bravo Hotel."),
                                                new DialogueItem("ACC:", "Yankee Tango.")
                                        )
                                ),R.raw.example_arrival1_3));

                        exampleItems.add(new ExampleItem("INITIAL CONTACT WITH ARRIVING AIRCRAFT",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1228")
                                                                )
                                                        )),
                                                new DialogueItem("SRQ 512:", "APP, SRQ 512, passing FL200 for FL150."),
                                                new DialogueItem("APP:", "SRQ 512, Manila APP, Proceed Manila VOR via Vamil, Volets, Expect ILS Approach RWY24, QNH 1019, Descend 110.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("ILS Approach RWY24"),
                                                                        new DialogueHighlight("110")
                                                                )
                                                        )),
                                                    new DialogueItem("SRQ 512:", "Roger. To proceed Manila VOR via Vamil, Volets, Expect ILS Approach RWY24, QNH 1019, will Descend 110.")
                                        )
                                ),R.raw.example_arrival1_4));

                        exampleItems.add(new ExampleItem("Monitoring of Aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("ATC APP:", "Report over Vamil."),
                                                new DialogueItem("SRQ 512:", "Wilco."),
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1230")
                                                                )
                                                        )),
                                                new DialogueItem("SRQ 512:", "Approach, SRQ 512, now over Vamil."),
                                                new DialogueItem("ATC APP:", "Roger. Report over Volets."),
                                                new DialogueItem("SRQ 512:", "Will report over Volets, SRQ 512.")
                                        )
                                ),R.raw.example_arrival1_5));

                        exampleItems.add(new ExampleItem("Aircraft Approaching the VOR",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1239")
                                                                )
                                                        )),
                                                new DialogueItem("SRQ 512:", "APP, SRQ 512 Just passed Volets a minute ago."),
                                                new DialogueItem("APP:", "SRQ 512, descend 6000, cleared ILS approach rwy24. Report commencing over the VOR at 6000.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("6000")
                                                                )
                                                        )),
                                                new DialogueItem("(Readback)"),
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1241")
                                                                )
                                                        )),
                                                new DialogueItem("GAP2135:", "APP, SRQ 512 commencing over the VOR."),
                                                new DialogueItem("APP:", "Roger. Report Turning Base."),
                                                new DialogueItem("(Readback)")
                                        )
                                ),R.raw.example_arrival1_6));

                        exampleItems.add(new ExampleItem("Advise TWR that arrival is commencing over the VOR",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1242")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "Go Ahead."),
                                                new DialogueItem("APP:", "SRQ 512 commencing over the VOR your control on final."),
                                                new DialogueItem("TWR:", "Alpha Charlie."),
                                                new DialogueItem("APP:", "India Golf.")
                                        )
                                ),R.raw.example_arrival1_7));

                        exampleItems.add(new ExampleItem("Aircraft Approaching the VOR",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1243")
                                                                )
                                                        )),
                                                new DialogueItem("SRQ 512:", "APP, SRQ 512 on base turn."),
                                                new DialogueItem("APP:", "SRQ 512 report at final."),
                                                new DialogueItem("(Readback)"),
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1244")
                                                                )
                                                        )),
                                                new DialogueItem("SRQ 512:", "APP, SRQ 512 now on final."),
                                                new DialogueItem("APP:", "SRQ 512 contact tower, 118.1.")
                                        )
                                ),R.raw.example_arrival1_8));
                        break;
                    case 2:
                        exampleItems.add(new ExampleItem("ACC gives ESTIMATE of arriving traffic",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("2359")
                                                                )
                                                        )),
                                                new DialogueItem("ACC:", "APP, Control, Estimate."),
                                                new DialogueItem("APP:", "Control, APP, Go ahead estimate."),
                                                new DialogueItem("ACC:", "Estimate, APG 009 A330, from Kuwait, estimates Letem at time 0047, FL150.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("APG 009 A330"),
                                                                        new DialogueHighlight("Kuwait"),
                                                                        new DialogueHighlight("Letem"),
                                                                        new DialogueHighlight("0047"),
                                                                        new DialogueHighlight("FL150")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Charlie Victor."),
                                                new DialogueItem("ACC:", "Yankee Tango.")
                                        )
                                ),R.raw.example_arrival2_1));

                        exampleItems.add(new ExampleItem("Relay to TOWER the sequence of arriving traffic with their estimates over the VOR",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "APP, TWR, Go ahead."),
                                                new DialogueItem("APP:", "Arrival Estimate, APG 009, an A320 from Kuwait, estimates Manila VOR at 0058, Your control on final."),
                                                new DialogueItem("TWR:", "Alpha Charlie."),
                                                new DialogueItem("APP:", "Charlie Victor.")
                                        )
                                ),R.raw.example_arrival2_2));

                        exampleItems.add(new ExampleItem("ACC Releases arrivals to APP",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("ACC:", "APP, Control, RELEASE."),
                                                new DialogueItem("APP:", "Control, APP, Go ahead."),
                                                new DialogueItem("ACC:", "Estimate, APG 009, descending FL150, approaching Letem, Release."),
                                                new DialogueItem("APP:", "Bravo Hotel."),
                                                new DialogueItem("ACC:", "Yankee Tango.")
                                        )
                                ),R.raw.example_arrival2_3));

                        exampleItems.add(new ExampleItem("INITIAL CONTACT WITH ARRIVING AIRCRAFT",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0045")
                                                                )
                                                        )),
                                                new DialogueItem("APG 009:", "APP, APG 009, passing FL200 for FL150."),
                                                new DialogueItem("APP:", "APG 009, Manila APP, Proceed Manila VOR via Letem, Kore, Gabon. Expect ILS Approach RWY24, QNH 1019, Descend 110.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("ILS Approach RWY24"),
                                                                        new DialogueHighlight("110")
                                                                )
                                                        )),
                                                new DialogueItem("APG 009:", "Roger. To proceed Manila VOR via Letem, Kore, Gabon. Expect ILS Approach RWY24, QNH 1019, will Descend 110.")
                                        )
                                ),R.raw.example_arrival2_4));

                        exampleItems.add(new ExampleItem("Monitoring of Aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("ATC APP:", "Report over Letem."),
                                                new DialogueItem("APG 009:", "Wilco."),
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0047")
                                                                )
                                                        )),
                                                new DialogueItem("APG 009:", "Approach, APG 009, now over Letem."),
                                                new DialogueItem("ATC APP:", "Roger. Report over Kore."),
                                                new DialogueItem("APG 009:", "Will report over Kore, APG 009."),
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0049")
                                                                )
                                                        )),
                                                new DialogueItem("APG 009:", "Approach, APG 009, now over Kore."),
                                                new DialogueItem("ATC APP:", "Roger. Report over Gabon."),
                                                new DialogueItem("APG 009:", "Will report over Gabon, APG 009.")
                                        )
                                ),R.raw.example_arrival2_5));

                        exampleItems.add(new ExampleItem("Aircraft Approaching the VOR",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0054")
                                                                )
                                                        )),
                                                new DialogueItem("APG 009:", "APP, APG 009 now over Gabon."),
                                                new DialogueItem("APP:", "APG 009, descend 6000, cleared ILS approach rwy24. Report commencing over the VOR at 6000.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("6000")
                                                                )
                                                        )),
                                                new DialogueItem("(Readback)"),
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0058")
                                                                )
                                                        )),
                                                new DialogueItem("GAP2135:", "APP, APG 009 commencing over the VOR."),
                                                new DialogueItem("APP:", "Roger. Report Turning Base."),
                                                new DialogueItem("(Readback)")
                                        )
                                ),R.raw.example_arrival2_6));

                        exampleItems.add(new ExampleItem("Advise TWR that arrival is commencing over the VOR",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0059")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "Go Ahead."),
                                                new DialogueItem("APP:", "APG 009 commencing over the VOR your control on final."),
                                                new DialogueItem("TWR:", "Alpha Charlie."),
                                                new DialogueItem("APP:", "Charlie Victor.")
                                        )
                                ),R.raw.example_arrival2_7));

                        exampleItems.add(new ExampleItem("Aircraft Approaching the VOR",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0100")
                                                                )
                                                        )),
                                                new DialogueItem("APG 009:", "APP, APG 009 on base turn."),
                                                new DialogueItem("APP:", "APG 009 report at final."),
                                                new DialogueItem("(Readback)"),
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0101")
                                                                )
                                                        )),
                                                new DialogueItem("APG 009:", "APP, APG 009 now on final."),
                                                new DialogueItem("APP:", "APG 009 contact tower, 118.1.")
                                        )
                                ),R.raw.example_arrival2_8));
                        break;
                    case 3:
                        exampleItems.add(new ExampleItem("ACC gives ESTIMATE of arriving traffic",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0630")
                                                                )
                                                        )),
                                                new DialogueItem("ACC:", "APP, Control, Estimate."),
                                                new DialogueItem("APP:", "Control, APP, Go ahead estimate."),
                                                new DialogueItem("ACC:", "Estimate, SIA 888 B737, from Singapore, estimates Calay at time 0654, FL150.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("SIA 888 B737"),
                                                                        new DialogueHighlight("Singapore"),
                                                                        new DialogueHighlight("Calay"),
                                                                        new DialogueHighlight("0654"),
                                                                        new DialogueHighlight("FL150")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Charlie Victor."),
                                                new DialogueItem("ACC:", "Yankee Tango.")
                                        )
                                ),R.raw.example_arrival3_1));

                        exampleItems.add(new ExampleItem("Relay to TOWER the sequence of arriving traffic with their estimates over the VOR",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "APP, TWR, Go ahead."),
                                                new DialogueItem("APP:", "Arrival Estimate, SIA 888, an A320 from Singapore, estimates Manila VOR at 0708, Your control on final."),
                                                new DialogueItem("TWR:", "Alpha Charlie."),
                                                new DialogueItem("APP:", "Charlie Victor.")
                                        )
                                ),R.raw.example_arrival3_2));

                        exampleItems.add(new ExampleItem("ACC Releases arrivals to APP",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("ACC:", "APP, Control, RELEASE."),
                                                new DialogueItem("APP:", "Control, APP, Go ahead."),
                                                new DialogueItem("ACC:", "Estimate, SIA 888, descending FL150, approaching Calay, Release."),
                                                new DialogueItem("APP:", "Bravo Hotel."),
                                                new DialogueItem("ACC:", "Yankee Tango.")
                                        )
                                ),R.raw.example_arrival3_3));

                        exampleItems.add(new ExampleItem("INITIAL CONTACT WITH ARRIVING AIRCRAFT",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0653")
                                                                )
                                                        )),
                                                new DialogueItem("SIA 888:", "APP, SIA 888, passing FL200 for FL150."),
                                                new DialogueItem("APP:", "SIA 888, Manila APP, Proceed Manila VOR via Calay, Yanib, Dali. Expect ILS Approach RWY24, QNH 1019, Descend 110.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("ILS Approach RWY24"),
                                                                        new DialogueHighlight("110")
                                                                )
                                                        )),
                                                new DialogueItem("SIA 888:", "Roger. To proceed Manila VOR via Calay, Yanib, Dali. Expect ILS Approach RWY24, QNH 1019, will Descend 110.")
                                        )
                                ),R.raw.example_arrival3_4));

                        exampleItems.add(new ExampleItem("Monitoring of Aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("ATC APP:", "Report over Calay."),
                                                new DialogueItem("SIA 888:", "Wilco."),
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0654")
                                                                )
                                                        )),
                                                new DialogueItem("SIA 888:", "Approach, SIA 888, now over Calay."),
                                                new DialogueItem("ATC APP:", "Roger. Report over Yanib."),
                                                new DialogueItem("SIA 888:", "Will report over Yanib, SIA 888."),
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0659")
                                                                )
                                                        )),
                                                new DialogueItem("SIA 888:", "Approach, SIA 888, now over Yanib."),
                                                new DialogueItem("ATC APP:", "Roger. Report over Dali."),
                                                new DialogueItem("SIA 888:", "Will report over Dali, SIA 888.")
                                        )
                                ),R.raw.example_arrival3_5));

                        exampleItems.add(new ExampleItem("Aircraft Approaching the VOR",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0703")
                                                                )
                                                        )),
                                                new DialogueItem("SIA 888:", "APP, SIA 888 now over Gabon."),
                                                new DialogueItem("APP:", "SIA 888, descend 6000, cleared ILS approach rwy24. Report commencing over the VOR at 6000.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("6000")
                                                                )
                                                        )),
                                                new DialogueItem("(Readback)"),
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0708")
                                                                )
                                                        )),
                                                new DialogueItem("GAP2135:", "APP, SIA 888 commencing over the VOR."),
                                                new DialogueItem("APP:", "Roger. Report Turning Base."),
                                                new DialogueItem("(Readback)")
                                        )
                                ),R.raw.example_arrival3_6));

                        exampleItems.add(new ExampleItem("Advise TWR that arrival is commencing over the VOR",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0709")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "Go Ahead."),
                                                new DialogueItem("APP:", "SIA 888 commencing over the VOR your control on final."),
                                                new DialogueItem("TWR:", "Alpha Charlie."),
                                                new DialogueItem("APP:", "Charlie Victor.")
                                        )
                                ),R.raw.example_arrival3_7));

                        exampleItems.add(new ExampleItem("Aircraft Approaching the VOR",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0710")
                                                                )
                                                        )),
                                                new DialogueItem("SIA 888:", "APP, SIA 888 on base turn."),
                                                new DialogueItem("APP:", "SIA 888 report at final."),
                                                new DialogueItem("(Readback)"),
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0711")
                                                                )
                                                        )),
                                                new DialogueItem("SIA 888:", "APP, SIA 888 now on final."),
                                                new DialogueItem("APP:", "SIA 888 contact tower, 118.1.")
                                        )
                                ),R.raw.example_arrival3_8));
                        break;
                }
                break;
            case ApproachControl.EXAMPLE_STO:
                switch (displayPageNumber) {
                    case 1:
                        exampleItems.add(new ExampleItem("Requesting Aerodrome Information",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "APP, TWR, Go ahead."),
                                                new DialogueItem("APP:", "Request Aerodrome Information."),
                                                new DialogueItem("TWR:", "RWY24, QNH1010, Visibility 8000km.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("RWY24")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Papa Golf."),
                                                new DialogueItem("TWR:", "Victor Sierra.")
                                        )
                                ),R.raw.example_sto1_1));

                        exampleItems.add(new ExampleItem("Pilot requests ATC Clearance for DEPARTURE from APP/Clearance Delivery (CD)",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0546")
                                                                )
                                                        )
                                                ),
                                                new DialogueItem("UAE 626:", "Clearance Delivery, this is UAE 626.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("UAE 626")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "UAE 626 , Clearance Delivery, Go Ahead."),
                                                new DialogueItem("UAE 626:", "We are an B777, requesting ATC Clearance to Dubai, FL310.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("B777"),
                                                                        new DialogueHighlight("Dubai"),
                                                                        new DialogueHighlight("FL310")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "UAE 626 , standby for clearance.")
                                        )
                                ),R.raw.example_sto1_2));

                        exampleItems.add(new ExampleItem("APP/CD request ATC Clearance from ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0549")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "Manila Control, Clearance Delivery."),
                                                new DialogueItem("ACC:", "Clearance Delivery, Manila Control, Go ahead."),
                                                new DialogueItem("CD:", "Request ATC clearance for UAE 626, an B777, destination Dubai, requesting FL310."),
                                                new DialogueItem("ACC:", "Clearance ready."),
                                                new DialogueItem("CD:", "Ready to copy."),
                                                new DialogueItem("APP:", "UAE 626 cleared Dubai International airport via Bitam Y718, Flight plan route climb and maintain FL150 until further advise.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("Bitam Y718"),
                                                                        new DialogueHighlight("FL150")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "UAE 626 cleared Dubai International airport via Bitam Y718, Flight plan route climb and maintain FL150 until further advise."),
                                                new DialogueItem("ACC:", "Readback correct. Uniform Hotel.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Uniform Hotel")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "Delta Papa.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Delta Papa")
                                                                )
                                                        ))
                                        )
                                ),R.raw.example_sto1_3));

                        exampleItems.add(new ExampleItem("APP/CD relays ATC Clearance to Pilot",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0550")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "UAE 626 , Clearance ready."),
                                                new DialogueItem("UAE 626:", "Ready to copy."),
                                                new DialogueItem("CD:", "UAE 626 cleared Dubai airport via Bitam Y718 flight plan route climb and maintain FL310 on Bit (2A) departure.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Bit (2A)")
                                                                )
                                                        )),
                                                new DialogueItem("UAE 626:", "UAE 626 cleared Dubai airport via Bitam Y718 flight plan route climb and maintain FL310 on Bit (2A) departure."),
                                                new DialogueItem("CD:", "Readback correct, contact Ground 121.8."),
                                                new DialogueItem("UAE 626:", "Switching to 121.8. Good day.")
                                        )
                                ),R.raw.example_sto1_4));

                        exampleItems.add(new ExampleItem("TOWER requests RELEASE for departing aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0552")
                                                                )
                                                        )),
                                                new DialogueItem("TWR:", "APP, TWR."),
                                                new DialogueItem("APP:", "TWR, APP, Go ahead."),
                                                new DialogueItem("TWR:", "Request release, UAE 626 , rwy24."),
                                                new DialogueItem("APP:", "Straight Out (STO) Departure, 5000. RWY24, Release."),
                                                new DialogueItem("TWR:", "Bravo Zulu."),
                                                new DialogueItem("APP:", "Delta Papa.")
                                        )
                                ),R.raw.example_sto1_5));

                        exampleItems.add(new ExampleItem("Departure is airborne, TOWER relays departure time to APP",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("Revised/Additional Instructions for Aircraft for departure."),
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "APP, TWR, Go ahead."),
                                                new DialogueItem("APP:", "Revise, for UAE 626 , straight out departure upon airborne to climb 3000."),
                                                new DialogueItem("TWR:", "Bravo Zulu."),
                                                new DialogueItem("APP:", "Delta Papa.")
                                        )
                                ),R.raw.example_sto1_6));

                        exampleItems.add(new ExampleItem("Relay Airborne Time to ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0555")
                                                                )
                                                        )),
                                                new DialogueItem("TWR:", "APP, TWR, Departure."),
                                                new DialogueItem("APP:", "TWR, APP, Go Ahead."),
                                                new DialogueItem("TWR:", "UAE 626  airborne at 0555."),
                                                new DialogueItem("APP:", "Delta Papa."),
                                                new DialogueItem("TWR:", "Bravo Zulu."),
                                                new DialogueItem("APP:", "Control, Approach, Departure."),
                                                new DialogueItem("ACC:", "Approach Control, Go Ahead."),
                                                new DialogueItem("APP:", "UAE 626  airborne at 0555."),
                                                new DialogueItem("ACC:", "Uniform Hotel."),
                                                new DialogueItem("APP:", "Delta Papa.")
                                        )
                                ),R.raw.example_sto1_7));

                        exampleItems.add(new ExampleItem("Initial Contact with Departing aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("UAE 626:", "APP, UAE 626 , airborne runway 24, on straight out departure climbing 3000."),
                                                new DialogueItem("APP:", "UAE 626 , APP, climb 5000, after passing 3000, turn left, intercept and track out R248, report when established."),
                                                new DialogueItem("UAE 626:", "Roger, climb 5000, after passing 3000, turn left, intercept and track out R248, will report when established.")
                                        )
                                ),R.raw.example_sto1_8));

                        exampleItems.add(new ExampleItem("Monitoring of Aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0559")
                                                                )
                                                        )),
                                                new DialogueItem("UAE 626:", "APP, UAE 626 , now established R117 of Manila VOR, maintaining 5000."),
                                                new DialogueItem("APP:", "UAE 626  request DME from Manila VOR."),
                                                new DialogueItem("UAE 626:", "UAE 626  we are 10 DME from Manila VOR."),
                                                new DialogueItem("(If cleared of traffic)"),
                                                new DialogueItem("APP:", "UAE 626 , APP, climb flight level 150 until further advised, after passing 8000, turn right direct Bitam."),
                                                new DialogueItem("UAE 626:", "Roger, leaving 5000 for flight level 150UFA, after passing 8000, will turn right direct Bitam.")
                                        )
                                ),R.raw.example_sto1_9));

                        exampleItems.add(new ExampleItem("Release Message to ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0603")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Control, APP, release."),
                                                new DialogueItem("ACC:", "APP, Control, go ahead."),
                                                new DialogueItem("APP:", "UAE 626 , approaching Econ climbing FL150UFA, release."),
                                                new DialogueItem("ACC:", "Oscar Sierra."),
                                                new DialogueItem("APP:", "India Hotel."),
                                                new DialogueItem("APP:", "UAE 626, APP, switch to 125.7."),
                                                new DialogueItem("UAE 626:", "Switching 125.7, good day!")
                                        )
                                ),R.raw.example_sto1_10));
                        break;
                    case 2:
                        exampleItems.add(new ExampleItem("Requesting Aerodrome Information",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "APP, TWR, Go ahead."),
                                                new DialogueItem("APP:", "Request Aerodrome Information."),
                                                new DialogueItem("TWR:", "RWY24, QNH1010, Visibility 8000km.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("RWY24")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Juliet Oscar."),
                                                new DialogueItem("TWR:", "Golf Hotel.")
                                        )
                                ),R.raw.example_sto2_1));

                        exampleItems.add(new ExampleItem("Pilot requests ATC Clearance for DEPARTURE from APP/Clearance Delivery (CD)",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0730")
                                                                )
                                                        )
                                                ),
                                                new DialogueItem("CEB 412:", "Clearance Delivery, this is CEB 412.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("CEB 412")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "CEB 412 , Clearance Delivery, Go Ahead."),
                                                new DialogueItem("CEB 412:", "We are an A320, requesting ATC Clearance to Los Angeles, FL360.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("A320"),
                                                                        new DialogueHighlight("Los Angeles"),
                                                                        new DialogueHighlight("FL360")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "CEB 412 , standby for clearance.")
                                        )
                                ),R.raw.example_sto2_2));

                        exampleItems.add(new ExampleItem("APP/CD request ATC Clearance from ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0732")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "Manila Control, Clearance Delivery."),
                                                new DialogueItem("ACC:", "Clearance Delivery, Manila Control, Go ahead."),
                                                new DialogueItem("CD:", "Request ATC clearance for CEB 412, an A320, destination Los Angeles, requesting FL360."),
                                                new DialogueItem("ACC:", "Clearance ready."),
                                                new DialogueItem("CD:", "Ready to copy."),
                                                new DialogueItem("APP:", "CEB 412 cleared Los Angeles International airport via Maglayog A32, Flight plan route climb and maintain FL150 until further advise.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("Maglayog A32"),
                                                                        new DialogueHighlight("FL150")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "CEB 412 cleared Los Angeles International airport via Maglayog A32, Flight plan route climb and maintain FL150 until further advise."),
                                                new DialogueItem("ACC:", "Readback correct. Echo Charlie.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Echo Charlie")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "Juliet Oscar.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Juliet Oscar")
                                                                )
                                                        ))
                                        )
                                ),R.raw.example_sto2_3));

                        exampleItems.add(new ExampleItem("APP/CD relays ATC Clearance to Pilot",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0733")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "CEB 412 , Clearance ready."),
                                                new DialogueItem("CEB 412:", "Ready to copy."),
                                                new DialogueItem("CD:", "CEB 412 cleared Los Angeles airport via Maglayog A32 flight plan route climb and maintain FL360 on Mag (2B) departure.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Mag (2B)")
                                                                )
                                                        )),
                                                new DialogueItem("CEB 412:", "CEB 412 cleared Los Angeles airport via Maglayog A32 flight plan route climb and maintain FL360 on Mag (2B) departure."),
                                                new DialogueItem("CD:", "Readback correct, contact Ground 121.8."),
                                                new DialogueItem("CEB 412:", "Switching to 121.8. Good day.")
                                        )
                                ),R.raw.example_sto2_4));

                        exampleItems.add(new ExampleItem("TOWER requests RELEASE for departing aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0735")
                                                                )
                                                        )),
                                                new DialogueItem("TWR:", "APP, TWR."),
                                                new DialogueItem("APP:", "TWR, APP, Go ahead."),
                                                new DialogueItem("TWR:", "Request release, CEB 412 , rwy24."),
                                                new DialogueItem("APP:", "Straight Out (STO) Departure, 5000. RWY24, Release."),
                                                new DialogueItem("TWR:", "Bravo Zulu."),
                                                new DialogueItem("APP:", "Juliet Oscar.")
                                        )
                                ),R.raw.example_sto2_5));

                        exampleItems.add(new ExampleItem("Departure is airborne, TOWER relays departure time to APP",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("Revised/Additional Instructions for Aircraft for departure."),
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "APP, TWR, Go ahead."),
                                                new DialogueItem("APP:", "Revise, for CEB 412 , straight out departure upon airborne to climb 3000."),
                                                new DialogueItem("TWR:", "Bravo Zulu."),
                                                new DialogueItem("APP:", "Juliet Oscar.")
                                        )
                                ),R.raw.example_sto2_6));

                        exampleItems.add(new ExampleItem("Relay Airborne Time to ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0740")
                                                                )
                                                        )),
                                                new DialogueItem("TWR:", "APP, TWR, Departure."),
                                                new DialogueItem("APP:", "TWR, APP, Go Ahead."),
                                                new DialogueItem("TWR:", "CEB 412  airborne at 0740."),
                                                new DialogueItem("APP:", "Juliet Oscar."),
                                                new DialogueItem("TWR:", "Bravo Zulu."),
                                                new DialogueItem("APP:", "Control, Approach, Departure."),
                                                new DialogueItem("ACC:", "Approach Control, Go Ahead."),
                                                new DialogueItem("APP:", "CEB 412  airborne at 0740."),
                                                new DialogueItem("ACC:", "Echo Charlie."),
                                                new DialogueItem("APP:", "Juliet Oscar.")
                                        )
                                ),R.raw.example_sto2_7));

                        exampleItems.add(new ExampleItem("Initial Contact with Departing aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("CEB 412:", "APP, CEB 412 , airborne runway 24, on straight out departure climbing 3000."),
                                                new DialogueItem("APP:", "CEB 412 , APP, climb 5000, after passing 3000, turn left, intercept and track out R117, report when established."),
                                                new DialogueItem("CEB 412:", "Roger, climb 5000, after passing 3000, turn left, intercept and track out R117, will report when established.")
                                        )
                                ),R.raw.example_sto2_8));

                        exampleItems.add(new ExampleItem("Monitoring of Aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0750")
                                                                )
                                                        )),
                                                new DialogueItem("CEB 412:", "APP, CEB 412 , now established R117 of Manila VOR, maintaining 5000."),
                                                new DialogueItem("APP:", "CEB 412  request DME from Manila VOR."),
                                                new DialogueItem("CEB 412:", "CEB 412  we are 20 DME from Manila VOR."),
                                                new DialogueItem("(If cleared of traffic)"),
                                                new DialogueItem("APP:", "CEB 412 , APP, climb flight level 150 until further advised, after passing 10000, turn right direct Maglayog."),
                                                new DialogueItem("CEB 412:", "Roger, leaving 5000 for flight level 150UFA, after passing 8000, will turn right direct Maglayog.")
                                        )
                                ),R.raw.example_sto2_9));

                        exampleItems.add(new ExampleItem("Release Message to ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("0751")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Control, APP, release."),
                                                new DialogueItem("ACC:", "APP, Control, go ahead."),
                                                new DialogueItem("APP:", "CEB 412 , approaching Econ climbing FL150UFA, release."),
                                                new DialogueItem("ACC:", "Oscar Sierra."),
                                                new DialogueItem("APP:", "India Hotel."),
                                                new DialogueItem("APP:", "CEB 412, APP, switch to 125.7"),
                                                new DialogueItem("CEB 412:", "Switching 125.7, good day!")
                                        )
                                ),R.raw.example_sto2_10));
                        break;
                    case 3:
                        exampleItems.add(new ExampleItem("Requesting Aerodrome Information",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "APP, TWR, Go ahead."),
                                                new DialogueItem("APP:", "Request Aerodrome Information."),
                                                new DialogueItem("TWR:", "RWY24, QNH1010, Visibility 8000km.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("RWY24")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Tango Yankee."),
                                                new DialogueItem("TWR:", "Golf Hotel.")
                                        )
                                ),R.raw.example_sto3_1));

                        exampleItems.add(new ExampleItem("Pilot requests ATC Clearance for DEPARTURE from APP/Clearance Delivery (CD)",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1124")
                                                                )
                                                        )
                                                ),
                                                new DialogueItem("GAP 500:", "Clearance Delivery, this is GAP 500.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("GAP 500")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "GAP 500, Clearance Delivery, Go Ahead."),
                                                new DialogueItem("GAP 500:", "We are an A340, requesting ATC Clearance to Tacloban, FL290.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("A340"),
                                                                        new DialogueHighlight("Tacloban"),
                                                                        new DialogueHighlight("FL290    ")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "GAP 500, standby for clearance.")
                                        )
                                ),R.raw.example_sto3_2));

                        exampleItems.add(new ExampleItem("APP/CD request ATC Clearance from ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1126")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "Manila Control, Clearance Delivery."),
                                                new DialogueItem("ACC:", "Clearance Delivery, Manila Control, Go ahead."),
                                                new DialogueItem("CD:", "Request ATC clearance for GAP 500, an A340, destination Tacloban, requesting FL290."),
                                                new DialogueItem("ACC:", "Clearance ready."),
                                                new DialogueItem("CD:", "Ready to copy."),
                                                new DialogueItem("APP:", "GAP 500 cleared Tacloban International airport via Palta E43, Flight plan route climb and maintain FL150 until further advise.",
                                                        new ArrayList<>(
                                                                Arrays.asList(
                                                                        new DialogueHighlight("Palta E43"),
                                                                        new DialogueHighlight("FL150")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "GAP 500 cleared Tacloban International airport via Palta E43, Flight plan route climb and maintain FL150 until further advise."),
                                                new DialogueItem("ACC:", "Readback correct. Quebec Delta.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Quebec Delta")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "Tango Yankee.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Tango Yankee")
                                                                )
                                                        ))
                                        )
                                ),R.raw.example_sto3_3));

                        exampleItems.add(new ExampleItem("APP/CD relays ATC Clearance to Pilot",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1127")
                                                                )
                                                        )),
                                                new DialogueItem("CD:", "GAP 500, Clearance ready."),
                                                new DialogueItem("GAP 500:", "Ready to copy."),
                                                new DialogueItem("CD:", "GAP 500 cleared Tacloban airport via Palta E43 flight plan route climb and maintain FL290 on Pal (2B) departure.",
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("Pal (2B)")
                                                                )
                                                        )),
                                                new DialogueItem("GAP 500:", "GAP 500 cleared Tacloban airport via Palta E43 flight plan route climb and maintain FL290 on Pal (2B) departure."),
                                                new DialogueItem("CD:", "Readback correct, contact Ground 121.8."),
                                                new DialogueItem("GAP 500:", "Switching to 121.8. Good day.")
                                        )
                                ),R.raw.example_sto3_4));

                        exampleItems.add(new ExampleItem("TOWER requests RELEASE for departing aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1129")
                                                                )
                                                        )),
                                                new DialogueItem("TWR:", "APP, TWR."),
                                                new DialogueItem("APP:", "TWR, APP, Go ahead."),
                                                new DialogueItem("TWR:", "Request release, GAP 500, rwy24."),
                                                new DialogueItem("APP:", "Straight Out (STO) Departure, 5000. RWY24, Release."),
                                                new DialogueItem("TWR:", "Bravo Zulu."),
                                                new DialogueItem("APP:", "Tango Yankee.")
                                        )
                                ),R.raw.example_sto3_5));

                        exampleItems.add(new ExampleItem("Departure is airborne, TOWER relays departure time to APP",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("Revised/Additional Instructions for Aircraft for departure."),
                                                new DialogueItem("APP:", "TWR, APP."),
                                                new DialogueItem("TWR:", "APP, TWR, Go ahead."),
                                                new DialogueItem("APP:", "Revise, for GAP 500 , straight out departure upon airborne to climb 3000."),
                                                new DialogueItem("TWR:", "Bravo Zulu."),
                                                new DialogueItem("APP:", "Tango Yankee.")
                                        )
                                ),R.raw.example_sto3_6));

                        exampleItems.add(new ExampleItem("Relay Airborne Time to ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1134")
                                                                )
                                                        )),
                                                new DialogueItem("TWR:", "APP, TWR, Departure."),
                                                new DialogueItem("APP:", "TWR, APP, Go Ahead."),
                                                new DialogueItem("TWR:", "GAP 500  airborne at 1134."),
                                                new DialogueItem("APP:", "Tango Yankee."),
                                                new DialogueItem("TWR:", "Bravo Zulu."),
                                                new DialogueItem("APP:", "Control, Approach, Departure."),
                                                new DialogueItem("ACC:", "Approach Control, Go Ahead."),
                                                new DialogueItem("APP:", "GAP 500  airborne at 1134."),
                                                new DialogueItem("ACC:", "Quebec Delta."),
                                                new DialogueItem("APP:", "Tango Yankee.")
                                        )
                                ),R.raw.example_sto3_7));

                        exampleItems.add(new ExampleItem("Initial Contact with Departing aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem("GAP 500:", "APP, GAP 500 , airborne runway 24, on straight out departure climbing 3000."),
                                                new DialogueItem("APP:", "GAP 500 , APP, climb 5000, after passing 3000, turn left, intercept and track out R081, report when established."),
                                                new DialogueItem("GAP 500:", "Roger, climb 5000, after passing 3000, turn left, intercept and track out R081, will report when established.")
                                        )
                                ),R.raw.example_sto3_8));

                        exampleItems.add(new ExampleItem("Monitoring of Aircraft",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1140")
                                                                )
                                                        )),
                                                new DialogueItem("GAP 500:", "APP, GAP 500 , now established R117 of Manila VOR, maintaining 5000."),
                                                new DialogueItem("APP:", "GAP 500  request DME from Manila VOR."),
                                                new DialogueItem("GAP 500:", "GAP 500  we are 15 DME from Manila VOR."),
                                                new DialogueItem("(If cleared of traffic)"),
                                                new DialogueItem("APP:", "GAP 500 , APP, climb flight level 150 until further advised, after passing 9000, turn right direct Palta"),
                                                new DialogueItem("GAP 500:", "Roger, leaving 5000 for flight level 150UFA, after passing 9000, will turn right direct Palta.")
                                        )
                                ),R.raw.example_sto3_9));

                        exampleItems.add(new ExampleItem("Release Message to ACC",
                                new ArrayList<>(
                                        Arrays.asList(
                                                new DialogueItem(
                                                        new ArrayList<>(
                                                                Collections.singletonList(
                                                                        new DialogueHighlight("1145")
                                                                )
                                                        )),
                                                new DialogueItem("APP:", "Control, APP, release."),
                                                new DialogueItem("ACC:", "APP, Control, go ahead."),
                                                new DialogueItem("APP:", "GAP 500 , approaching Econ climbing FL150UFA, release."),
                                                new DialogueItem("ACC:", "Oscar Sierra."),
                                                new DialogueItem("APP:", "India Hotel."),
                                                new DialogueItem("APP:", "GAP 500, APP, switch to 125.7"),
                                                new DialogueItem("GAP 500:", "Switching 125.7, good day!")
                                        )
                                ),R.raw.example_sto3_10));
                        break;
                }
                break;
        }

        //Returns Created ArrayList
        return exampleItems;
    }

    private void setupFlightStrip() {
        ArrayList<TextView> flightStripTextViews = new ArrayList<>();
        ArrayList<View> flightStripOtherViews = new ArrayList<>();
        ArrayList<String> flightStripTexts = new ArrayList<>();

        switch (startupCode) {
            case ApproachControl.EXAMPLE_DEPARTURE:
                //Set CardView Visibility
                flightStripDeparture.setVisibility(View.VISIBLE);
                flightStripArrival.setVisibility(View.GONE);
                flightStripStraightOut.setVisibility(View.GONE);

                //Setup TextViews
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent1));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent2));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent3));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent4));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent5));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent6));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent7));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent8));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent9));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent10));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent11));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent12));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent13));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent14));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent15));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent16));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent17));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent18));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent19));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent20));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent22));
                flightStripTextViews.add(findViewById(R.id.exampleFSDepartureContent23));

                //Setup OtherViews
                flightStripOtherViews.add(findViewById(R.id.exampleFSDepartureContent21));

                //Setup Content
                switch (displayPageNumber) {
                    case 1:
                        flightStripTexts.add("PAL 321");
                        flightStripTexts.add("A380");
                        flightStripTexts.add("FL350");
                        flightStripTexts.add("RPLL");
                        flightStripTexts.add("VHHH");
                        flightStripTexts.add("Tucaban W10 FPR");
                        flightStripTexts.add("FL150 UFA");
                        flightStripTexts.add("TUC (2A)");
                        flightStripTexts.add("5000");
                        flightStripTexts.add("8000");
                        flightStripTexts.add("FL150UFA");
                        flightStripTexts.add("06");
                        flightStripTexts.add("0912");
                        flightStripTexts.add("AA");
                        flightStripTexts.add("ZY");
                        flightStripTexts.add("0915");
                        flightStripTexts.add("0916");
                        flightStripTexts.add("ZY");
                        flightStripTexts.add("0918");
                        flightStripTexts.add("0920");
                        flightStripTexts.add("R176");
                        flightStripTexts.add("0934");
                        break;
                    case 2:
                        flightStripTexts.add("UAL 490");
                        flightStripTexts.add("B747");
                        flightStripTexts.add("FL300");
                        flightStripTexts.add("RPLL");
                        flightStripTexts.add("KSFO");
                        flightStripTexts.add("Maglayog G656 Y777 FPR");
                        flightStripTexts.add("FL150 UFA");
                        flightStripTexts.add("Mag (2A)");
                        flightStripTexts.add("5000");
                        flightStripTexts.add("9000");
                        flightStripTexts.add("FL150UFA");
                        flightStripTexts.add("06");
                        flightStripTexts.add("1223");
                        flightStripTexts.add("ND");
                        flightStripTexts.add("PA");
                        flightStripTexts.add("1228");
                        flightStripTexts.add("1229");
                        flightStripTexts.add("PA");
                        flightStripTexts.add("1232");
                        flightStripTexts.add("1234");
                        flightStripTexts.add("R081");
                        flightStripTexts.add("1245");
                        break;
                    case 3:
                        flightStripTexts.add("CAL 123");
                        flightStripTexts.add("A350");
                        flightStripTexts.add("FL320");
                        flightStripTexts.add("RPLL");
                        flightStripTexts.add("ZGSG");
                        flightStripTexts.add("Tucaban T555 FPR");
                        flightStripTexts.add("FL150 UFA");
                        flightStripTexts.add("Tuc (2A)");
                        flightStripTexts.add("5000");
                        flightStripTexts.add("7000");
                        flightStripTexts.add("FL150UFA");
                        flightStripTexts.add("06");
                        flightStripTexts.add("1709");
                        flightStripTexts.add("KS");
                        flightStripTexts.add("RJ");
                        flightStripTexts.add("1712");
                        flightStripTexts.add("1715");
                        flightStripTexts.add("RJ");
                        flightStripTexts.add("1719");
                        flightStripTexts.add("1721");
                        flightStripTexts.add("R176");
                        flightStripTexts.add("1730");
                        break;
                }
                break;
            case ApproachControl.EXAMPLE_ARRIVAL:
                //Set CardView Visibility
                flightStripDeparture.setVisibility(View.GONE);
                flightStripArrival.setVisibility(View.VISIBLE);
                flightStripStraightOut.setVisibility(View.GONE);

                //Setup TextViews
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent1));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent2));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent3));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent4));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent5));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent6));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent7));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent8));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent9));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent10));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent11));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent12));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent13));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent14));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent15));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent16));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent17));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent18));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent19));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent20));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent21));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent22));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent23));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent24));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent25));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent26));
                flightStripTextViews.add(findViewById(R.id.exampleFSArrivalContent27));

                //Setup Content
                switch (displayPageNumber) {
                    case 1:
                        flightStripTexts.add("SRQ 512");
                        flightStripTexts.add("B777");
                        flightStripTexts.add("RJTT");
                        flightStripTexts.add("RPLL");
                        flightStripTexts.add("12");
                        flightStripTexts.add("41");
                        flightStripTexts.add("FL150");
                        flightStripTexts.add("110");
                        flightStripTexts.add("60");
                        flightStripTexts.add("Vamil");
                        flightStripTexts.add("1147");
                        flightStripTexts.add("1228");
                        flightStripTexts.add("1230");
                        flightStripTexts.add("1230");
                        flightStripTexts.add("Volets");
                        flightStripTexts.add("38");
                        flightStripTexts.add("38");
                        flightStripTexts.add(" ");
                        flightStripTexts.add(" ");
                        flightStripTexts.add(" ");
                        flightStripTexts.add("39");
                        flightStripTexts.add("41");
                        flightStripTexts.add("41");
                        flightStripTexts.add("43");
                        flightStripTexts.add("44");
                        flightStripTexts.add("24");
                        flightStripTexts.add("1242");
                        break;
                    case 2:
                        flightStripTexts.add("APG 009");
                        flightStripTexts.add("A330");
                        flightStripTexts.add("OKBK");
                        flightStripTexts.add("RPLL");
                        flightStripTexts.add("00");
                        flightStripTexts.add("58");
                        flightStripTexts.add("FL150");
                        flightStripTexts.add("110");
                        flightStripTexts.add("60");
                        flightStripTexts.add("Letem");
                        flightStripTexts.add("2359");
                        flightStripTexts.add("0045");
                        flightStripTexts.add("47");
                        flightStripTexts.add("47");
                        flightStripTexts.add("Kore");
                        flightStripTexts.add("49");
                        flightStripTexts.add("49");
                        flightStripTexts.add("Gabon");
                        flightStripTexts.add("54");
                        flightStripTexts.add("54");
                        flightStripTexts.add("54");
                        flightStripTexts.add("58");
                        flightStripTexts.add("58");
                        flightStripTexts.add("00");
                        flightStripTexts.add("01");
                        flightStripTexts.add("24");
                        flightStripTexts.add("0059");
                        break;
                    case 3:
                        flightStripTexts.add("SIA 888");
                        flightStripTexts.add("B737");
                        flightStripTexts.add("WSSS");
                        flightStripTexts.add("RPLL");
                        flightStripTexts.add("07");
                        flightStripTexts.add("08");
                        flightStripTexts.add("FL150");
                        flightStripTexts.add("110");
                        flightStripTexts.add("60");
                        flightStripTexts.add("Calay");
                        flightStripTexts.add("0630");
                        flightStripTexts.add("0653");
                        flightStripTexts.add("54");
                        flightStripTexts.add("54");
                        flightStripTexts.add("Yanib");
                        flightStripTexts.add("59");
                        flightStripTexts.add("59");
                        flightStripTexts.add("Dali");
                        flightStripTexts.add("03");
                        flightStripTexts.add("03");
                        flightStripTexts.add("03");
                        flightStripTexts.add("08");
                        flightStripTexts.add("08");
                        flightStripTexts.add("10");
                        flightStripTexts.add("11");
                        flightStripTexts.add("24");
                        flightStripTexts.add("0709");
                        break;
                }
                break;
            case ApproachControl.EXAMPLE_STO:
                //Set CardView Visibility
                flightStripDeparture.setVisibility(View.GONE);
                flightStripArrival.setVisibility(View.GONE);
                flightStripStraightOut.setVisibility(View.VISIBLE);

                //Setup TextViews
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent1));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent2));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent3));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent4));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent5));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent6));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent7));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent8));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent9));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent11));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent12));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent13));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent14));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent15));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent16));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent17));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent18));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent19));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent20));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent21));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent22));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent23));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent24));
                flightStripTextViews.add(findViewById(R.id.exampleFSSTOContent25));

                //Setup OtherViews
                flightStripOtherViews.add(findViewById(R.id.exampleFSSTOContent10));

                //Setup Content
                switch (displayPageNumber) {
                    case 1:
                        flightStripTexts.add("UAE 626");
                        flightStripTexts.add("B777");
                        flightStripTexts.add("FL310");
                        flightStripTexts.add("RPLL");
                        flightStripTexts.add("OMDB");
                        flightStripTexts.add("Bitam Y718 FPR");
                        flightStripTexts.add("5000LT -> R248");
                        flightStripTexts.add("8000RT -D> R289");
                        flightStripTexts.add("FL150 UFA");
                        flightStripTexts.add("STO ");
                        flightStripTexts.add("5000");
                        flightStripTexts.add("3000");
                        flightStripTexts.add("Bit (2A)");
                        flightStripTexts.add("10 DME | 0559");
                        flightStripTexts.add("24");
                        flightStripTexts.add("0546");
                        flightStripTexts.add("UH");
                        flightStripTexts.add("DP");
                        flightStripTexts.add("0549");
                        flightStripTexts.add("0550");
                        flightStripTexts.add("DP");
                        flightStripTexts.add("0552");
                        flightStripTexts.add("0555");
                        flightStripTexts.add("0603");
                        break;
                    case 2:
                        flightStripTexts.add("CEB 412");
                        flightStripTexts.add("A320");
                        flightStripTexts.add("FL360");
                        flightStripTexts.add("RPLL");
                        flightStripTexts.add("KLAX");
                        flightStripTexts.add("Maglayog A32 FPR");
                        flightStripTexts.add("5000LT -> R117");
                        flightStripTexts.add("8000RT -D> R081");
                        flightStripTexts.add("FL150 UFA");
                        flightStripTexts.add("STO ");
                        flightStripTexts.add("5000");
                        flightStripTexts.add("3000");
                        flightStripTexts.add("Mag (2B)");
                        flightStripTexts.add("20 DME | 0750");
                        flightStripTexts.add("24");
                        flightStripTexts.add("0730");
                        flightStripTexts.add("EC");
                        flightStripTexts.add("JO");
                        flightStripTexts.add("0732");
                        flightStripTexts.add("0733");
                        flightStripTexts.add("JO");
                        flightStripTexts.add("0735");
                        flightStripTexts.add("0740");
                        flightStripTexts.add("0751");
                        break;
                    case 3:
                        flightStripTexts.add("GAP 500");
                        flightStripTexts.add("A340");
                        flightStripTexts.add("FL290");
                        flightStripTexts.add("RPLL");
                        flightStripTexts.add("RPVA");
                        flightStripTexts.add("Palta E43 FPR");
                        flightStripTexts.add("5000LT -> R081");
                        flightStripTexts.add("9000RT -D> Palta");
                        flightStripTexts.add("FL150 UFA");
                        flightStripTexts.add("STO ");
                        flightStripTexts.add("5000");
                        flightStripTexts.add("3000");
                        flightStripTexts.add("Pal (2B)");
                        flightStripTexts.add("15 DME | 1140");
                        flightStripTexts.add("24");
                        flightStripTexts.add("1124");
                        flightStripTexts.add("QD");
                        flightStripTexts.add("TY");
                        flightStripTexts.add("1126");
                        flightStripTexts.add("1127");
                        flightStripTexts.add("TY");
                        flightStripTexts.add("1129");
                        flightStripTexts.add("1134");
                        flightStripTexts.add("1145");
                        break;
                }
                break;
        }

        //Set Text to TextViews
        if (flightStripTextViews.size() == flightStripTexts.size()) {
            for (int index = 0; index < flightStripTextViews.size(); index++) {
                if (flightStripTextViews.get(index) != null && flightStripTexts.get(index) != null) {
                    flightStripTextViews.get(index).setText(flightStripTexts.get(index));
                }
            }
        }

        //Set Flight Strip Views
        this.flightStripTextViews = flightStripTextViews;
        this.flightStripOtherViews = flightStripOtherViews;
    }

    public void approachControl_examples_pseudoTMA(View view) {
        startActivity(new Intent(this, PseudoTMA.class));
    }

    public void approachControl_examples_flightStrip(View view) {
        //Shows or Hides Flight Strip
        flightStripVisibility = setFlightStripVisibility(findViewById(R.id.exampleFSContent),findViewById(R.id.exampleFSLayout));
    }

    public void approachControl_examples_swap_previous(View view) {
        if (displayPageNumber > 1) {
            //Update Page
            displayPageNumber -= 1;
            setupPage();

            //Adjusts Flight Strip Content
            if (flightStripVisibility == View.VISIBLE) {
                setupFlightStrip();
            }

            //Update Flight Strip Data on Adapter
            exampleItemAdapter.setFlightStripTextViews(flightStripTextViews);
            exampleItemAdapter.setFlightStripOtherViews(flightStripOtherViews);
            exampleItemAdapter.setStartupCode(startupCode);
            exampleItemAdapter.manageFlightStrip(-1);

            //Update ExampleItems
            exampleItems.clear();
            exampleItems = setupExampleItems();
            exampleItemAdapter.updateData(exampleItems);
        }
    }

    public void approachControl_examples_swap_next(View view) {
        if (displayPageNumber < 3) {
            //Update Page
            displayPageNumber += 1;
            setupPage();

            //Adjusts Flight Strip Content
            if (flightStripVisibility == View.VISIBLE) {
                setupFlightStrip();
            }

            //Update Flight Strip Data on Adapter
            exampleItemAdapter.setFlightStripTextViews(flightStripTextViews);
            exampleItemAdapter.setFlightStripOtherViews(flightStripOtherViews);
            exampleItemAdapter.setStartupCode(startupCode);
            exampleItemAdapter.manageFlightStrip(-1);

            //Update ExampleItems
            exampleItems.clear();
            exampleItems = setupExampleItems();
            exampleItemAdapter.updateData(exampleItems);
        }
    }
}