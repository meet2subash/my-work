/**
 * 
 */
package com.alexa.model;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author singhsub
 *
 */
public class ReviewPredicates
{
	/**
	 * 
	 * @param rSource
	 * @return
	 */
    public static Predicate<AlexaAppReviewBean> getByReviewSource(String rSource) {
        return p -> p.getReview_source().equalsIgnoreCase(rSource);
    }
    /**
     * 
     * @param rating
     * @return
     */
    public static Predicate<AlexaAppReviewBean> getByRating(String rating) {
        return p -> p.getRating().equalsIgnoreCase(rating);
    }
    /**
     * 
     * @param date
     * @return
     */
    public static Predicate<AlexaAppReviewBean> getByReviewDate(String date) {
        return p -> p.getReviewed_date().equalsIgnoreCase(date);
    }
    /**
     * 
     * @param rSource
     * @param rating
     * @return
     */
    public static Predicate<AlexaAppReviewBean> getByReviewSourceAndRating(String rSource,String rating) {
        return p -> p.getReview_source().equalsIgnoreCase(rSource) && p.getRating().equalsIgnoreCase(rating);
    }
    /**
     * 
     * @param rSource
     * @param reviewed_date
     * @return
     */
    public static Predicate<AlexaAppReviewBean> getByReviewSourceAndDate(String rSource,String reviewed_date) {
        return p -> p.getReview_source().equalsIgnoreCase(rSource) && p.getReviewed_date().equalsIgnoreCase(reviewed_date);
    }
    /**
     * 
     * @param rating
     * @param reviewed_date
     * @return
     */
    public static Predicate<AlexaAppReviewBean> getByRatingAndDate(String rating,String reviewed_date) {
        return p -> p.getRating().equalsIgnoreCase(rating) && p.getReviewed_date().equalsIgnoreCase(reviewed_date);
    }
    /**
     * 
     * @param rating
     * @param rSource
     * @param reviewed_date
     * @return
     */
    public static Predicate<AlexaAppReviewBean> getByReviewSourceAndRatingAndDate(String rating, String rSource, String reviewed_date) {
        return p -> p.getRating().equalsIgnoreCase(rating)&& p.getReview_source().equalsIgnoreCase(rSource) && p.getReviewed_date().equalsIgnoreCase(reviewed_date);
    }
	/**
	 * 			     
	 * @param reviews
	 * @param predicate
	 * @return
	 */
    public static List<AlexaAppReviewBean> filterReviews (List<AlexaAppReviewBean> reviews, Predicate<AlexaAppReviewBean> predicate) {
        return reviews.stream().filter( predicate ).collect(Collectors.<AlexaAppReviewBean>toList());
    }
    
    public static int sum(int a,int b){
    	
    	return a+b;
    }
}  
