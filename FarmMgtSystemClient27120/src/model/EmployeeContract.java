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

public class EmployeeContract implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    private int contractId;
    private Employee employee;
    private ContractType contractType;
    private Date startDate;
    private Date endDate;
    private String termsAndConditions;
    private Boolean healthInsurance = false;
    private Boolean housingAllowance = false;
    private Boolean transportAllowance = false;
    private Boolean bonusEligibility = false;
    private String bankAccountNumber;
    private String bankName;
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

    // Getters and Setters
    public int getContractId() { 
        return contractId; 
    }
    
    public void setContractId(int contractId) { 
        this.contractId = contractId; 
    }

    public Employee getEmployee() { 
        return employee; 
    }
    
    public void setEmployee(Employee employee) { 
        this.employee = employee; 
    }

    public ContractType getContractType() { 
        return contractType; 
    }
    
    public void setContractType(ContractType contractType) { 
        this.contractType = contractType; 
    }

    public Date getStartDate() { 
        return startDate; 
    }
    
    public void setStartDate(Date startDate) { 
        this.startDate = startDate; 
    }

    public Date getEndDate() { 
        return endDate; 
    }
    
    public void setEndDate(Date endDate) { 
        this.endDate = endDate; 
    }

    public String getTermsAndConditions() { 
        return termsAndConditions; 
    }
    
    public void setTermsAndConditions(String termsAndConditions) { 
        this.termsAndConditions = termsAndConditions; 
    }

    public Boolean getHealthInsurance() { 
        return healthInsurance; 
    }
    
    public void setHealthInsurance(Boolean healthInsurance) { 
        this.healthInsurance = healthInsurance; 
    }

    public Boolean getHousingAllowance() { 
        return housingAllowance; 
    }
    
    public void setHousingAllowance(Boolean housingAllowance) { 
        this.housingAllowance = housingAllowance; 
    }

    public Boolean getTransportAllowance() { 
        return transportAllowance; 
    }
    
    public void setTransportAllowance(Boolean transportAllowance) { 
        this.transportAllowance = transportAllowance; 
    }

    public Boolean getBonusEligibility() { 
        return bonusEligibility; 
    }
    
    public void setBonusEligibility(Boolean bonusEligibility) { 
        this.bonusEligibility = bonusEligibility; 
    }

    public String getBankAccountNumber() { 
        return bankAccountNumber; 
    }
    
    public void setBankAccountNumber(String bankAccountNumber) { 
        this.bankAccountNumber = bankAccountNumber; 
    }

    public String getBankName() { 
        return bankName; 
    }
    
    public void setBankName(String bankName) { 
        this.bankName = bankName; 
    }

    public Date getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(Date createdAt) { 
        this.createdAt = createdAt; 
    }
    
    // Basic equals and hashCode
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
    

}