package HospitalManagementSystem.ServiceInterfaces;

import HospitalManagementSystem.Service.Doctor;
import HospitalManagementSystem.Service.Patient;

public interface IBookAppointment {
    public void bookAppointment(IPatient patient, IDoctor doctor);
    public boolean checkDoctorAvailability(int doctor_id,String appointmentDate);
}
