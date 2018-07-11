import org.junit.BeforeClass;
import org.junit.Test;

import com.alexa.model.ReviewPredicates;

import junit.framework.Assert;


public class SampleTest {

 private static ReviewPredicates rp= null;
	
	@BeforeClass
	public static void  init(){
		rp = new ReviewPredicates();
	}
	@Test
	public void sum(){
		
		Assert.assertEquals(100, rp.sum(50, 50));
		
		
	}
}
