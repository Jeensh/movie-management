package com.poscodx.movie_management.service;

import com.poscodx.movie_management.model.ReviewDTO;
import com.poscodx.movie_management.repository.ReviewRepository;

import java.util.List;

public class ReviewService {
    private static final ReviewRepository reviewRepository = new ReviewRepository();

    public String getAvgScoreByMovieId(int movieId){
        return reviewRepository.getAvgScoreByMovieId(movieId);
    }

    public List<ReviewDTO> getReviewsByMovieId(int movieId, int size, int pageNumber, int reviewType){
        if(reviewType == 1)
            return reviewRepository.findByMovieIdAndRange(movieId, size, pageNumber);
        else
            return reviewRepository.findByMovieIdAndRangeAndReviewType(movieId, size, pageNumber, reviewType);
    }

    public int getReviewCountByMovieIdAndReviewType(int movieId, int reviewType){
        if(reviewType == 1)
            return reviewRepository.findReviewCountByMovieId(movieId);
        else
            return reviewRepository.findReviewCountByMovieId(movieId, reviewType);
    }

    public void addReview(ReviewDTO reviewDTO){
        reviewRepository.add(reviewDTO);
    }

    public void deleteReview(int reviewId){
        reviewRepository.deleteById(reviewId);
    }
}
