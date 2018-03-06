package edu.mum.cs.uis.model;

public enum Status {
	
	CREATED("CREATED"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    CLOSED("CLOSED");

    private String status;

    Status(String status) {
        this.status = status;
    }

//    public String getValue() {
//        return status;
//    }
    
    public static void main(String[] args) {

        for (Status status : Status.values()) {
            System.out.println(status);
        }

    }

}
