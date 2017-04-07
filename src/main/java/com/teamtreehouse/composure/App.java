package com.teamtreehouse.composure;

import com.teamtreehouse.composure.filters.Html;
import com.teamtreehouse.composure.filters.Markdown;
import com.teamtreehouse.composure.utils.Renderer;

import java.util.function.Function;

public class App {
    public static void main(String[] args) {
        String htmlInput = "<p>Using strong tags <strong>allows you to over promote</strong> and " +
                "<a href=\"http://www.myspace.com/tom\">Links should open in new browser</a></p>";
        
        // Möglichkeit 1
//        //Strips the bold
//        String result = Html.boldCleaner(htmlInput);
//
//        // Add rel = external
//        result = Html.externalizeLinks(result);
    
        // Möglichkeit 2
//        String renderedLink = Renderer.render(htmlInput, text -> {
//            String html = Html.boldCleaner(text);
//            return Html.externalizeLinks(html);
//
//        });
    
        // Möglichkeit 3 -> Composing (zusammensetzen) a method
        // Chaining Functions with andThen
        Function<String, String> boldCleaner = Html::boldCleaner;
        Function<String, String> sanitizer = boldCleaner.andThen(Html::externalizeLinks);
        
        String renderedLink = Renderer.render(htmlInput, sanitizer);
        System.out.println(renderedLink);
        
        // Composing Functions with compose
        String markDownIput = "Using strong tags **allows you to over promote** and "
            + "[Links open in a new browser](http://www.gwo.de)";
        
        // Markdown::render wird zuerst ausgeführt
        // und wird dann in sanitizer gepasst
        Function<String, String> markedDownSenitizer = sanitizer.compose(Markdown::render);
        String marked = Renderer.render(htmlInput, markedDownSenitizer);
        System.out.println(marked);
        
        marked = Renderer.render(markDownIput, markedDownSenitizer);
        System.out.println(marked);
  }
}
