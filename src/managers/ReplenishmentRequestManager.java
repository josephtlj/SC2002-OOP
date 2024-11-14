package managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import Models.ReplenishmentRequest;

public class ReplenishmentRequestManager {

    // List to store ReplenishmentRequest objects
    private List<ReplenishmentRequest> requests;

    // Constructor to initialize the list
    public ReplenishmentRequestManager() {
        this.requests = new ArrayList<>();
    }


    // Method to approve a request
    public boolean approveRequest(UUID requestId) {
        ReplenishmentRequest request = getRequestById(requestId);
        if (request != null && "Pending".equalsIgnoreCase(request.getStatus())) {
            request.setStatus("Approved");
            return true;  // Successfully approved the request
        }
        return false;  // Return false if the request is not found or it's not in Pending status
    }

    // Method to reject a request
    public boolean rejectRequest(UUID requestId) {
        ReplenishmentRequest request = getRequestById(requestId);
        if (request != null && "Pending".equalsIgnoreCase(request.getStatus())) {
            request.setStatus("Rejected");
            return true;  // Successfully rejected the request
        }
        return false;  // Return false if the request is not found or it's not in Pending status
    }

    // Method to get a request by its ID
    public ReplenishmentRequest getRequestById(UUID requestId) {
        for (ReplenishmentRequest request : requests) {
            if (request.getRequestId().equals(requestId)) {
                return request;
            }
        }
        return null;  // Return null if no request is found with the given ID
    }

    // Method to list all requests
    public List<ReplenishmentRequest> getAllRequests() {
        return new ArrayList<>(requests);  // Return a copy of the list to avoid external modification
    }

    // Method to list all pending requests
    public List<ReplenishmentRequest> getPendingRequests() {
        List<ReplenishmentRequest> pendingRequests = new ArrayList<>();
        for (ReplenishmentRequest request : requests) {
            if ("Pending".equalsIgnoreCase(request.getStatus())) {
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }

    // Method to list all approved requests
    public List<ReplenishmentRequest> getApprovedRequests() {
        List<ReplenishmentRequest> approvedRequests = new ArrayList<>();
        for (ReplenishmentRequest request : requests) {
            if ("Approved".equalsIgnoreCase(request.getStatus())) {
                approvedRequests.add(request);
            }
        }
        return approvedRequests;
    }

    // Method to list all rejected requests
    public List<ReplenishmentRequest> getRejectedRequests() {
        List<ReplenishmentRequest> rejectedRequests = new ArrayList<>();
        for (ReplenishmentRequest request : requests) {
            if ("Rejected".equalsIgnoreCase(request.getStatus())) {
                rejectedRequests.add(request);
            }
        }
        return rejectedRequests;
    }

    // Method to generate a summary of all requests
    public String generateRequestSummary() {
        StringBuilder summary = new StringBuilder();
        for (ReplenishmentRequest request : requests) {
            summary.append(request.toString()).append("\n");
        }
        return summary.toString();
    }
}
