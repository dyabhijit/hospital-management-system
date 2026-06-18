package HospitalManagementSystem.ServiceInterfaces;

import HospitalManagementSystem.Service.Doctor;
import HospitalManagementSystem.Service.Patient;

public interface IBookAppointment {
    public void bookAppointment(Patient patient, Doctor doctor);
    public boolean checkDoctorAvailability(int doctor_id,String appointmentDate);
}
