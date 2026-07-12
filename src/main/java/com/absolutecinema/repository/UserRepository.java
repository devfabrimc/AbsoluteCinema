package com.absolutecinema.repository;

import com.absolutecinema.model.User;
import com.absolutecinema.utils.Paths;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User> {
    private static final String filepath = Paths.USER_REPOSITORY;
    private final TxtFileManager fileManager = new TxtFileManager();

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        for(String line : fileManager.readLines(filepath)){
            users.add(User.printformat(line));
        }

        return users;
    }

    @Override
    public User findById(String id) {

        for(User user : findAll()){
            if(user.getId().equals(id)){
                return user;
            }
        }

        return null;
    }

    @Override
    public void save(User user) {
        fileManager.appendLine(filepath, user.toString());
    }

    @Override
    public void update(User user) {
        List<User> users = findAll();

        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(user.getId())){
                users.set(i, user);
                break;
            }
        }

        writeAll(users);
    }

    @Override
    public void delete(String id) {
        List<User> users = findAll();

        users.removeIf(user -> user.getId().equals(id));

        writeAll(users);
    }

    public User findByUsername(String username) {
        for(User user : findAll()){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }

        return null;
    }

    public User findByEmail(String email) {
        for(User user : findAll()){
            if(user.getEmail().equalsIgnoreCase(email)){
                return user;
            }
        }

        return null;
    }

    public String getLastId(){
        List<User> users = findAll();

        if (users.isEmpty()){
            return "USR000";
        }

        return  users.get(users.size()-1).getId();
    }

    private void writeAll(List<User> users){
        List<String> lines = new ArrayList<>();

        for(User user : users){
            lines.add(user.toString());
        }

        fileManager.writeLines(filepath, lines);
    }
}
