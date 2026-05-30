package HospitalManagementSystem.Service;

import HospitalManagementSystem.DAO.DoctorDao;

import java.sql.Connection;


public class Doctor {
    private Connection connection;

    public Doctor(Connection connection){
        this.connection = connection;
        this.doctorDao = new DoctorDao(connection);
    }
    private DoctorDao doctorDao;


    public void viewDoctors() {
        doctorDao.viewDoctorsDao();
    }

    public boolean getDoctorById(int id){
        return doctorDao.getDoctorByIdDao(id);
    }
}
