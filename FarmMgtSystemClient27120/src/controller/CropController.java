package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import model.Crop;
import service.CropService;

/**
 * Controller for Crop-related operations
 * Handles communication between View and RMI Service
 */
public class CropController {
    
    private CropService cropService;
    
    public CropController() throws Exception {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 3001);
        this.cropService = (CropService) registry.lookup("CropService");
    }
    
    /**
     * Save new crop
     */
    public Crop saveCrop(Crop crop) throws RemoteException {
        try {
            // If nationalId is missing, try to resolve it from SessionManager or by fetching user from server
            if (crop != null && (crop.getNationalId() == null || crop.getNationalId().trim().isEmpty())) {
                try {
                    util.SessionManager sm = util.SessionManager.getInstance();
                    model.User cu = sm.getCurrentUser();
                    model.User resolved = null;
                    if (cu != null) {
                        try {
                            controller.UserController uc = new controller.UserController();
                            if (cu.getEmail() != null && !cu.getEmail().trim().isEmpty()) {
                                resolved = uc.findByEmail(cu.getEmail());
                            } else if (cu.getPhoneNumber() != null && !cu.getPhoneNumber().trim().isEmpty()) {
                                resolved = uc.findByPhone(cu.getPhoneNumber());
                            } else if (cu.getNationalId() != null && !cu.getNationalId().trim().isEmpty()) {
                                resolved = uc.findByNationalId(cu.getNationalId());
                            }
                        } catch (Exception e) {
                            System.err.println("CropController.saveCrop: could not refresh user from server: " + e.getMessage());
                        }
                    }
                    if (resolved != null && resolved.getNationalId() != null) {
                        crop.setNationalId(resolved.getNationalId());
                    } else {
                        String last = util.SessionManager.getInstance().getLastPersistedNationalId();
                        if (last != null && !last.trim().isEmpty()) {
                            crop.setNationalId(last.trim());
                        }
                    }
                } catch (Exception e) {
                    System.err.println("CropController.saveCrop: unexpected error resolving nationalId: " + e.getMessage());
                }
            }
            System.out.println("CropController.saveCrop: sending crop -> nid='" + (crop != null ? crop.getNationalId() : "<null>") + "', name='" + (crop != null ? crop.getCropName() : "<null>") + "'");
            // If nationalId still missing after attempts, surface a helpful message immediately
            if (crop.getNationalId() == null || crop.getNationalId().trim().isEmpty()) {
                throw new RemoteException("Unable to determine your National ID for the current session. Please log out and log in again or verify your account details.");
            }
        } catch (java.rmi.RemoteException re) {
            throw re;
        } catch (Exception ignore) {}
        return cropService.saveCrop(crop);
    }
    
    /**
     * Update existing crop
     */
    public Crop updateCrop(Crop crop) throws RemoteException {
        return cropService.updateCrop(crop);
    }
    
    /**
     * Delete crop
     */
    public Crop deleteCrop(Crop crop) throws RemoteException {
        return cropService.deleteCrop(crop);
    }
    
    /**
     * Search crop by ID
     */
    public Crop searchCropById(Crop crop) throws RemoteException {
        return cropService.searchCropById(crop);
    }
    
    /**
     * Retrieve all crops
     */
    public List<Crop> retrieveAllCrops() throws RemoteException {
        return cropService.retrieveAllCrops();
    }
    
    /**
     * Find crops by user (nationalId)
     */
    public List<Crop> findCropsByUser(String nationalId) throws RemoteException {
        return cropService.findCropsByUser(nationalId);
    }
    
    /**
     * Search crops by name
     */
    public List<Crop> searchCropsByName(String nationalId, String cropName) throws RemoteException {
        return cropService.searchCropsByName(nationalId, cropName);
    }
    
    /**
     * Find crops by status
     */
    public List<Crop> findCropsByStatus(String nationalId, String status) throws RemoteException {
        return cropService.findCropsByStatus(nationalId, status);
    }
    
    /**
     * Count crops by user
     */
    public Long countCropsByUser(String nationalId) throws RemoteException {
        return cropService.countCropsByUser(nationalId);
    }
    
    /**
     * Count all crops
     */
    public Long countAllCrops() throws RemoteException {
        return cropService.countAllCrops();
    }
    
    /**
     * Get total expected yield by user
     */
    public Double getTotalExpectedYieldByUser(String nationalId) throws RemoteException {
        return cropService.getTotalExpectedYieldByUser(nationalId);
    }
    
    /**
     * Helper method: Delete crop by ID and nationalId
     * Creates a Crop object with the ID and calls deleteCrop
     */
    public boolean deleteCropById(int cropId, String nationalId) throws RemoteException {
        Crop crop = new Crop();
        crop.setCropId(cropId);
        crop.setNationalId(nationalId);
        Crop deleted = deleteCrop(crop);
        return deleted != null;
    }
    
    /**
     * Helper method: Create crop (for compatibility with views expecting int return)
     */
    public int createCrop(Crop crop) throws RemoteException {
        Crop saved = saveCrop(crop);
        return saved != null ? 1 : 0;
    }
    
    /**
     * Helper method: Update crop (for compatibility with views expecting int return)
     */
    public int updateCropById(Crop crop) throws RemoteException {
        Crop updated = updateCrop(crop);
        return updated != null ? 1 : 0;
    }
}

