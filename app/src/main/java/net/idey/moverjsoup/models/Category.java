package net.idey.moverjsoup.models;

import android.content.Context;

import net.idey.moverjsoup.R;

/**
 * Created by yusuf.abdullaev on 7/20/2016.
 */
public class Category implements IdConstants{
    CategoryTypes type;
    String title, id;

    public static Category newInstance(Context context, int nav_id){
        switch (nav_id){
            case R.id.nav_main:
                return new Category(CategoryTypes.MAIN, context.getString(R.string.main), null);
            case R.id.nav_latest:
                return new Category(CategoryTypes.LATEST, context.getString(R.string.latest), LATEST);
            case R.id.nav_search:
                return new Category(CategoryTypes.SEARCH, context.getString(R.string.search), SEARCH);
            case R.id.nav_humor:
                return new Category(CategoryTypes.HUMOR, context.getString(R.string.humor), HUMOR);
            case R.id.nav_cinema:
                return new Category(CategoryTypes.CINEMA, context.getString(R.string.cinema), CINEMA);
            case R.id.nav_tv:
                return new Category(CategoryTypes.TV, context.getString(R.string.tv), TV);
            case R.id.nav_game:
                return new Category(CategoryTypes.GAME, context.getString(R.string.game), GAME);
            case R.id.nav_music:
                return new Category(CategoryTypes.MUSIC, context.getString(R.string.music), MUSIC);
            case R.id.nav_avto:
                return new Category(CategoryTypes.AVTO, context.getString(R.string.avto), AVTO);
            case R.id.nav_sport:
                return new Category(CategoryTypes.SPORT, context.getString(R.string.sport), SPORT);
            case R.id.nav_tech:
                return new Category(CategoryTypes.TECH, context.getString(R.string.tech), TECH);
            case R.id.nav_education:
                return new Category(CategoryTypes.EDUCATION, context.getString(R.string.education), EDUCATION);
            case R.id.nav_adver:
                return new Category(CategoryTypes.ADVER, context.getString(R.string.adver), ADVER);
            case R.id.nav_beauty:
                return new Category(CategoryTypes.BEAUTY, context.getString(R.string.beauty), BEAUTY);
            case R.id.nav_anime:
                return new Category(CategoryTypes.ANIME, context.getString(R.string.anime), ANIME);
            case R.id.nav_family:
                return new Category(CategoryTypes.FAMILY, context.getString(R.string.family), FAMILY);
            case R.id.nav_cook:
                return new Category(CategoryTypes.COOK, context.getString(R.string.cook), COOK);
            case R.id.nav_animals:
                return new Category(CategoryTypes.ANIMALS, context.getString(R.string.animals), ANIMALS);
            case R.id.nav_news:
                return new Category(CategoryTypes.NEWS, context.getString(R.string.news), NEWS);
            case R.id.nav_nature:
                return new Category(CategoryTypes.NATURE, context.getString(R.string.nature), NATURE);
            case R.id.nav_arts:
                return new Category(CategoryTypes.ARTS, context.getString(R.string.arts), ARTS);
            case R.id.nav_misc:
                return new Category(CategoryTypes.MISC, context.getString(R.string.misc), MISC);
        }
        return null;
    }

    public Category(CategoryTypes type, String title, String id) {
        this.type = type;
        this.title = title;
        this.id = id;
    }

    public CategoryTypes getType() {
        return type;
    }

    public void setType(CategoryTypes type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl(){

        String URL_BASE = "http://mover.uz/";
        String URL_VIDEO = "video/";

        if (type == CategoryTypes.MAIN){
            return URL_BASE;
        }else if(type == CategoryTypes.SEARCH){

        }

        return URL_BASE + URL_VIDEO + id;
    }
}
