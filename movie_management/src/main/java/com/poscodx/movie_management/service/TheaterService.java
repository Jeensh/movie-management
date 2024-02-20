package com.poscodx.movie_management.service;

import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.TheaterDTO;
import com.poscodx.movie_management.repository.TheaterRepository;

import java.util.List;

public class TheaterService {
    private static final TheaterRepository theaterRepository = new TheaterRepository();

    public void addTheater(TheaterDTO theater){
        theaterRepository.addWithAddress(theater);
    }

    public List<TheaterDTO> getTheatersByPageNumber(int pageNumber){
        List<TheaterDTO> list = theaterRepository.findByRange(10, pageNumber);
        return list;
    }

    public List<TheaterDTO> getTheatersByKeyWordAndPageNumber(int pageNumber, String keyWord){
        List<TheaterDTO> list = theaterRepository.findByKeyWord(keyWord, 10, pageNumber);
        return list;
    }

    public TheaterDTO getTheaterById(int id){
        return theaterRepository.findById(id);
    }

    /*
        theater 들어있어야 할 정보
        - name
        - location
        - tel
        - thumbnail
     */

    public int getTotalCount(){
        return theaterRepository.findTheaterCount();
    }
    public void editTheater(TheaterDTO theater){
        theaterRepository.updateWithoutThumbnail(theater);
    }

    public void deleteTheater(int id){
        theaterRepository.deleteById(id);
    }
}
