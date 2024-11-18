package services;

import daos.DoctorDao;

public class DoctorService 
{
    //ATTRIBUTES
    DoctorDao doctorDao;

    //CONSTRUCTOR
    public DoctorService(String ID)
    {
        this.doctorDao= new DoctorDao();
    }
}
