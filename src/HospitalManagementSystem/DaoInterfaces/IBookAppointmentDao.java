package HospitalManagementSystem.DaoInterfaces;

public interface IBookAppointmentDao {
    public int bookAppointmentDao(int patient_id,int doctor_id,String appointmentDate);
    public boolean checkDoctorAvailabilityDao(int doctor_id,String appointmentDate);

}
