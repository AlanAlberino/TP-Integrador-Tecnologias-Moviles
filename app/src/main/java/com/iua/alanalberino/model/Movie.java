package com.iua.alanalberino.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "movies", primaryKeys = {"id", "categoriaID"})
public class Movie {

    @NonNull
    private int id;
    @NonNull
    private int categoriaID;
    private String title;
    private String verticalImageURL;
    private String horizontalImageURL;
    private String fechaLanzamiento;
    private String categorias;
    private String duracion;
    private String descripcion;


    public Movie(int id, String titulo, String verticalImageURL){
        this.id = id;
        this.title=titulo;
        this.verticalImageURL = verticalImageURL;
    }

    public Movie(int id, String title, String verticalImageURL, String horizontalImageURL, String fechaLanzamiento, String categorias, String duracion, String descripcion, int categoriaID) {
        this.id = id;
        this.title = title;
        this.verticalImageURL = verticalImageURL;
        this.horizontalImageURL = horizontalImageURL;
        this.fechaLanzamiento = fechaLanzamiento;
        this.categorias = categorias;
        this.duracion = duracion;
        this.descripcion = descripcion;
        this.categoriaID = categoriaID;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVerticalImageURL() {
        return verticalImageURL;
    }

    public void setVerticalImageURL(String verticalImageURL) {
        this.verticalImageURL = verticalImageURL;
    }

    public String getHorizontalImageURL() {
        return horizontalImageURL;
    }

    public void setHorizontalImageURL(String horizontalImageURL) {
        this.horizontalImageURL = horizontalImageURL;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCategoriaID() {
        return categoriaID;
    }

    public void setCategoriaID(int categoriaID) {
        this.categoriaID = categoriaID;
    }
}
