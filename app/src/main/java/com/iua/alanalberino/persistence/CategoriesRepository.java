package com.iua.alanalberino.persistence;

import android.app.Application;
import android.os.AsyncTask;

import com.iua.alanalberino.model.Category;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoriesRepository {
    private CategoriesDAO categoriesDAO;
    private Category category;

    public CategoriesRepository(Application application) {
        TheMovieDBDatabase database = TheMovieDBDatabase.getDatabase(application);
        categoriesDAO = database.categoriesDAO();
    }

    public void addCategory(Category category){
        new addCategoryAsyncTask(categoriesDAO).execute(category);
    }

    public List<Category> getCategories() throws ExecutionException, InterruptedException {
        return new getCategoriesAsyncTask(categoriesDAO).execute().get();
    }

    private static class addCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoriesDAO asyncTaskCategoriesDAO;
        addCategoryAsyncTask(CategoriesDAO categoriesDAO) { asyncTaskCategoriesDAO = categoriesDAO; }

        @Override
        protected Void doInBackground(Category... categories) {
            asyncTaskCategoriesDAO.addCategory(categories[0]);
            return null;
        }
    }

    private static class getCategoriesAsyncTask extends AsyncTask<Void, Void, List<Category>> {
        private CategoriesDAO asyncTaskCategoriesDAO;
        getCategoriesAsyncTask(CategoriesDAO categoriesDAO) { asyncTaskCategoriesDAO = categoriesDAO; }

        @Override
        protected List<Category> doInBackground(Void... voids) {
            return asyncTaskCategoriesDAO.getCategories();
        }
    }

}
