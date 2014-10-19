
public class MyWebservice {

	
	public String hello(String name){

	    System.out.println("HelloService.hello(String name): value of karthik: " + name);

	    if( name == null || name.equals("") ){
	        return "Hello World";
	    }
	    else{
	        return "Hello " + name;
	    }
	}
	
}
