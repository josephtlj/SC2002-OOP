package src.controllers;

import src.interfaces.PharmacistServiceInterface;

public class PharmacistController {
    private final PharmacistServiceInterface pharmacistService;

    public PharmacistController(PharmacistServiceInterface pharmacistService) {
        this.pharmacistService = pharmacistService;
    }

    public boolean handleUpdatePassword(String hospitalId, String newPassword, String confirmPassword) {
        try {
            pharmacistService.updatePassword(hospitalId, newPassword, confirmPassword);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
