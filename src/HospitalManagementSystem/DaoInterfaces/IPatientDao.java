package HospitalManagementSystem.DaoInterfaces;

public interface IPatientDao {
    public int addPatientDao(String name,int age,String gender);
    public void viewPatientsDao();
    public boolean getPatientByIdDao(int id);
}
