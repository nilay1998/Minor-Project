package com.example.attendancemanagementfaculty.Reterofit;

public class MyPojo
{
    private AllItems[] allItems;

    private String message;

    private String status;

    public AllItems[] getAllItems ()
    {
        return allItems;
    }

    public void setAllItems (AllItems[] allItems)
    {
        this.allItems = allItems;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [allItems = "+allItems+", message = "+message+", status = "+status+"]";
    }
}