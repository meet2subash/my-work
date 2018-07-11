package com.alexa.ws;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alexa.model.AlexaAppReviewBean;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpe.sessionMgmt.DeviceRepositoryImpl;
import com.hpe.sessionMgmt.vo.Device;

import static com.alexa.model.ReviewPredicates.*;

//import com.alexa.dao.ReviewDao;



@RestController
public class ReviewService {

	@Value("${welcome.message:test}")
	private String message = "Hello World";
	
	private List<AlexaAppReviewBean> reviewList;
	@Autowired
	DeviceRepositoryImpl deviceRepositoryImpl;
		

	@PostConstruct
	public void init()
	{
		System.out.println("ReviewService.init()");
		try {
			String filename="alexa.json";
		    Path pathToFile = Paths.get(filename);
		    System.out.println("pathToFile.toAbsolutePath() ------ > "+pathToFile.toAbsolutePath());
			byte[] jsonData = Files.readAllBytes(Paths.get(pathToFile.toAbsolutePath().toUri()));

			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//convert json string to object
			reviewList = objectMapper.readValue(jsonData, new TypeReference<List<AlexaAppReviewBean>>(){});
			System.out.println("=========================init End==========================");			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value ="/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		String id = UUID.randomUUID().toString();
		Device dev = new Device();
		dev.setAlwaysAvailable(true);
		dev.setRequestReachability(true);
		dev.setSessionContexts(null);
		dev.setUniqueIdentifier(id);
		
		//DeviceRepositoryImpl repo = new DeviceRepositoryImpl();
		
		deviceRepositoryImpl.saveOrUpdate(dev);
		System.out.println("device state saved for : "+dev.getUniqueIdentifier());
		
		dev = deviceRepositoryImpl.find(id);
		System.out.println("After get from redis : "+ dev.getUniqueIdentifier());
		
		
		return "welcome : " +dev.getUniqueIdentifier();
	}

	@RequestMapping(value = "/review/actGetReviewList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> getReviews(HttpServletRequest request,
			@RequestParam(value = "rating", required = false) String rating,
			@RequestParam(value = "review_source", required = false) String review_source,
			@RequestParam(value = "reviewed_date", required = false) String reviewed_date, HttpServletResponse response) throws Exception
	{
		System.out.println("ReviewService.getReviews()\n"
				+ "\nrating : "+rating
				+ "\n review_source : "+ review_source
				+ "\n reviewed_date : "+reviewed_date
				);
		
		List<AlexaAppReviewBean> newReviewList = null;
		String reviewsJson = null;
		
		if(null != rating && null !=review_source && null!=reviewed_date)
			newReviewList = filterReviews(reviewList, getByReviewSourceAndRatingAndDate(rating, review_source, reviewed_date));	
		else if (null != rating && null !=review_source)			
			newReviewList = filterReviews(reviewList, getByReviewSourceAndRating(review_source, rating));
		else if( null !=review_source && null!=reviewed_date)
			newReviewList = filterReviews(reviewList, getByReviewSourceAndDate(review_source, reviewed_date));
		else if( null !=rating && null!=reviewed_date)
			newReviewList = filterReviews(reviewList, getByRatingAndDate(rating, reviewed_date));
		else if(null != rating)
			newReviewList = filterReviews(reviewList, getByRating(rating));
		else if(null != review_source)
			newReviewList = filterReviews(reviewList, getByReviewSource(review_source));
		else if(null != reviewed_date)
			newReviewList = filterReviews(reviewList, getByReviewDate(reviewed_date));
		
		
		ObjectMapper mapper = new ObjectMapper();
		if(null == rating && null ==review_source && null==reviewed_date){
			reviewsJson = mapper.writeValueAsString(reviewList);
			return new ResponseEntity<String>(reviewsJson,new HttpHeaders(),HttpStatus.OK);
		}
		
		if (null != newReviewList && newReviewList.size() > 0) {			
			reviewsJson = mapper.writeValueAsString(newReviewList);
			return new ResponseEntity<String>(reviewsJson,new HttpHeaders(),HttpStatus.OK);
		}
		else
			return new ResponseEntity<String>("",new HttpHeaders(),HttpStatus.NO_CONTENT);
			
	}
	
	

	/*public static void main(String[] args) {
		try {

			byte[] jsonData = Files.readAllBytes(Paths.get("c:\\review.json"));

			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			//convert json string to object
			List<AlexaAppReviewBean> reviewList = objectMapper.readValue(jsonData, new TypeReference<List<AlexaAppReviewBean>>(){});
			int cnt=1;
			for (AlexaAppReviewBean alexaAppReviewBean : reviewList) {
				System.out.println("\nReview Object : "+cnt+" : "+alexaAppReviewBean.getReview());
				cnt++;
			}			
			
			List<AlexaAppReviewBean> newReviewList = filterReviews(reviewList, getByRating("1"));
					System.out.println("=="+newReviewList.get(0).getReview());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}*/

}