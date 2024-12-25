package Classes;

public class Ingredient {
	 private int iid;
	 private String iname;
	 private int uid;
	  
	    
	    public Ingredient( int iid, String iname, int uid ) 
		  { 
		  this.iid = iid; 
		  this.iname = iname; 	
		  this.uid = uid;
		  } 	 
	    @Override
		  public String toString() 
		  { 	
		     	return iname +"                                                  " +  uid; 	 
		  } 

	    public int getIid() {
	        return iid;
	    }

	    public void setIid(int iid) {
	        this.iid = iid;
	    }
	    
	    public int getUid() {
	        return uid;
	    }

	    public void setUid(int uid) {
	        this.uid = uid;
	    }

	    public String getIname() {
	        return iname;
	    }
	    public void setIname(String iname) {
	        this.iname = iname;
	    }

}
