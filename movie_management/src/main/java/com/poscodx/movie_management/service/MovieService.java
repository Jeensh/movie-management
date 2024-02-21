package com.poscodx.movie_management.service;

import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.repository.MovieRepository;

import java.util.List;

public class MovieService {
    private static final MovieRepository movieRepository = new MovieRepository();

    public void addMovie(MovieDTO movie) {
        movieRepository.addWithAddress(movie);
    }

    public List<MovieDTO> getMoviesByPageNumber(int pageNumber) {
        List<MovieDTO> list = movieRepository.findByRange(10, pageNumber);
        return list;
    }

    public List<MovieDTO> getMoviesByKeyWordAndPageNumber(int pageNumber, String keyWord) {
        List<MovieDTO> list = movieRepository.findByKeyWord(keyWord, 10, pageNumber);
        return list;
    }

    public int getTotalCountByKeyWord(String keyWord){
        return movieRepository.findMovieCountByKeyWord(keyWord);
    }

    public MovieDTO getMovieById(int id) {
        return movieRepository.findById(id);
    }

    /*
        movie에 들어있어야 할 정보
        id : 수정할 영화 아이디
        title : 수정할 영화 제목
        description : 수정할 영화 줄거리
        grade : 수정할 영화 등급
     */

    public int getTotalCount() {
        return movieRepository.findMovieCount();
    }

    public void editMovie(MovieDTO movie) {
        movieRepository.update(movie);
    }

    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
    }
}
