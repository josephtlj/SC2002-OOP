package src.models;

public class Staff extends User {
    public enum Gender {
        MALE,
        FEMALE
    }

    // INSTANCE VARIABLES
    String name;
    Gender gender;
    int age;

    // CONSTRUCTOR
    public Staff(String hospitalId, String password, Role role, byte[] salt, boolean isFirstLogin, String name,
            Gender gender,
            int age) {
        super(hospitalId, password, role, salt, isFirstLogin);
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    
    /** 
     * @return String
     */
    // GETTERS AND SETTERS
    public String getName() {
        return this.name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return Gender
     */
    public Gender getGender() {
        return this.gender;
    }

    
    /** 
     * @param gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
