package Classes;

public class Unity {
	 private int uid;
	 private String uname;
	  
	    
	    public Unity( int uid, String uname ) 
		  { 
		  this.uid = uid; 
		  this.uname = uname; 		 
		  } 	 
	    @Override
		  public String toString() 
		  { 	
		     	return uname; 	 
		  } 

	    public int getUid() {
	        return uid;
	    }

	    public void setUid(int uid) {
	        this.uid = uid;
	    }

	    public String getUname() {
	        return uname;
	    }
	    public void setUname(String uname) {
	        this.uname = uname;
	    }
	  

}
