package com.example.aircomms.airportCode;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.MainActivity;
import com.example.aircomms.R;
import com.example.aircomms.SharedPref;

import java.util.ArrayList;
import java.util.Comparator;

public class Display extends AppCompatActivity {
    private ArrayList<DisplayItem> displayItemArrayList;
    private RecyclerView displayRecyclerView;
    private String startupCode;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Display.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Display.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //Fetches Startup Code from Intent
        Bundle startup = getIntent().getExtras();
        if (startup != null) {
            startupCode = startup.getString("startupCode");
        }

        //Setup Title and Description
        TextView title, description;
        title = findViewById(R.id.airportCode_title);
        description = findViewById(R.id.airportCode_description);
        setupPage(startupCode,title,description);

        //Setup RecyclerView and Items
        displayRecyclerView = findViewById(R.id.airportCode_recyclerView);
        displayItemArrayList = new ArrayList<>();
        setDisplayItems(startupCode);
        setAdapter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void setupPage(@NonNull String startupCode, TextView title, TextView description) {
        switch (startupCode) {
            case AirportCode.DOMESTIC_PRINCIPAL:
                title.setText("Domestic / Principal");
                description.setText("This is a list of Domestic Principal Airports along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
            case AirportCode.DOMESTIC_COMMUNITY:
                title.setText("Domestic / Community");
                description.setText("This is a list of Domestic Community Airports along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
            case AirportCode.INTERNATIONAL_PHILIPPINES:
                title.setText("International / Philippines");
                description.setText("This is a list of International Airports in the Philippines along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
            case AirportCode.INTERNATIONAL_CHINA:
                title.setText("International / China");
                description.setText("This is a list of International Airports in China along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
            case AirportCode.INTERNATIONAL_INDONESIA:
                title.setText("International / Indonesia");
                description.setText("This is a list of International Airports in Indonesia along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
            case AirportCode.INTERNATIONAL_MALAYSIA:
                title.setText("International / Malaysia");
                description.setText("This is a list of International Airports in Malaysia along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
            case AirportCode.INTERNATIONAL_SINGAPORE:
                title.setText("International / Singapore");
                description.setText("This is a list of International Airports in Singapore along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
            case AirportCode.INTERNATIONAL_JAPAN:
                title.setText("International / Japan");
                description.setText("This is a list of International Airports in Japan along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
            case AirportCode.INTERNATIONAL_TAIWAN:
                title.setText("International / China");
                description.setText("This is a list of International Airports in Taiwan along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
            case AirportCode.INTERNATIONAL_AUSTRALIA:
                title.setText("International / Australia");
                description.setText("This is a list of International Airports in Australia along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
            case AirportCode.INTERNATIONAL_THAILAND:
                title.setText("International / Thailand");
                description.setText("This is a list of International Airports in Thailand along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
            case AirportCode.INTERNATIONAL_MIDDLE_EAST:
                title.setText("International / Middle East");
                description.setText("This is a list of International Airports in the Middle East along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
            default:
                title.setText("Airport Code");
                description.setText("This is a list of Airports along with their corresponding International Civil Aviation Organization (ICAO) Code and Location.");
                break;
        }
    }

    private void setAdapter() {
        DisplayAdapter adapter = new DisplayAdapter(displayItemArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        displayRecyclerView.setLayoutManager(layoutManager);
        displayRecyclerView.setItemAnimator(new DefaultItemAnimator());
        displayRecyclerView.setAdapter(adapter);
    }

    private void setDisplayItems(@NonNull String startupCode) {
        //Add Items to Display List depending on Startup Code
        switch (startupCode) {
            case AirportCode.DOMESTIC_PRINCIPAL:
                displayItemArrayList.add(new DisplayItem("Bacolod-Silay Airport", "RPVB", "Negros Occidental"));
                displayItemArrayList.add(new DisplayItem("Bancasi (Butuan) Airport", "RPME", "Butuan"));
                displayItemArrayList.add(new DisplayItem("Cotabato Airport", "RPMC", "Butuan"));
                displayItemArrayList.add(new DisplayItem("Dipolog Airport", "RPMG", "Zamboanga Del Norte"));
                displayItemArrayList.add(new DisplayItem("Sibulan (Dumaguete) Airport", "RPVD", "Negros Oriental"));
                displayItemArrayList.add(new DisplayItem("Laguindingan Airport", "RPMY", "Misamis Oriental"));
                displayItemArrayList.add(new DisplayItem("Legaspi Airport", "RPLP", "Legazpi, Albay"));
                displayItemArrayList.add(new DisplayItem("Naga Airport", "RPUN", "Pili, Camarines Sur"));
                displayItemArrayList.add(new DisplayItem("Pagadian Airport", "RPMP", "Pagadian"));
                displayItemArrayList.add(new DisplayItem("Roxas Airport ", "RPVR", "Roxas, Capiz"));
                displayItemArrayList.add(new DisplayItem("San Jose Airport", "RPUH", "Occidental Mindoro"));
                displayItemArrayList.add(new DisplayItem("Daniel Romualdez Airport", "RPVA", "Tacloban, Leyte"));
                displayItemArrayList.add(new DisplayItem("Tuguegarao Airport ", "RPUT", "Tuguegarao, Cagayan"));
                displayItemArrayList.add(new DisplayItem("Evelio Javier Airport", "RPVS", "Antique"));
                displayItemArrayList.add(new DisplayItem("Loakan (Baguio) Airport", "RPUB", "Baguio"));
                displayItemArrayList.add(new DisplayItem("Basco Airport", "RPUO", "Basco, Batanes"));
                displayItemArrayList.add(new DisplayItem("Francisco B. Reyes Airport", "RPVV", "Coron, Palawan"));
                displayItemArrayList.add(new DisplayItem("Calbayog Airport", "RPVC", "Calbayog, Samar"));
                displayItemArrayList.add(new DisplayItem("Camiguin Airport", "RPMH", "Mambajao, Camiguin"));
                displayItemArrayList.add(new DisplayItem("Catarman National Airport", "RPVF", "Northern Samar"));
                displayItemArrayList.add(new DisplayItem("Godofredo P. Ramos Airport", "RPVE", "Boracay, Aklan"));
                displayItemArrayList.add(new DisplayItem("Cuyo Airport", "RPLO", "Palawan"));
                displayItemArrayList.add(new DisplayItem("Jolo Airport", "RPMJ", "Jolo, Sulu"));
                displayItemArrayList.add(new DisplayItem("Marinduque Airport", "RPUW", "Marinduque"));
                displayItemArrayList.add(new DisplayItem("Moises R. Espinosa Airport", "RPVJ", "Masbate"));
                displayItemArrayList.add(new DisplayItem("Ormoc Airport", "RPVO", "Ormoc, Leyte"));
                displayItemArrayList.add(new DisplayItem("Sayak (Siargao) Airport", "RPNS", "Siargao"));
                displayItemArrayList.add(new DisplayItem("Surigao Airport", "RPMS", "Surigao del Norte"));
                displayItemArrayList.add(new DisplayItem("Tugdan Airport", "RPVU", "Romblon"));
                displayItemArrayList.add(new DisplayItem("Tandag Airport", "RPMW", "Surigao del Sur"));
                displayItemArrayList.add(new DisplayItem("Sanga-Sanga Airport", "RPMN", "Tawi-Tawi"));
                displayItemArrayList.add(new DisplayItem("Virac Airport ", "RPUV", "Catanduanes"));
                break;
            case AirportCode.DOMESTIC_COMMUNITY:
                displayItemArrayList.add(new DisplayItem("Alabat Airport", "RPLY", "Perez, Quezon"));
                displayItemArrayList.add(new DisplayItem("Allah Valley Airport", "RPMA", "South Cotabato"));
                displayItemArrayList.add(new DisplayItem("Bagabag Airport", "RPUZ", "Nueva Vizcaya"));
                displayItemArrayList.add(new DisplayItem("Dr. Juan C. Angara Airport", "RPUR", "Baler, Aurora"));
                displayItemArrayList.add(new DisplayItem("Bantayan Airport", "RPSB", "Cebu"));
                displayItemArrayList.add(new DisplayItem("Biliran Airport", "RPVQ", "Naval, Biliran"));
                displayItemArrayList.add(new DisplayItem("Bislig Airport", "RPMF", "Surigao del Sur"));
                displayItemArrayList.add(new DisplayItem("Borongan Airport", "RPVW", "Eastern Samar"));
                displayItemArrayList.add(new DisplayItem("Bulan Airport", "RPUU", "Bulan, Sorsogon"));
                displayItemArrayList.add(new DisplayItem("Calapan Airport", "RPUK", "Oriental Mindoro"));
                displayItemArrayList.add(new DisplayItem("Catbalogan Airport", "RPVY", "Catbalogan, Samar"));
                displayItemArrayList.add(new DisplayItem("Cauayan Airport", "RPUY", "Cauayan, Isabela"));
                displayItemArrayList.add(new DisplayItem("Bagasbas Airport", "RPUD", "Daet, Camarines Norte"));
                displayItemArrayList.add(new DisplayItem("Guiuan Airport", "RPVG", "Eastern Samar"));
                displayItemArrayList.add(new DisplayItem("Hilongos Airport", "RPVH", "Hilongos, Leyte"));
                displayItemArrayList.add(new DisplayItem("Iba Airport", "RPUI", "Iba, Zambales"));
                displayItemArrayList.add(new DisplayItem("Maria Cristina Airport", "RPMI", "Lanao del Norte"));
                displayItemArrayList.add(new DisplayItem("Ipil Airport", "RPMV", "Zamboanga Sibugay"));
                displayItemArrayList.add(new DisplayItem("Jorge Abad Airport", "RPLT", "Itbayat, Batanes"));
                displayItemArrayList.add(new DisplayItem("Jomalig Airport", "RPLJ", "Jomalig, Quezon"));
                displayItemArrayList.add(new DisplayItem("Liloy Airport", "RPMX", "Zamboanga del Norte"));
                displayItemArrayList.add(new DisplayItem("Lingayen Airport", "RPUG", "Lingayen, Pangasinan"));
                displayItemArrayList.add(new DisplayItem("Lubang Airport", "RPLU", "Occidental Mindoro"));
                displayItemArrayList.add(new DisplayItem("Panan-awan (Maasin) Airport", "RPSM", "Southern Leyte"));
                displayItemArrayList.add(new DisplayItem("Malabang Airport", "RPMM", "Lanao del Sur"));
                displayItemArrayList.add(new DisplayItem("Mamburao Airport", "RPUM", "Occidental Mindoro"));
                displayItemArrayList.add(new DisplayItem("Wasig (Mansalay) Airport", "RPLG", "Oriental Mindoro"));
                displayItemArrayList.add(new DisplayItem("Cagayan de Sulu Airport", "RPMU", "Mapun, Tawi-Taw"));
                displayItemArrayList.add(new DisplayItem("Mati Airport", "RPMQ", "Mati, Davao Oriental"));
                displayItemArrayList.add(new DisplayItem("Labo (Ozamis) Airport", "RPMO", "Misamis Occidental"));
                displayItemArrayList.add(new DisplayItem("Palanan Airport", "RPLN", "Palanan, Isabela"));
                displayItemArrayList.add(new DisplayItem("Pinamalayan Airport", "RPLA", "Occidental Mindoro"));
                displayItemArrayList.add(new DisplayItem("Plaridel Airport", "RPUX", "Plaridel, Bulacan"));
                displayItemArrayList.add(new DisplayItem("Rosales Airport", "RPLR", "Rosales, Pangasinan"));
                displayItemArrayList.add(new DisplayItem("San Fernando Airport", "RPUS", "La Union"));
                displayItemArrayList.add(new DisplayItem("Siocon Airport", "RPNO", "Zamboanga del Norte"));
                displayItemArrayList.add(new DisplayItem("Siquijor Airport", "RPVZ", "Siquijor"));
                displayItemArrayList.add(new DisplayItem("Sorsogon Airport", "RPLZ", "Sorsogon"));
                displayItemArrayList.add(new DisplayItem("Ubay Airport", "RPSN", "Ubay, Bohol"));
                displayItemArrayList.add(new DisplayItem("Mindoro (Vigan) Airport", "RPUQ", "Vigan, Ilocos Sur"));
                break;
            case AirportCode.INTERNATIONAL_PHILIPPINES:
                displayItemArrayList.add(new DisplayItem("Clark International Airport", "RPLC", "Pampanga"));
                displayItemArrayList.add(new DisplayItem("Mactan-Cebu International Airport", "RPVM", "Cebu"));
                displayItemArrayList.add(new DisplayItem("Francisco Bangoy International Airport", "RPMD", "Davao"));
                displayItemArrayList.add(new DisplayItem("General Santos International Airport", "RPMR", "General Santos"));
                displayItemArrayList.add(new DisplayItem("Iloilo International Airport", "RPVI", "Iloilo"));
                displayItemArrayList.add(new DisplayItem("Kalibo International Airport", "RPVK", "Aklan"));
                displayItemArrayList.add(new DisplayItem("Laoag International Airport", "RPLI", "Laoag, Ilocos Norte"));
                displayItemArrayList.add(new DisplayItem("Ninoy Aquino International Airport", "RPLL", "Pasay, Metro Manila"));
                displayItemArrayList.add(new DisplayItem("Bohol Panglao International Airport", "RPSP", "Bohol"));
                displayItemArrayList.add(new DisplayItem("Puerto Princesa International Airport", "RPVP", "Puerto Princesa"));
                displayItemArrayList.add(new DisplayItem("Subic Bay International Airport", "RPLB", "Morong, Bataan"));
                displayItemArrayList.add(new DisplayItem("Zamboanga International Airport", "RPMZ", "Zamboanga City"));
                break;
            case AirportCode.INTERNATIONAL_CHINA:
                displayItemArrayList.add(new DisplayItem("Beijing Airport", "ZBAA", "China"));
                displayItemArrayList.add(new DisplayItem("Guangzhou", "ZGGG", "China"));
                displayItemArrayList.add(new DisplayItem("Shenzhen", "ZGSZ", "China"));
                displayItemArrayList.add(new DisplayItem("Xiamen", "ZSAM", "China"));
                displayItemArrayList.add(new DisplayItem("Phudong", "ZSPD", "China"));
                displayItemArrayList.add(new DisplayItem("Shanghai", "ZSSS", "China"));
                displayItemArrayList.add(new DisplayItem("Macau", "VMMC", "China"));
                break;
            case AirportCode.INTERNATIONAL_INDONESIA:
                displayItemArrayList.add(new DisplayItem("Ujung Pandang Airport", "WAAA", "Indonesia"));
                displayItemArrayList.add(new DisplayItem("Bali Airport", "WADD", "Indonesia"));
                displayItemArrayList.add(new DisplayItem("Manado Airport", "WAMM", "Indonesia"));
                displayItemArrayList.add(new DisplayItem("Surabaya Airport", "WARR", "Indonesia"));
                displayItemArrayList.add(new DisplayItem("Jakarta", "WIII", "Indonesia"));
                break;
            case AirportCode.INTERNATIONAL_MALAYSIA:
                displayItemArrayList.add(new DisplayItem("Kota kinabalu Airport", "WBKK", "Malaysia"));
                displayItemArrayList.add(new DisplayItem("Brunei Airport", "WBSB", "Malaysia"));
                displayItemArrayList.add(new DisplayItem("Kuala Lumpur", "WMKK", "Malaysia"));
                displayItemArrayList.add(new DisplayItem("Kuantan", "WMKD", "Malaysia"));
                displayItemArrayList.add(new DisplayItem("Langkawi Airport", "WMKL", "Malaysia"));
                displayItemArrayList.add(new DisplayItem("Penang", "WMKP", "Malaysia"));
                displayItemArrayList.add(new DisplayItem("Subang", "WMSA", "Malaysia"));
                break;
            case AirportCode.INTERNATIONAL_SINGAPORE:
                displayItemArrayList.add(new DisplayItem("Paya Lebar", "WSAP", "Singapore"));
                displayItemArrayList.add(new DisplayItem("Seletar", "WSSL", "Singapore"));
                displayItemArrayList.add(new DisplayItem("Singapore", "WSSS", "Singapore"));
                break;
            case AirportCode.INTERNATIONAL_JAPAN:
                displayItemArrayList.add(new DisplayItem("Tokyo", "RJAA", "Japan"));
                displayItemArrayList.add(new DisplayItem("Kansai", "RJBD", "Japan"));
                displayItemArrayList.add(new DisplayItem("Sapporo", "RJCC", "Japan"));
                displayItemArrayList.add(new DisplayItem("Fukuoka", "RJFF", "Japan"));
                displayItemArrayList.add(new DisplayItem("Chubu", "RJGG", "Japan"));
                displayItemArrayList.add(new DisplayItem("Sendai", "RJSS", "Japan"));
                displayItemArrayList.add(new DisplayItem("Tokyo", "RJTT", "Japan"));
                displayItemArrayList.add(new DisplayItem("Yokota", "RJTY", "Japan"));
                displayItemArrayList.add(new DisplayItem("Kadena", "RODN", "Japan"));
                displayItemArrayList.add(new DisplayItem("Futenma", "ROTM", "Japan"));
                break;
            case AirportCode.INTERNATIONAL_TAIWAN:
                displayItemArrayList.add(new DisplayItem("Tainan", "RCNN", "Taiwan"));
                displayItemArrayList.add(new DisplayItem("Taipei", "RCTP", "Taiwan"));
                displayItemArrayList.add(new DisplayItem("Gaoxiong/Kaohsiung", "RCKH", "Taiwan"));
                break;
            case AirportCode.INTERNATIONAL_AUSTRALIA:
                displayItemArrayList.add(new DisplayItem("Brisbane", "YBBN", "Australia"));
                displayItemArrayList.add(new DisplayItem("Cairns", "YBCS", "Australia"));
                displayItemArrayList.add(new DisplayItem("Melbourne", "YMML", "Australia"));
                displayItemArrayList.add(new DisplayItem("Adelaide", "YPAD", "Australia"));
                displayItemArrayList.add(new DisplayItem("Perth", "YPPH", "Australia"));
                displayItemArrayList.add(new DisplayItem("Darwin", "YPDN", "Australia"));
                displayItemArrayList.add(new DisplayItem("Sydney", "YSSY", "Australia"));
                break;
            case AirportCode.INTERNATIONAL_THAILAND:
                displayItemArrayList.add(new DisplayItem("Bangkok", "VTBS", "Thailand"));
                displayItemArrayList.add(new DisplayItem("Utaphao", "VTBU", "Thailand"));
                displayItemArrayList.add(new DisplayItem("Chang Mai", "VTCC", "Thailand"));
                displayItemArrayList.add(new DisplayItem("Phuket", "VTSP", "Thailand"));
                displayItemArrayList.add(new DisplayItem("Hanoi", "VVNB", "Thailand"));
                displayItemArrayList.add(new DisplayItem("Hochiminh", "VVTS", "Thailand"));
                displayItemArrayList.add(new DisplayItem("Yangon", "VYYY", "Thailand"));
                break;
            case AirportCode.INTERNATIONAL_MIDDLE_EAST:
                displayItemArrayList.add(new DisplayItem("Bahrain", "OBBI", "Middle East"));
                displayItemArrayList.add(new DisplayItem("Dharan", "OEDR", "Middle East"));
                displayItemArrayList.add(new DisplayItem("Jeddah", "OEJN", "Middle East"));
                displayItemArrayList.add(new DisplayItem("Riyadh", "OERK", "Middle East"));
                displayItemArrayList.add(new DisplayItem("Kuwait", "OKBK", "Middle East"));
                displayItemArrayList.add(new DisplayItem("Abu Dhabi", "OMAA", "Middle East"));
                displayItemArrayList.add(new DisplayItem("Dubai", "OMDB", "Middle East"));
                displayItemArrayList.add(new DisplayItem("Sharjah", "OMSJ", "Middle East"));
                displayItemArrayList.add(new DisplayItem("Muscat", "OOMS", "Middle East"));
                displayItemArrayList.add(new DisplayItem("Karachi", "OPKC", "Middle East"));
                displayItemArrayList.add(new DisplayItem("Doha", "OTBD", "Middle East"));
                break;
            default:
                displayItemArrayList.add(new DisplayItem("N/A", "N/A", "N/A"));
                break;
        }

        //Sort Display List into Alphabetical Order
        if (displayItemArrayList.size()>1) {
            displayItemArrayList.sort(new Comparator<DisplayItem>() {
                @Override
                public int compare(DisplayItem displayItem, DisplayItem t1) {
                    String name1 = displayItem.getAirportName();
                    String name2 = t1.getAirportName();
                    return name1.compareToIgnoreCase(name2);
                }
            });
        }
    }

    public void airportCode_display_back(View view) {
        onBackPressed();
    }
}