package HospitalManagementSystem.DaoInterfaces;

public interface IMedicalRecordDao {
    public int addMedicalRecordDao(int patientId, int doctorId, String diagnosis, String prescription, String recordDate);
    public void viewRecordsByPatientDao(int patientId);
    public int deleteRecordDao(int recordId);
}
