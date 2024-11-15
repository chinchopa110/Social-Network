package com.example.webapp1.Diaries.Posts.UserPostService;

import com.example.webapp1.Diaries.Posts.UserPost;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

public class UserPostComparator implements Comparator<UserPost> {
    @Override
    public int compare(UserPost post1, UserPost post2) {
        LocalDate date1 = LocalDate.parse(post1.getDate());
        LocalDate date2 = LocalDate.parse(post2.getDate());

        int dateComparison = date1.compareTo(date2);
        if (dateComparison != 0) {
            return dateComparison;
        }

        LocalTime time1 = LocalTime.parse(post1.getTime());
        LocalTime time2 = LocalTime.parse(post2.getTime());

        return time1.compareTo(time2);
    }
}
