package com.absolutecinema.repository;

import com.absolutecinema.model.Showtime;
import com.absolutecinema.utils.Paths;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

public class ShowtimeRepository implements Repository<Showtime> {
    private static final String filePath = Paths.SHOWTIME_REPOSITORY;
    private final TxtFileManager fileManager = new TxtFileManager();

    @Override
    public List<Showtime> findAll() {
        List<Showtime> showtimes = new ArrayList<>();

        for(String line : fileManager.readLines(filePath)){
            showtimes.add(Showtime.printformat(line));
        }

        return showtimes;
    }

    @Override
    public Showtime findById(String id) {

        for(Showtime showtime : findAll()){
            if(showtime.getId().equals(id)){
                return showtime;
            }
        }

        return null;
    }

    @Override
    public void save(Showtime showtime) {
        fileManager.appendLine(filePath, showtime.toString());
    }

    @Override
    public void update(Showtime showtime) {
        List<Showtime> showtimes = findAll();

        for (int i = 0; i < showtimes.size(); i++) {
            if (showtimes.get(i).getId().equals(showtime.getId())) {
                showtimes.set(i, showtime);
                break;
            }
        }

        writeAll(showtimes);
    }

    @Override
    public void delete(String id) {
        List<Showtime> showtimes = findAll();

        showtimes.removeIf(showtime -> showtime.getId().equals(id));

        writeAll(showtimes);
    }

    public List<Showtime> findByMovieId(String movieId){
        List<Showtime> result = new ArrayList<>();
        for (Showtime showtime :  findAll()){
            if (showtime.getMovieId().equals(movieId)){
                result.add(showtime);
            }
        }

        return result;
    }

    public List<Showtime> findByDate(String date){
        List<Showtime>  result = new ArrayList<>();

        for (Showtime showtime :   findAll()){
            if (showtime.getDate().equals(date)){
                result.add(showtime);
            }
        }

        return result;
    }

    public List<Showtime> findByRoom(String roomId){
        List<Showtime> result = new ArrayList<>();

        for (Showtime showtime :  findAll()){
            if (showtime.getRoomId().equals(roomId)){
                result.add(showtime);
            }
        }

        return result;
    }

    public String getLastId() {
        List<Showtime> showtimes = findAll();

        if (showtimes.isEmpty()) {
            return "SHW000";
        }
        return showtimes.get(showtimes.size() - 1).getId();
    }

    private void writeAll(List<Showtime> showtimes){
        List<String> lines = new ArrayList<>();

        for(Showtime showtime : showtimes){
            lines.add(showtime.toString());
        }

        fileManager.writeLines(filePath, lines);
    }
}
