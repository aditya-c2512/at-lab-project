package com.example.campuslink;

import java.util.ArrayList;
import java.util.List;

public class ProjectPost {
    public String userId;
    public String project_name;
    public String desc;
    public boolean status;
    public List<String> relevant_domain;
    public int duration;

    public ProjectPost()
    {

    }

    public ProjectPost(String uid,String pn, String d, boolean s, List<String> rd, int du)
    {
        userId = uid;
        project_name = pn;
        desc = d;
        status = s;
        relevant_domain = new ArrayList<>();
        relevant_domain.addAll(rd);
        duration = du;
    }
}
