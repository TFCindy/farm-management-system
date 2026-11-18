/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author PC
 */

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "employee_contracts")
public class EmployeeContract implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private int contractId;
    
    @OneToOne
    @JoinColumn(name = "employee_id", unique = true, nullable = false)
    private Employee employee;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type", nullable = false, length = 20)
    private ContractType contractType;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;
    
    @Column(name = "terms_and_conditions", length = 2000)
    private String termsAndConditions;
    
    @Column(name = "health_insurance")
    private Boolean healthInsurance = false;
    
    @Column(name = "housing_allowance")
    private Boolean housingAllowance = false;
    
    @Column(name = "transport_allowance")
    private Boolean transportAllowance = false;
    
    @Column(name = "bonus_eligibility")
    private Boolean bonusEligibility = false;
    
    @Column(name = "bank_account_number", length = 30)
    private String bankAccountNumber;
    
    @Column(name = "bank_name", length = 50)
    private String bankName;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;
    
    // Enum for contract types
    public enum ContractType {
        PERMANENT, TEMPORARY, CASUAL, SEASONAL, INTERN
    }
    
    // Constructors
    public EmployeeContract() {
        this.createdAt = new Date();
    }
    
    public EmployeeContract(Employee employee, ContractType contractType, Date startDate) {
        this();
        this.employee = employee;
        this.contractType = contractType;
        this.startDate = startDate;
    }
    
    // Constructor for searching by contractId
    public EmployeeContract(int contractId) {
        this.contractId = contractId;
    }
    
    // Constructor for searching by employee
    public EmployeeContract(Employee employee) {
        this.employee = employee;
    }

    // Getters and Setters
    public int getContractId() { return contractId; }
    public void setContractId(int contractId) { this.contractId = contractId; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public ContractType getContractType() { return contractType; }
    public void setContractType(ContractType contractType) { this.contractType = contractType; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getTermsAndConditions() { return termsAndConditions; }
    public void setTermsAndConditions(String termsAndConditions) { this.termsAndConditions = termsAndConditions; }

    public Boolean getHealthInsurance() { return healthInsurance; }
    public void setHealthInsurance(Boolean healthInsurance) { this.healthInsurance = healthInsurance; }

    public Boolean getHousingAllowance() { return housingAllowance; }
    public void setHousingAllowance(Boolean housingAllowance) { this.housingAllowance = housingAllowance; }

    public Boolean getTransportAllowance() { return transportAllowance; }
    public void setTransportAllowance(Boolean transportAllowance) { this.transportAllowance = transportAllowance; }

    public Boolean getBonusEligibility() { return bonusEligibility; }
    public void setBonusEligibility(Boolean bonusEligibility) { this.bonusEligibility = bonusEligibility; }

    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    // Helper method to check if contract is active
    public boolean isActive() {
        Date today = new Date();
        return startDate != null && 
               (endDate == null || today.before(endDate) || today.equals(endDate));
    }
    
    // Helper method to get contract duration in days
    public Long getDurationInDays() {
        if (startDate == null) return null;
        Date end = (endDate != null) ? endDate : new Date();
        long diff = end.getTime() - startDate.getTime();
        return diff / (1000 * 60 * 60 * 24);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EmployeeContract that = (EmployeeContract) obj;
        return contractId == that.contractId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(contractId);
    }
    
    @Override
    public String toString() {
        return "EmployeeContract{" + 
                "contractId=" + contractId + 
                ", employeeId=" + (employee != null ? employee.getEmployeeId() : "null") + 
                ", contractType=" + contractType + 
                ", startDate=" + startDate + 
                ", endDate=" + endDate + 
                ", isActive=" + isActive() + 
                '}';
    }
}