package com.example.aircomms.extras;

import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {


    public static HashMap<String, List<String>> getData(){


        HashMap<String, List <String>> expandableListDetail = new HashMap<>();

        List <String> cricket = new ArrayList<>();
        cricket.add("India");
        cricket.add("Pakistan");


        expandableListDetail.put("CRICKET TEAMS", cricket);

        return expandableListDetail;

    }
}
