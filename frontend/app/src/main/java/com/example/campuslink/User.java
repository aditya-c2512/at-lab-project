package com.example.campuslink;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String full_name;
    public String username;
    public String email;
    public String pwd;
    public String org;
    public String qual;

    public boolean gender;

    public List<String> exps;

    public User()
    {

    }

    public User(String fname, String uname, String em, String p, String or, String q, boolean g, List<String> e)
    {
        full_name = fname;
        username = uname;
        email = em;
        pwd = p;
        org = or;
        qual = q;
        gender = g;
        exps = new ArrayList<String>();
        exps.addAll(e);
    }
}
