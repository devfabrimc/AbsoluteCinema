package com.absolutecinema.utils;

import com.absolutecinema.model.Genre;

public final class GenreFormatter {

    private GenreFormatter() {
    }

    public static String format(Genre genre) {
        return switch (genre) {
            case TODOS -> "Todos los géneros";
            case ACCION -> "Acción";
            case ANIMACION -> "Animación";
            case AVENTURA -> "Aventura";
            case BIOGRAFIA -> "Biografía";
            case CIENCIA_FICCION -> "Ciencia ficción";
            case COMEDIA -> "Comedia";
            case CRIMEN -> "Crimen";
            case DRAMA -> "Drama";
            case FANTASIA -> "Fantasía";
            case HISTORIA -> "Historia";
            case HORROR -> "Horror";
            case ROMANCE -> "Romance";
            case SUSPENSO -> "Suspenso";
            case TERROR -> "Terror";
        };
    }
}