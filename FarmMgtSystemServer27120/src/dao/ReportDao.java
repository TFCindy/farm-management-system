/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author PC
 */

import dto.ReportDTO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class ReportDao {

    public List<ReportDTO> getUserReportData(String nationalId) {
        List<ReportDTO> reportData = new ArrayList<>();
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();

            // Query crops
            Query cropQuery = session.createQuery(
                "SELECT c.cropName, 'Crop', c.plantingDate, 1, c.status " +
                "FROM Crop c WHERE c.user.nationalId = :nationalId"
            );
            cropQuery.setParameter("nationalId", nationalId);  // works in Hibernate 4
            List crops = cropQuery.list();

            for (Object row : crops) {
                Object[] cols = (Object[]) row;
                ReportDTO dto = new ReportDTO();
                dto.setItemName((String) cols[0]);
                dto.setItemType((String) cols[1]);
                dto.setDate((java.util.Date) cols[2]);
                dto.setQuantity((Integer) cols[3]);
                dto.setStatus((String) cols[4]);
                reportData.add(dto);
            }

            // Query livestock
            Query livestockQuery = session.createQuery(
                "SELECT l.animalType, 'Livestock', CURRENT_DATE, l.quantity, l.healthStatus " +
                "FROM Livestock l WHERE l.user.nationalId = :nationalId"
            );
            livestockQuery.setParameter("nationalId", nationalId);
            List livestock = livestockQuery.list();

            // Count sick animals
            Query sickAnimalsQuery = session.createQuery(
                "SELECT COUNT(l.animalId) " +
                "FROM Livestock l WHERE l.user.nationalId = :nationalId AND l.healthStatus = 'Sick'"
            );
            sickAnimalsQuery.setParameter("nationalId", nationalId);
            Long sickCount = (Long) sickAnimalsQuery.uniqueResult();

            for (Object row : livestock) {
                Object[] cols = (Object[]) row;
                ReportDTO dto = new ReportDTO();
                dto.setItemName((String) cols[0]);
                dto.setItemType((String) cols[1]);
                dto.setDate((java.util.Date) cols[2]);
                dto.setQuantity((Integer) cols[3]);
                dto.setStatus((String) cols[4]);
                reportData.add(dto);
            }

            // Add sick animals summary as a special report item
            if (sickCount != null && sickCount > 0) {
                ReportDTO sickAnimalsDTO = new ReportDTO();
                sickAnimalsDTO.setItemName("Sick Animals Summary");
                sickAnimalsDTO.setItemType("Health Alert");
                sickAnimalsDTO.setDate(new java.util.Date());
                sickAnimalsDTO.setQuantity(sickCount.intValue());
                sickAnimalsDTO.setStatus("Needs Attention");
                reportData.add(sickAnimalsDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return reportData;
    }
}
