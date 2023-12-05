package com.example.duan1bookapp;

import com.example.duan1bookapp.models.Link;

import java.util.ArrayList;
import java.util.List;

public class BookmarkManager {
    private static List<Link> bookmarkedLinks = new ArrayList<>();

    public static void addBookmark(Link link) {
        if (!bookmarkedLinks.contains(link)) {
            bookmarkedLinks.add(link);
        }
    }

    public static void removeBookmark(Link link) {
        bookmarkedLinks.remove(link);
    }

    public static List<Link> getBookmarkedLinks() {
        return bookmarkedLinks;
    }

    public static boolean isBookmarked(Link link) {
        return bookmarkedLinks.contains(link);
    }
}
