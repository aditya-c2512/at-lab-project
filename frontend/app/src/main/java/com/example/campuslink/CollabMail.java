package com.example.campuslink;

import java.util.ArrayList;
import java.util.List;

public class CollabMail {
    public String userId;
    public String fname;
    public String project_name;
    public String relevant_work;
    public List<String> skills;

    public CollabMail()
    {

    }
    public CollabMail(String uid, String fn, String pn, String rwork, List<String> sk)
    {
        fname = fn;
        project_name = pn;
        relevant_work = rwork;
        skills = new ArrayList<>();
        skills.addAll(sk);
    }
}
