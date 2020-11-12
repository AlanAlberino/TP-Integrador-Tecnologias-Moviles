package com.iua.alanalberino.persistence;

import android.app.Application;
import android.os.AsyncTask;

import com.iua.alanalberino.model.User;

import java.util.concurrent.ExecutionException;

public class UserRepository {
    private UserDAO userDAO;
    private User user;

    public UserRepository(Application application) {
        TheMovieDBDatabase database = TheMovieDBDatabase.getDatabase(application);
        userDAO = database.userDAO();
    }

    public User getUser(String userName) {
        try {
            user = new getUserAsyncTask(userDAO).execute(userName).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getLoggedUser() {
        try {
            user = new getLoggedUserAsyncTask(userDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void insert(User user) throws ExecutionException, InterruptedException {
        new insertUsersAsyncTask(userDAO).execute(user).get();
    }

    public void deleteAll(){
        new deleteAllAsyncTask(userDAO).execute();
    }

    public void setAsLoggedIn(User u){
        new setAsLoggedInAsyncTask(userDAO).execute(u);
    }

    public void setAsLoggedOut(User u){
        new setAsLoggedOutAsyncTask(userDAO).execute(u);
    }

    private static class insertUsersAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO asyncTaskUserDAO;
        insertUsersAsyncTask(UserDAO userDAO) {
            asyncTaskUserDAO = userDAO;
        }
        @Override
        protected Void doInBackground(User... users) {
            asyncTaskUserDAO.insert(users[0]);
            return null;
        }
    }

        private static class getUserAsyncTask extends AsyncTask<String, Void, User> {
            private UserDAO asyncTaskUserDAO;
            getUserAsyncTask(UserDAO userDAO) {
                asyncTaskUserDAO = userDAO;
            }
        @Override
        protected User doInBackground(String... strings) {
            return asyncTaskUserDAO.getUserByUserName(strings[0]);
        }
    }

    private static class getLoggedUserAsyncTask extends AsyncTask<String, Void, User> {
        private UserDAO asyncTaskUserDAO;
        getLoggedUserAsyncTask(UserDAO userDAO) {
            asyncTaskUserDAO = userDAO;
        }
        @Override
        protected User doInBackground(String... strings) {
            return asyncTaskUserDAO.getLoggedUser();
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDAO asyncTaskUserDAO;
        deleteAllAsyncTask(UserDAO userDAO) { asyncTaskUserDAO = userDAO; }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskUserDAO.deleteAll();
            return null;
        }
    }

    private static class setAsLoggedInAsyncTask extends AsyncTask <User, Void, Void>{
        private UserDAO asyncTaskUserDAO;
        setAsLoggedInAsyncTask(UserDAO userDAO) { asyncTaskUserDAO = userDAO; }

        @Override
        protected Void doInBackground(User... users) {
            asyncTaskUserDAO.setUserAsLoggedIn(users[0].getUserName());
            return null;
        }
    }

    private static class setAsLoggedOutAsyncTask extends AsyncTask <User, Void, Void>{
        private UserDAO asyncTaskUserDAO;
        setAsLoggedOutAsyncTask(UserDAO userDAO) { asyncTaskUserDAO = userDAO; }

        @Override
        protected Void doInBackground(User... users) {
            asyncTaskUserDAO.setUserAsLoggedOut(users[0].getUserName());
            return null;
        }
    }
}


