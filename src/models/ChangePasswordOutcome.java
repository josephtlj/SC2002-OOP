package src.models;

import src.utils.ENUM.PasswordErrorType;
public class ChangePasswordOutcome 
{
    
    //ATTRIBUTES
    private boolean outcome;
    private PasswordErrorType errorType;

    //CONSTRUCTOR
    public ChangePasswordOutcome(boolean outcome, PasswordErrorType errorType)
    {
        this.outcome= outcome;
        this.errorType= errorType;
    }

    //SET METHODS
    public void setOutcome(boolean outcome)
    {
        this.outcome= outcome;
    }

    public void setPasswordErrorType(PasswordErrorType errorType)
    {
        this.errorType= errorType;
    }

    //GET METHODS
    public boolean getOutcome()
    {
        return this.outcome;
    }

    public PasswordErrorType getErrorType()
    {
        return this.errorType;
    }
}