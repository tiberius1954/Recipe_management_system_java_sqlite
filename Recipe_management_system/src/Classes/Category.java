package Classes;

public class Category {
	 private int cid;
	 private String cname;
	  
	    
	    public Category( int cid, String cname ) 
		  { 
		  this.cid = cid; 
		  this.cname = cname; 		 
		  } 	 
	    @Override
		  public String toString() 
		  { 	
		     	return cname; 	 
		  } 

	    public int getCid() {
	        return cid;
	    }

	    public void setCid(int cid) {
	        this.cid = cid;
	    }

	    public String getCname() {
	        return cname;
	    }
	    public void setCname(String cname) {
	        this.cname = cname;
	    }	 
}
