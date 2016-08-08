package net.idey.moverjsoup.utils;

import net.idey.moverjsoup.models.CategoryTypes;
import net.idey.moverjsoup.models.SliderTabs;
import net.idey.moverjsoup.constants.TagsAttrConstants;
import net.idey.moverjsoup.models.Video;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yusuf.abdullaev on 7/19/2016.
 */
public class HtmlParser implements TagsAttrConstants{

    public List<Video> getVideosFromHtml(Document document, CategoryTypes type, SliderTabs tab){
        List<Video> modelList = new ArrayList<>();

        Element video_elem = null;

        switch (tab){
            case RECOMMENDED:
                if (type == CategoryTypes.MAIN){
                    video_elem = document.getElementById("home-recommended");
                }else {
                    video_elem = document.select(TAG_DIV+".video-recommended").first();
                }
                break;
            case TOP3:
                if (type == CategoryTypes.MAIN){
                    video_elem = document.getElementById("home-popular");
                }else {
                    video_elem = document.select(TAG_DIV + ".video-list").first();
                }
                break;
            case NEW:
                video_elem = document.select(TAG_DIV+".video-list").last();
                break;
        }

        Elements elements_videos = video_elem.select(TAG_DIV+".video");
        for (int c = 0; c < elements_videos.size(); c++) {
            Video model = new Video();
            //Элемент для одного видео из списка
            Element element_video = elements_videos.get(c);
            //Элемент для тэга а(ссылка), внутри тэги img и span
            Element video_a = element_video.select(TAG_A).first();
            model.setUrl(video_a.attr(ATTR_HREF));
            model.setTitle(video_a.attr(ATTR_TITLE));
            //Тэг img со ссылкой на thumb в атрибуте src
            Element video_a_img = video_a.select(TAG_IMG).first();
            model.setImage_url(video_a_img.attr(ATTR_SRC));
            //Тэг span с одной лишь длиной видео внутри
            Element video_a_span = video_a.select(TAG_SPAN + ".length").first();
            model.setLength(video_a_span.text());
            //Элемент для тэга info, внутри полная инфа о видео
            Element info = element_video.select(TAG_DIV + ".info").first();
            //Проверяем есть ли тэг р.views и вытаскиваем из него текст с кол-вом просмотров
            if (info.select(TAG_P + ".views").size()>0){
                Element views = info.select(TAG_P + ".views").first();
                model.setViews(views.text());
            }
            //Тэг p.owner, из ссылки внутри него вытаскиваем имя автора видео
            Element owner = info.select(TAG_P + ".owner").first();
            model.setOwner(owner.select(TAG_A).first().text());
            modelList.add(model);
        }
        return modelList;
    }

}
