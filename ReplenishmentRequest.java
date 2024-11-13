import java.util.UUID;

public class ReplenishmentRequest 
{

    // Data members
    private UUID requestId;
    private int requestedQuantity;
    private String status = "Pending";  // Default value is "Pending"

    //Default Constructor
    public ReplenishmentRequest() 
    {
        this.requestId = UUID.randomUUID();  // Default to a random UUID
        this.requestedQuantity = 0;          // Default requested quantity
        // 'status' is already initialized to "Pending"
    }

    // Constructor with parameters
    public ReplenishmentRequest(UUID requestId, int requestedQuantity, String status) 
    {
        this.requestId = requestId;
        this.requestedQuantity = requestedQuantity;
        this.status = status;
    }

    // Getter for requestId
    public UUID getRequestId() 
    {
        return requestId;
    }

    // Setter for requestId
    public void setRequestId(UUID requestId) 
    {
        this.requestId = requestId;
    }

    // Getter for requestedQuantity
    public int getRequestedQuantity() 
    {
        return requestedQuantity;
    }

    // Setter for requestedQuantity
    public void setRequestedQuantity(int requestedQuantity) 
    {
        this.requestedQuantity = requestedQuantity;
    }

    // Getter for status
    public String getStatus() 
    {
        return status;
    }

    // Setter for status
    public void setStatus(String status) 
    {
        this.status = status;
    }
}
