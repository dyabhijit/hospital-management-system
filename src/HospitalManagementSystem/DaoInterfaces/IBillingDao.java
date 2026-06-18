package HospitalManagementSystem.DaoInterfaces;

public interface IBillingDao {
    public int generateBillDao(int patientId, int doctorId, String treatment, double amount, String billDate);
    public void viewBillByIdDao(int patientId);
    public int updatePaymentStatusDao(String status,int billId);
    public void viewAllUnpaidBillsDao();
}
