package com.example.lenovo.enjad;

/**
 * Created by LENOVO on 06/11/17.
 */

public class configuration {

    public String con_id;
    public String act_as_helper;
    public String inform_contact_list;

    public configuration() {}

    public configuration(String conid, String actas_helper, String inform_contactlist) {
        this.con_id = conid;
        this.act_as_helper = actas_helper;
        this.inform_contact_list = inform_contactlist;
    }

}
