package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.ReviewDTO;
import com.poscodx.movie_management.service.MovieService;
import com.poscodx.movie_management.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public class MoviesRestController implements RestController{

    private final MovieService movieService = new MovieService();
    private final ReviewService reviewService = new ReviewService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int movieId = Integer.parseInt(paramMap.getOrDefault("movieId", "-1"));
        int reviewPageNumber = Integer.parseInt(paramMap.getOrDefault("reviewPageNumber", "-1"));
        int pageNumber = Integer.parseInt(paramMap.getOrDefault("pageNumber", "-1"));
        int reviewType = Integer.parseInt(paramMap.getOrDefault("reviewType", "1"));
        String keyword = paramMap.getOrDefault("keyword", null);
        if(movieId > 0){    // 단건 조회
            //  option
            //      - 1 : 전체 리뷰 가져오기
            //      - 2 : 일반 사용자 리뷰 가져오기
            //      - 3 : 평론가 리뷰 가져오기
            MovieDTO movie = movieService.getMovieById(movieId);

            String avgScore = reviewService.getAvgScoreByMovieId(movieId);
            if(avgScore == null) avgScore = "0";

            movie.setAvgScore(avgScore);
            List<ReviewDTO> reviewList = reviewService.getReviewsByMovieId(movieId, 5, reviewPageNumber, reviewType);
            movie.setReviewList(reviewList);
            int total = reviewService.getReviewCountByMovieIdAndReviewType(movieId, reviewType);
            model.put("movie", movie);
            model.put("total", total);
        }
        if(pageNumber > 0){ // 범위 조회
            List<MovieDTO> list = null;
            int total = 0;
            if(keyword == null || keyword.isEmpty()){
                list = movieService.getMoviesByPageNumber(pageNumber);
                total = movieService.getTotalCount();
            }
            else{
                list = movieService.getMoviesByKeyWordAndPageNumber(pageNumber, keyword);
                total = movieService.getTotalCountByKeyWord(keyword);
            }

            model.put("total", total);
            model.put("movies", list);
        }
        if(pageNumber == 0){    // 총 영화 수 조회
            int total = 0;
            if(keyword == null){
                total = movieService.getTotalCount();
            }
            else{
                total = movieService.getTotalCountByKeyWord(keyword);
            }

            model.put("total", total);
        }
    }
}
