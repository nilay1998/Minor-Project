package com.example.attendancemanagementfaculty.Reterofit;

import java.util.Comparator;

public class AllItems
{
    private String password;
    private String __v;
    private String name;
    private String rollNumber;
    private String _id;
    private String id;
    private String[] attendance;
    private String email;

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getRollNumber ()
    {
        return rollNumber;
    }

    public void setRollNumber (String rollNumber)
    {
        this.rollNumber = rollNumber;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String[] getAttendance ()
    {
        return attendance;
    }

    public void setAttendance (String[] attendance)
    {
        this.attendance = attendance;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public static Comparator<AllItems> StuRollno = new Comparator<AllItems>() {

        public int compare(AllItems s1, AllItems s2) {

            int rollno1 = Integer.parseInt(s1.getRollNumber());
            int rollno2 = Integer.parseInt(s2.getRollNumber());

            /*For ascending order*/
            return rollno1-rollno2;

            /*For descending order*/
            //rollno2-rollno1;
        }};

}