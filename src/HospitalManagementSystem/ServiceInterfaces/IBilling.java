package HospitalManagementSystem.ServiceInterfaces;

public interface IBilling {
    public void generateBill();
    public void viewBillById(int patientId);
    public void updatePaymentStatus();
    public void viewAllUnpaidBills();
}
