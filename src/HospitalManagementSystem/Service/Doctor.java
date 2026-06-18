package HospitalManagementSystem.Service;

import HospitalManagementSystem.DAO.DoctorDao;
import HospitalManagementSystem.DaoInterfaces.IDoctorDao;
import HospitalManagementSystem.ServiceInterfaces.IDoctor;

import java.sql.Connection;


public class Doctor implements IDoctor {
    private Connection connection;

    public Doctor(Connection connection){
        this.connection = connection;
        this.doctorDao = new DoctorDao(connection);
    }
    private IDoctorDao doctorDao;


    public void viewDoctors() {
        doctorDao.viewDoctorsDao();
    }

    public boolean getDoctorById(int id){
        return doctorDao.getDoctorByIdDao(id);
    }
}
